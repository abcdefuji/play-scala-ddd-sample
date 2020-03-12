package com.pray.domain.infrastructure.common.data

import com.pray.infrastructure.service.TokenService

sealed trait Token {

  val value: String

}

case class MD5Token(value: String) extends Token

case class SHA256Token(value: String) extends Token

object Token extends TokenService {

  def withMD5 = MD5Token(generateMD5Token())

  def withSHA256 = SHA256Token(generateSHA256Token())
}
