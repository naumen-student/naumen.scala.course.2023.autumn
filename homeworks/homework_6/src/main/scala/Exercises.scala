import utils.{ColorService, PictureGenerationService}
import utils.ColorService.{ColorService, ServiceImpl}
import utils.PictureGenerationService.PictureGenerationService
import utils.Utils._
import zio.{IO, Random, URIO, ZIO, ZLayer}

import java.awt.Color

object Exercises {

    /**
     * В задании необходимо модифицировать ZIO объект, чтобы в случае ошибки в методе getColor
     * вернулся None, а в случае упеха Some
     */
    def task1(r: Int, g: Int, b: Int): URIO[ColorService, Option[Color]] =
        ZIO.serviceWithZIO[ColorService.Service](_.getColor(r, g, b)).either.map(_.toOption)


    /**
     * Неободимо модифицировать ZIO объект так, чтобы он возвращал текстовую матрицу цветов вида
     * 1 23 -4
     * 25 -1 2
     * где элементы - числовые значения объекта Color (можно получить через getRGB)
     */
    def task2(size: (Int, Int)): ZIO[PictureGenerationService, GenerationError, String] =
        ZIO.serviceWith[PictureGenerationService.Service](_.generatePicture(size)).
          flatMap(picIO => picIO.map(pic => pic.lines.map(_.map(_.getRGB).mkString(" ")).mkString("\n")))


    /**
     * В задаче необходимо поработать с ошибками
     * 1. Необходимо, чтобы тип ошибки был единым для всего объекта ZIO, иначе не соберется
     * 2. Необходимо добавить в случае каждой из ошибок возвращать ее с определенным текстом
     *  - при ошибке генерации случайного цвета -> Не удалось создать цвет
     *  - при генерации картинки -> Ошибка генерации изображения
     *  - при заполнении картинки -> Возникли проблемы при заливке изображения
     */
    def task3(size: (Int, Int)): ZIO[PictureGenerationService with ColorService, GenerationError, Picture] = (
        for {
            colorServ <- ZIO.service[ColorService.Service]
            pictureServ <- ZIO.service[PictureGenerationService.Service]
            color <- colorServ.generateRandomColor().mapError(_ => new GenerationError("Не удалось создать цвет"))
            picture <- pictureServ.generatePicture(size).mapError(_ => new GenerationError("Ошибка генерации изображения"))
            filledPicture <- pictureServ.fillPicture(picture, color).mapError(_ => new GenerationError("Возникли проблемы при заливке изображения"))
        } yield filledPicture).catchAll(error=>ZIO.fail(error))

    /**
     * Необходимо предоставить объекту ZIO все необходимые зависимости
     */
    def task4(size: (Int, Int)): IO[GenerationError, Picture] =
        task3(size)
          .provideSomeLayer(PictureGenerationService.live)
          .provideSomeLayer(ColorService.live)

}
