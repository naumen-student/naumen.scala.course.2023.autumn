package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.FileWriter
import scala.io.Source

/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] =
    ZIO.acquireReleaseWith(
      ZIO.attempt(Source.fromFile(filePath))
    )(scanner => ZIO.succeed(scanner.close()))
    {
      source => ZIO.iterate(new StringBuilder
      )(_ => source.hasNext
      )(sb => ZIO.attempt(sb.append(source.next())))
        .map(r => r.result())
    }


  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] =
    ZIO.acquireReleaseWith(
      ZIO.succeed(new FileWriter(filePath))
    )(writer => ZIO.succeed(writer.close())){ writer => ZIO.succeed(writer.write(data)) }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
