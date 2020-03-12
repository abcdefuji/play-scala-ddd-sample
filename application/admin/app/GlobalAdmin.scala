import play.api._
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

object GlobalAdmin extends GlobalSettings {

  // 404 - page not found error
  override def onHandlerNotFound (request: RequestHeader) = Future.successful(
    NotFound(interface.admin.html.index(request.toString()))
  )

  // 500 - internal server error
  override def onError (request: RequestHeader, throwable: Throwable) = Future.successful(
    InternalServerError(interface.admin.html.index(throwable.toString))
  )

  // called when a route is found, but it was not possible to bind the request parameters
  override def onBadRequest (request: RequestHeader, error: String) = Future.successful(
    BadRequest("Bad Request: " + error)
  )

}
