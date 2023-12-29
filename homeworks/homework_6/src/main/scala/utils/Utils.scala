package utils

import zio.{IO, Random, Task, ZIO}

import java.awt.Color

object Utils {
    case class Picture(lines: List[List[Color]])

    class GenerationError(msg: String) extends Throwable {
        override def getMessage: String = msg
    }

    object GenerateRandomColorError extends GenerationError("Не удалось создать цвет")
    object GeneratePictureError extends GenerationError("Ошибка генерации изображения")
    object FillPictureError extends GenerationError("Возникли проблемы при заливке изображения")

    val zeroSize = (0, 0)

}
