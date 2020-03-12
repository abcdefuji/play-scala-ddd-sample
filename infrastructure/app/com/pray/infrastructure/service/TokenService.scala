package com.pray.infrastructure.service

import scala.util.Random
import java.security.{SecureRandom, MessageDigest}

class TokenService(withLength: Int = 45) {

  private final val TokenChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.-"

  private final val TokenPrefix = new Random(new SecureRandom()).alphanumeric.take(10).mkString

  private val secureRandom = new SecureRandom()

  private def toHexWith(typeName: String)(value: String): String =
    MessageDigest.getInstance(typeName).digest(value.getBytes("UTF-8")).map("%02x".format(_)).mkString("")

  private lazy val md5: String => String = toHexWith("MD5")

  private lazy val sha256: String => String = toHexWith("SHA-256")

  private def generateToken(tokenLength: Int): String = {
    val charLen = TokenChars.length()
    def generateTokenAccumulator(accumulator: String, number: Int): String = {
      if (number == 0) accumulator
      else
        generateTokenAccumulator(accumulator +
          TokenChars(secureRandom.nextInt(charLen)).toString, number - 1)
    }
    generateTokenAccumulator("", tokenLength)
  }

  private def tokenSeed: String = System.nanoTime() + generateToken(withLength)

  def generateMD5Token(tokenPrefix: String = TokenPrefix): String = md5(tokenPrefix + tokenSeed)

  def generateSHA256Token(tokenPrefix: String = TokenPrefix): String = sha256(tokenPrefix + tokenSeed)

}
