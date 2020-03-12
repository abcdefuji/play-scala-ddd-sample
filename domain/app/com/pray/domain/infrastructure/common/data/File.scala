package com.pray.domain.infrastructure.common.data

trait FileType

trait File[+A <: FileType] {
  // def self: java.io.File
}

case class BinaryFile[+A <: FileType](filename: String, contentType: A, content: Array[Byte]) extends File[A] {



}

object FileUtils {


}