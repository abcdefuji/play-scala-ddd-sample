package com.pray.domain.infrastructure.common.data

case class Email(account: String, domain: String) {

  def value = s"$account@$domain"

}

object Email {

  def apply(email: String): Email = {
    if (email.contains("@")) {
      val splits = email.split("@").ensuring(_.size == 2)
      Email(splits(0), splits(1))
    } else Email("", "")
  }
}
