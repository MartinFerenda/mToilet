package com.example.mtoilet

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.core.entities.Event
import com.example.core.entities.LoggedUser
import com.example.core.entities.PaymentInfo
import com.example.mtoilet.data.Repository
import com.example.mtoilet.databinding.ActivityQrcodeScanBinding
import com.example.mtoilet.viewmodel.MToiletViewModel
import java.time.ZoneId
import java.time.ZonedDateTime

private const val CAMERA_REQUEST_CODE = 101
private const val INTERNET_REQUEST_CODE = 102


class QRCodeScan : AppCompatActivity() {

    private lateinit var binding: ActivityQrcodeScanBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var prefs: SharedPreferences
    private lateinit var webView: WebView
    private var deviceId: Int = -1
    private var orderId = ""
    private var paymentInitiated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodeScanBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        webView = binding.webView

        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        prefs = sharedPref

        binding.btnPay.visibility = View.INVISIBLE
        binding.qrScanProgressBar.visibility = View.INVISIBLE

        setupPermissions()
        codeScanner()
    }
    private fun codeScanner(){
        val viewModel = ViewModelProvider(this).get(MToiletViewModel::class.java)
        codeScanner = CodeScanner(this, binding.scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback{
                runOnUiThread {
                    deviceId = it.text.toInt()
                    binding.tvTextView.text = it.text
                    binding.qrScanProgressBar.visibility = View.VISIBLE
                    viewModel.initializeValue()
                    viewModel.getUrl(deviceId)
                    viewModel.fetchedUrl().observe(this@QRCodeScan, Observer{
                        if(viewModel.fetchedUrl().value!!) {
                            binding.btnPay.visibility = View.VISIBLE
                            binding.qrScanProgressBar.visibility = View.INVISIBLE
                        }
                    })
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume(){
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun setupPermissions(){
        val permissionCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val permissionInternet = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)

        if (permissionCamera != PackageManager.PERMISSION_GRANTED){
            makeRequestCamera()
        }
        if(permissionInternet != PackageManager.PERMISSION_GRANTED){
            makeRequestInternet()
        }
    }

    private fun makeRequestCamera() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }
    private fun makeRequestInternet(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET), INTERNET_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You need camera permission to be able to pay!", Toast.LENGTH_SHORT).show()
                }else{
                    //success
                }
            }
        }
    }
    fun getPaymentUri(view: View) {

        paymentInitiated = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(PaymentInfo.paymentUri)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        binding.linLayout.visibility = View.INVISIBLE
        binding.webView.visibility = View.VISIBLE
        paymentInitiated = true
    }
    override fun onBackPressed() {
        if(paymentInitiated){
            webView.evaluateJavascript("(function(){" +
                    "const reference = document.getElementById(\"details-desc-12\");" +
                    "return reference.innerText})();",
                object : ValueCallback<String?> {
                    override fun onReceiveValue(html: String?) {
                        if (html != "null") {
                            binding.webView.visibility = View.INVISIBLE
                            binding.tvTextView2.text = html
                            binding.linLayout.visibility = View.VISIBLE

                            orderId = html!!
                            val newOrder : String = orderId.removeSurrounding("\"")
                            val repository = Repository()

                            val viewModel2 = ViewModelProvider(this@QRCodeScan).get(MToiletViewModel::class.java)
                            viewModel2.getCheckPay(newOrder)
                            viewModel2.fetchedPaymentStatus().observe(this@QRCodeScan, Observer{
                                if(viewModel2.fetchedPaymentStatus().value!!) {
                                    val zagrebZone = ZoneId.of("Europe/Zagreb")
                                    val zagrebCurrentDateTime = ZonedDateTime.now(zagrebZone)
                                    val event = Event(0, zagrebCurrentDateTime, LoggedUser.id, deviceId)
                                    repository.postNewEvent(event)
                                    val intent = Intent(this@QRCodeScan, Success::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            })
                        }
                        else
                        {
                            binding.webView.visibility = View.INVISIBLE
                            binding.linLayout.visibility = View.VISIBLE
                            Toast.makeText(this@QRCodeScan, "Payment error, please try again!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@QRCodeScan, Error::class.java)
                            startActivity(intent)
                        }
                    }
                })
            paymentInitiated = false
        }
        else
        {
            super.onBackPressed()
        }
    }
}