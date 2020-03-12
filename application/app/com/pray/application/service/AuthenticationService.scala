package com.pray.application.service

import play.api.mvc._
import scala.concurrent.Future

class AuthenticatedRequest[A](request: Request[A], val user: String)
  extends WrappedRequest[A](request)

trait AuthenticationService {
  def executeInvokeBlock[A](request: Request[A],
                            block: AuthenticatedRequest[A] => Future[Result])
  : Future[Result]
}

/**
 * AuthenticateActionBase
 * -- you must add your user authentication!
 */
trait AuthenticateActionBase extends AuthenticationService {
  def executeInvokeBlock[A](request: Request[A],
                            block: AuthenticatedRequest[A] => Future[Result])
  : Future[Result] =  block(new AuthenticatedRequest(request, "you must add your user authentication"))
}

object ActionWithAuth extends ActionBuilder[AuthenticatedRequest] with AuthenticateActionBase {
  def invokeBlock[A](request: Request[A],
                     block: AuthenticatedRequest[A] => Future[Result])
  : Future[Result] = executeInvokeBlock(request, block)
}
