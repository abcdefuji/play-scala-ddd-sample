package controller.api

import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(interface.api.html.index("Hello! I'm the API!"))
  }

}