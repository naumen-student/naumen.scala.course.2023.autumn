package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] =
    ZIO.acquireReleaseWith(
      ZIO.attemptBlocking(
        new BufferedReader(new FileReader(filePath))
      )
    )(file => ZIO.attempt(file.close()).orDie) { file => ZIO.attemptBlocking(file.readLine()) }

  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] =
    ZIO.acquireReleaseWith(
      ZIO.attemptBlocking(
        new BufferedWriter(new FileWriter(filePath))
      )
    )(file => ZIO.attempt(
      {
        file.flush()
        file.close()
      }
    ).orDie) { file => ZIO.attempt(file.write(data)) }.orDie

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
