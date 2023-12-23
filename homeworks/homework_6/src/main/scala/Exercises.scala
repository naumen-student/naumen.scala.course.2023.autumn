import utils.ColorService.ColorService
import utils.PictureGenerationService.PictureGenerationService
import utils.Utils._
import utils._
import zio._

import java.awt.Color

object Exercises {

    /**
     * В задании необходимо модифицировать ZIO объект, чтобы в случае ошибки в методе getColor
     * вернулся None, а в случае упеха Some
     */
    def task1(r: Int, g: Int, b: Int): URIO[ColorService, Option[Color]] =
        ZIO.serviceWithZIO[ColorService](_.getColor(r, g, b)).option


    /**
     * Неободимо модифицировать ZIO объект так, чтобы он возвращал текстовую матрицу цветов вида
     * 1 23 -4
     * 25 -1 2
     * где элементы - числовые значения объекта Color (можно получить через getRGB)
     */
    def task2(size: (Int, Int)): ZIO[PictureGenerationService, GenerationError, String] = {
      import helpers._
      implicit val colorShow: Show[Color] = c => s"${c.getRGB.abs}"

      for {
        pic <- ZIO.serviceWithZIO[PictureGenerationService](_.generatePicture(size))
      } yield (for {
        l <- pic.lines
      } yield l.intercalate(" ")).mkString("\n")
    }


    /**
     * В задаче необходимо поработать с ошибками
     * 1. Необходимо, чтобы тип ошибки был единым для всего объекта ZIO, иначе не соберется
     * 2. Необходимо добавить в случае каждой из ошибок возвращать ее с определенным текстом
     *  - при ошибке генерации случайного цвета -> Не удалось создать цвет
     *  - при генерации картинки -> Ошибка генерации изображения
     *  - при заполнении картинки -> Возникли проблемы при заливке изображения
     */
    def task3(size: (Int, Int)): ZIO[PictureGenerationService with ColorService, GenerationError, Picture] = {
      import utils.Utils.GenerationError
      for {
        colorSvc <- ZIO.service[ColorService]
        pictureSvc <- ZIO.service[PictureGenerationService]
        color <- colorSvc.generateRandomColor().mapError(_ => GenerationError("Не удалось создать цвет"))
        pic <- pictureSvc.generatePicture(size).mapError(_ => GenerationError("Ошибка генерации изображения"))
        filledPic <- pictureSvc.fillPicture(pic, color).mapError(_ => GenerationError("Возникли проблемы при заливке изображения"))
      } yield filledPic
    }


    /**
     * Необходимо предоставить объекту ZIO все необходимые зависимости
     */
    def task4(size: (Int, Int)): IO[GenerationError, Picture] =
        task3(size).provideLayer(
          ColorService.live >+> PictureGenerationService.live 
        )

}

object helpers {
  trait Show[A]{
    def show(a: A): String
  }
  object Show {
    def apply[A](implicit ev: Show[A]): Show[A] = ev
  }
  
  implicit class ShowOps[A](private val a: A) extends AnyVal {
    def show()(implicit ev: Show[A]): String = Show[A].show(a)
  }
  
  implicit class ListOps[A](private val l: List[A]) extends AnyVal {
    def intercalate(sep: String)(implicit ev: Show[A]): String =
        l match {
            case h1 :: (r@ _ :: _) => r.foldLeft(s"${h1.show}"){
                case (acc, next) => s"$acc$sep${next.show}"
            }
            case h :: Nil => s"${h.show}"
            case Nil => ""
        }
  }
}