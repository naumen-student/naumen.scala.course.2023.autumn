package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.util.stream.Collectors


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] = ZIO.acquireReleaseWith(acquireReader(filePath))(releaseReader) {
    reader => ZIO.succeed(reader.lines().collect(Collectors.joining("\n")))
  }

  private def acquireReader(filePath: String) = ZIO.attempt(new BufferedReader(new FileReader(filePath)))

  private def acquireWriter(filePath: String) = ZIO.attempt(new BufferedWriter(new FileWriter(filePath)))
  private def releaseReader(reader: BufferedReader) = ZIO.succeed(reader.close())

  private def releaseWriter(writer: BufferedWriter) = ZIO.succeed(writer.close())
  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] = ZIO.acquireReleaseWith(acquireWriter(filePath))(releaseWriter) {
    writer => ZIO.attempt(writer.write(data))
  }.orDie

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
