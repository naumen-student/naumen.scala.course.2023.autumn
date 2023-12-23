package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.util.stream.Collectors


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {
  def readData(filePath: String): IO[Throwable, String] = {
    ZIO.acquireReleaseWith(
      ZIO.from(new BufferedReader(new FileReader(filePath)))
    )(
      reader => ZIO.attempt(reader.close()).ignore
    )
    {
      reader => ZIO.attempt(reader.lines().collect(Collectors.joining("\n")))
    }

  }

  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] =
      ZIO.acquireReleaseWith(
        ZIO.succeed(new BufferedWriter(new FileWriter(filePath)))
      )(
        writer => ZIO.attempt(writer.close()).ignore
      ){
        writer => ZIO.attempt(writer.write(data)).ignore
      }


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
