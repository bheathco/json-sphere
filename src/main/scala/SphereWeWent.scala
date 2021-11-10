import cats.data.Validated
import io.sphere.json._
import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import org.json4s.{JObject, JValue}

case class SphereWeWent(dateTime: LocalDateTime, age: Int)
object SphereWeWent {
  private[this] final val localTimeFormatter = new DateTimeFormatter(
    ISODateTimeFormat.dateTime.getPrinter,
    ISODateTimeFormat.dateTimeParser.getParser
  ).withOffsetParsed

  implicit val json: JSON[SphereWeWent] = new JSON[SphereWeWent] {
    import cats.data.ValidatedNel
    import cats.syntax.apply._

    def read(jval: JValue): ValidatedNel[JSONError, SphereWeWent] = jval match {
      case o: JObject =>
        (field[String]("dateTime")(o) match {
          case Validated.Valid(a) => Validated.Valid(localTimeFormatter.parseLocalDateTime(a))
          case Validated.Invalid(e) => Validated.Invalid(e)
        },
          field[Int]("age")(o)).mapN(SphereWeWent.apply)
      case _ => fail("eek")
    }

    def write(s: SphereWeWent): JValue = JObject(List(
      "dateTime" -> toJValue(localTimeFormatter.print(s.dateTime)),
      "age" -> toJValue(s.age)
    ))
  }
}
