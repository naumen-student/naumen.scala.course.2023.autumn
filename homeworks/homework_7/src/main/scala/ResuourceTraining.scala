package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] =
    ZIO.acquireReleaseWith(ZIO.attempt(new BufferedReader(new FileReader(filePath))))(
      fileReader => ZIO.attempt(fileReader.close()).orDie)(
      fileReader => ZIO.attempt(fileReader.readLine())
    )

  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] =
    ZIO.acquireReleaseWith(ZIO.attempt(new BufferedWriter(new FileWriter(filePath))))(
      fileWriter => ZIO.attempt(fileWriter.close()).orDie)(
      fileWriter => ZIO.attempt {
        fileWriter.write(data)
        fileWriter.flush()
      }).orDie

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}