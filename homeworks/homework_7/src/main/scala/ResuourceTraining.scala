package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] = {
    val acquire = ZIO.succeed(new BufferedReader(new FileReader(filePath)))
    val release = (reader: BufferedReader) => ZIO.succeed(reader.close())
    val use = (reader: BufferedReader) => ZIO.succeed(reader.readLine())

    ZIO.acquireReleaseWith(acquire)(release)(use)
  }

  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] = {
    val acquire = ZIO.succeed(new BufferedWriter(new FileWriter(filePath, false)))
    val release = (writer: BufferedWriter) => ZIO.succeed(writer.close())
    val use = (writer: BufferedWriter) => ZIO.succeed(writer.write(data)).flatMap(_ => ZIO.succeed(writer.flush()))

    ZIO.acquireReleaseWith(acquire)(release)(use)
  }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
