package com.pray.application.infrastructure

import com.github.tototoshi.play2.json4s.native.Json4s
import com.pray.domain.infrastructure.{ EntityIOContext, EntityIOContextOnJDBC, Entity }
import com.pray.domain.infrastructure.common.Identifier
import org.json4s.{ Extraction, DefaultFormats }
import play.api.mvc._
import scalikejdbc._

trait BaseController[ID <: Identifier[Long], E <: Entity[ID]] extends Controller with Json4s {

  implicit val formats = DefaultFormats

  protected def withTransaction[T](f: EntityIOContext => T): T = DB localTx { implicit s =>
    f(EntityIOContextOnJDBC(s))
  }

  def withJson(entity: E) = Extraction.decompose(entity)

  def withJson(entity: Option[E]) = Extraction.decompose(entity)

  def withJson(entities: Seq[E]) = Extraction.decompose(entities)

  protected val BadRequestForIOError = { ex: Throwable => BadRequest(s"IOError: $ex") }

}
