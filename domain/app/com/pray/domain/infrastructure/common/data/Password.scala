package com.pray.domain.infrastructure.common.data

sealed trait Hashed

trait UnSafe extends Hashed

trait Safe extends Hashed

case class Password[A <: Hashed] private (value: String) {

  override def toString = if (value != "") value else ""

  def hasing(implicit ev: A =:= UnSafe) = Password[Safe](Bcript.create(value))

  def isEqual(raw: String): Boolean = Bcript.check(raw, this.value)

}

object Password {

  def create(value: String) = Password[UnSafe](value)

  def fromRecord(value: String) = Password[Safe](value)

}
