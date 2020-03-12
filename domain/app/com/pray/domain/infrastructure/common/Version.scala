package com.pray.domain.infrastructure.common

import org.joda.time.DateTime

case class Version(createdAt: DateTime = DateTime.now,
                   updatedAt: Option[DateTime] = None) {

  def value = (updatedAt getOrElse createdAt).getMillis

}

object Version {

  def init: Version = Version()

}
