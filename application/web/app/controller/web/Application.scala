package controller.web

import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(interface.web.html.index("Hello! I'm the WEB!"))
  }

}