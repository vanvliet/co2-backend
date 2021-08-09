package nl.trivento.co2backend.senml

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SenMLParserTests {

    @Test
    fun `it should normalize a single datapoint with only regular fields`() {
        val pack =
            """
                [
                  {"n":"urn:dev:ow:10e2073a01080063","u":"Cel","v":23.1, "t": 1.276020076001e+09}
                ]
            """.trimIndent()

        val resolvedRecords = pack.toRawSenMLs().normalize()

        Assertions.assertEquals(1, resolvedRecords.size)
        Assertions.assertEquals("urn:dev:ow:10e2073a01080063", resolvedRecords[0].n)
        Assertions.assertEquals("Cel", resolvedRecords[0].u)
        Assertions.assertEquals(23.1, resolvedRecords[0].v)
        Assertions.assertEquals(1276020076, resolvedRecords[0].t.epochSecond)
    }
}
//
//    @Test
//    fun `it should parse a date with a nanosecond precision`() {
//        val pack =
//            """
//                [
//                  {"bn":"urn:dev:ow:10e2073a01080063:","n":"voltage","v":120.1, "t": 1596719815.117644}
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.shouldHaveSize(1)
//        resolvedRecords[0].t.toString().shouldBe("2020-08-06T13:16:55.117Z")
//    }
//
//    @Test
//    fun `it should normalize multiple datapoints using a bn`() {
//        val pack =
//            """
//                [
//                  {"bn":"urn:dev:ow:10e2073a01080063:","n":"voltage","u":"V","v":120.1},
//                  {"n":"current","u":"A","v":1.2}
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.shouldHaveSize(2)
//        resolvedRecords[0].n.shouldBe("urn:dev:ow:10e2073a01080063:voltage")
//        resolvedRecords[1].n.shouldBe("urn:dev:ow:10e2073a01080063:current")
//    }
//
//    @Test
//    fun `it should reject a record without a name`() {
//        val pack =
//            """
//                [
//                  {"t":"1234567890","u":"V","v":120.1}
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//        resolvedRecords.shouldBeEmpty()
//    }
//
//    @Test
//    fun `it should add a base value to a value found in a record`() {
//        val pack =
//            """
//                [
//                  {"t":"1234567890","u":"V","n":"voltage", "v": 2, "bv": 3},
//                  {"t":"1234567890","u":"V","n":"voltage", "v": 5}
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.shouldHaveSize(2)
//        resolvedRecords[0].v.shouldBe(5.0)
//        resolvedRecords[1].v.shouldBe(8.0)
//    }
//
//    @Test
//    fun `it should ignore a record without a value`() {
//        val pack =
//            """
//                [
//                  {"t":"1234567890","u":"V","n":"voltage"}
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.shouldBeEmpty()
//    }
//
//    @Test
//    fun `it should handle complex multi-measure`() {
//        val pack =
//            """
//                [
//                  {"bt": 1587664053.315175, "bn": "70b3d57050000958:Oyster4:", "v": 43.6361761, "u": "lat", "n": "latitude"},
//                  {"v": 6.9132341, "u": "lon", "n": "longitude"},
//                  {"v": 0, "u": "km/h", "n": "speed"},
//                  {"v": 3775, "u": "%EL", "n": "batteryLevel"},
//                  {"vs": "0", "n": "TripMode"},
//                  {"vs": "0", "n": "LastFix"}
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.size.shouldBe(6)
//
//        resolvedRecords[0].should {
//            it.n.shouldBe("70b3d57050000958:Oyster4:latitude")
//            it.u.shouldBe("lat")
//            it.v.shouldBe(43.6361761)
//        }
//
//        resolvedRecords[4].should {
//            it.n.shouldBe("70b3d57050000958:Oyster4:TripMode")
//            it.vs.shouldBe("0")
//            it.u.shouldBeNull()
//        }
//    }
//
//    @Test
//    fun `it should handle a simple multi-datapoint pack`() {
//        val pack =
//            """
//                 [
//                    { "n": "urn:ngsi-ld:Sensor:Sample000001:incoming", "u": "count", "v": 1200 },
//                    { "n": "urn:ngsi-ld:Sensor:Sample000002:outgoing", "u": "count", "v": 506 }
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.size.shouldBe(2)
//        resolvedRecords[0].n.shouldBe("urn:ngsi-ld:Sensor:Sample000001:incoming")
//        resolvedRecords[1].n.shouldBe("urn:ngsi-ld:Sensor:Sample000002:outgoing")
//    }
//
//    @Test
//    fun `it should handle special NaN value in a record`() {
//        val pack =
//            """
//                 [
//                    { "v": NaN, "u": "s", "n": "timeOnline" }
//                ]
//            """.trimIndent()
//
//        val resolvedRecords = pack.toSenMLRecords().normalize()
//
//        resolvedRecords.size.shouldBe(1)
//        resolvedRecords[0].v.shouldBe(Double.NaN)
//    }
//}
