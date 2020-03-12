package controller.admin

import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(interface.admin.html.index("Hello! I'm the ADMIN!"))
  }

}