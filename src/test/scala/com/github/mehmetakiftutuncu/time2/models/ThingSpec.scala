package com.github.mehmetakiftutuncu.time2.models

import java.time._

import com.github.mehmetakiftutuncu.time2.UnitSpec
import io.circe.Json
import io.circe.syntax._

class ThingSpec extends UnitSpec {
  val when: ZonedDateTime = ZonedDateTime.of(2018, 1, 18, 17, 42, 35, 0, ZoneId.ofOffset("", ZoneOffset.ofHours(3)))
  val thing: Thing        = Thing(1, "Drink Water!", when)

  "Thing" should {
    "be converted to Json properly" in {
      val expectedJson = """{"id":1,"what":"Drink Water!","when":"2018-01-18T17:42:35+03:00"}"""

      thing.toJson.noSpaces shouldBe expectedJson
    }
  }

  "Converting from Json" should {
    "fail for invalid Json" in {
      val notAThingJson = Json.obj("foo" -> Json.fromString("bar"))

      Thing.fromJson(notAThingJson).isLeft shouldBe true
    }

    "work correctly for a valid Json" in {
      val thingJson = Json.obj(
        "id"   -> 1.asJson,
        "what" -> "Drink Water!".asJson,
        "when" -> "2018-01-18T17:42:35+03:00".asJson
      )

      Thing.fromJson(thingJson).right.get shouldBe thing
    }
  }
}
