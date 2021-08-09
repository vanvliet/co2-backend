package nl.trivento.co2backend.domain

import nl.trivento.co2backend.senml.BooleanMeasurement
import nl.trivento.co2backend.senml.DoubleMeasurement
import nl.trivento.co2backend.senml.MeasurementCollection
import nl.trivento.co2backend.senml.StringMeasurement
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant

class MeasurementCollectionTest {

    @Test
    fun test1() {
        val col = MeasurementCollection()
        col.addMeasurement( StringMeasurement("temp1", Instant.now(), "Cel","10.9"))
        col.addMeasurement( DoubleMeasurement("temp2", Instant.now(), "F",99.9))
        col.addMeasurement( BooleanMeasurement("tempLimit", Instant.now(), "BOOLEAN?",true))

        Assertions.assertEquals("10.9", col.getStringMeasurement("temp1")!!.value)
        Assertions.assertEquals(99.9, col.getDoubleMeasurement("temp2")!!.value)

        Assertions.assertEquals("Cel", col.getStringMeasurement("temp1")!!.unit)
        Assertions.assertEquals("F", col.getDoubleMeasurement("temp2")!!.unit)

        Assertions.assertEquals(true, col.getBooleanMeasurement("tempLimit")!!.value)

    }

}
