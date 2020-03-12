package com.pray.infrastructure.db

import org.joda.time.DateTime

trait Record {

  val id: Long

  val status: String

  val createdAt: DateTime

  val updatedAt: Option[DateTime]

}
