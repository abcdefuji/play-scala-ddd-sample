package com.pray.domain.infrastructure.common.data

case class Coordinates(latitude: Double, longitude: Double)

case class Placement(zipCode: String, prefectureCode: Int,
                     address: String, building: Option[String] = None,
                     coordinates: Option[Coordinates] = None)