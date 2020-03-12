package com.pray.domain.infrastructure.common.data

object AccountType extends Enumeration {

  type AccountType = Value

  val User = Value("User")

  val Admin = Value("Admin")

  val Free = Value("Free")

  val Premium = Value("Premium")

}
