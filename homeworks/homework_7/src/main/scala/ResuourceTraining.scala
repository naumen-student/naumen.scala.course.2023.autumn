package ru.dru

import zio._
import java.io.IOException
import java.nio.file.{Files, Paths}


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] = {
    ZIO.attempt {
      new String(Files.readAllBytes(Paths.get(filePath)))
    }.refineToOrDie[IOException]
  }

  def writeData(filePath: String, data: String): ZIO[Any, Throwable, Unit] = {
    ZIO.attempt {
      Files.write(Paths.get(filePath), data.getBytes)
    }.unit.refineToOrDie[IOException]
  }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
