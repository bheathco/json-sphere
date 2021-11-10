import org.joda.time.LocalDateTime
import io.sphere.json._

object Main extends App {
  val sphereWeWent = SphereWeWent(LocalDateTime.now(), 5)

  val json = toJSON[SphereWeWent](sphereWeWent)
  println(s"json value: ${json}")

  val reSphere = fromJSON[SphereWeWent](json)

  println(s"from json age : $reSphere")

}