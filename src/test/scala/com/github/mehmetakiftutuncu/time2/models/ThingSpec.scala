package com.github.mehmetakiftutuncu.time2.models

import com.github.mehmetakiftutuncu.time2.UnitSpec
import io.circe.Json
import io.circe.syntax._

class ThingSpec extends UnitSpec {
  val thing: Thing = Thing(1, "Drink Water!", Some("a glass"), 2, 3)

  "Thing" should {
    "be converted to Json properly" in {
      val expectedJson = s"""{"id":1,"what":"Drink Water!","how":"a glass","ownerId":2,"planId":3}"""

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
        "id"      -> 1.asJson,
        "what"    -> "Drink Water!".asJson,
        "how"     -> "a glass".asJson,
        "ownerId" -> 2.asJson,
        "planId"  -> 3.asJson
      )

      Thing.fromJson(thingJson).right.get shouldBe thing
    }
  }
}
