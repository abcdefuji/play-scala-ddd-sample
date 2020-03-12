package com.pray.infrastructure.cookie

import java.util.UUID
import play.api.mvc.Cookie
import play.api.Play.current

object CookieFactory {

  private val CookieExpire = 30 * 60

  val CookieKeySessionId = "pray.sessionId"

  def create(value: Option[String] = None) =
    Cookie(CookieKeySessionId,  value.getOrElse(UUID.randomUUID().toString), Some(CookieExpire))

  def create(value: String) = Cookie(CookieKeySessionId, value, Some(CookieExpire))

  def create(key: String, value: String) = Cookie(key, value, Some(CookieExpire))

}
