package com.example.mtoilet

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
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.mtoilet.databinding.ActivityQrcodeScanBinding

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

        setupPermissions()
        codeScanner()
    }
    private fun codeScanner(){
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
        //TODO: API call za url sa /api/pay/deviceId koji se dobije iz QR koda, spremljen u device id
        webView.loadUrl("https://sandbox-business.revolut.com/payment-link/kwaEeMmJLw8dlT-izHxAww")
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
                            //TODO: API call za provjeru placanja na api/checkpay/orderId
                            //TODO: ako je placeno API call za otvaranje vrata
                        }
                        else
                        {
                            binding.webView.visibility = View.INVISIBLE
                            binding.linLayout.visibility = View.VISIBLE
                            Toast.makeText(this@QRCodeScan, "Payment error, please try again!", Toast.LENGTH_SHORT).show()
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