package com.pray.infrastructure.cache

import java.sql.Timestamp

trait PrayCache[+A] {

  val identifier: A

  val timestamp: Timestamp

  val expire: Timestamp = new Timestamp(timestamp.getTime)

  val isExpired: Boolean = expire.getTime < System.currentTimeMillis()

}

