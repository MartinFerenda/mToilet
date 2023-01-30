package com.example.core.entities

import java.time.ZonedDateTime

open class Event (
    open var id: Int? = null,
    var date: ZonedDateTime,
    var userId: Int,
    var deviceId: Int
        )