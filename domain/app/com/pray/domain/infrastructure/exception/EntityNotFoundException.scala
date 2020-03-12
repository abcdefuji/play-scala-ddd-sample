package com.pray.domain.infrastructure.exception

import com.pray.domain.infrastructure.common.Identifier

class EntityNotFoundException private (message: String) extends Exception(message)

object EntityNotFoundException {

  def apply(identifier: Identifier[Any]) =
    new EntityNotFoundException(s"Entity is not found: identifier = $identifier")

  def apply(clause: String) =
    new EntityNotFoundException(s"Entity is not found: cause = '$clause'")

}