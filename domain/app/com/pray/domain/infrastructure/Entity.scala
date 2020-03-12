package com.pray.domain.infrastructure

import common.Status.Status
import common.{Identifier, Version}

trait Entity[ID <: Identifier[_]] {

  val id: ID

  val status: Status

  val version: Version

  override def equals(obj: Any): Boolean = this match {
    case that: Entity[_] => id == that.id
    case _ => false
  }

  override def hashCode: Int = 31 * id.##

  def withVersion: Entity[ID]

}
