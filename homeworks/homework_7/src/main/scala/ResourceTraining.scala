package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{File, FileWriter}
import java.util.Scanner


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] =
    ZIO.acquireReleaseWith(
      ZIO.attempt(new Scanner(new File(filePath))))(sc => ZIO.succeed(sc.close()))
    { sc =>
      ZIO.iterate(new StringBuilder)(
          _ => sc.hasNext)(builder => ZIO.attempt(builder.append(sc.nextLine()))).map(_.result())
    }

  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] =
    ZIO.acquireReleaseWith(
      ZIO.succeed(new FileWriter(new File(filePath)))
    )(wr => ZIO.succeed(wr.close())) { wr =>
      ZIO.succeed(wr.write(data))
    }


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}