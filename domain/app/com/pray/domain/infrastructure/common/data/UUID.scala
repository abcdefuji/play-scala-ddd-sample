package com.pray.domain.infrastructure.common.data

case class UUID(value: String)

object UUID {

  def create = UUID(java.util.UUID.randomUUID.toString)

}