package com.pray.domain.infrastructure.exception

class EntitiesNotFoundException private (message: String) extends Exception(message)

object EntitiesNotFoundException {

  def apply() = new EntitiesNotFoundException(s"Entities are not found")

  def apply(clause: String) =
    new EntitiesNotFoundException(s"Entities are not found: cause = '$clause'")

}