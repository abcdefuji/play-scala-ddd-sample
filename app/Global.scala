import play.api._
import play.api.mvc._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    // this is called when application start
    Logger.info("Application has started")
  }

  override def onStop(app: Application) {
    // this is called when application stop
    Logger.info("Application shutdown...")
  }

  private def subdomain(request: RequestHeader) =
    request.domain.replaceFirst("[\\.]?[^\\.]+[\\.][^\\.]+$", "")

  override def onRouteRequest(request: RequestHeader) = subdomain(request) match {
    case "admin" => admin.Routes.routes.lift(request)
    case "api" => api.Routes.routes.lift(request)
    case _ => web.Routes.routes.lift(request)
  }

  // 404 - page not found error
  override def onHandlerNotFound (request: RequestHeader) = subdomain(request) match {
    case "admin" => GlobalAdmin.onHandlerNotFound(request)
    case "api" => GlobalApi.onHandlerNotFound(request)
    case _ => GlobalWeb.onHandlerNotFound(request)
  }

  // 500 - internal server error
  override def onError (request: RequestHeader, throwable: Throwable) = subdomain(request) match {
    case "admin" => GlobalAdmin.onError(request, throwable)
    case "api" => GlobalApi.onError(request, throwable)
    case _ => GlobalWeb.onError(request, throwable)
  }

  // called when a route is found, but it was not possible to bind the request parameters.
  override def onBadRequest (request: RequestHeader, error: String) = subdomain(request) match {
    case "admin" => GlobalAdmin.onBadRequest(request, error)
    case "api" => GlobalApi.onBadRequest(request, error)
    case _ => GlobalWeb.onBadRequest(request, error)
  }

}
