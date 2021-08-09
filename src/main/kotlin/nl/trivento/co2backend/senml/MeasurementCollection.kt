package nl.trivento.co2backend.senml

class MeasurementCollection {
    private val measurements = mutableMapOf<String, Measurement>()

    fun addMeasurement(measurement: Measurement) {
        val attribute = measurement.name.split(':').last()
        measurements[attribute] = measurement
    }

    fun getStringMeasurement(name: String): StringMeasurement? {
        val measurement = measurements[name]
        if (measurement is StringMeasurement?) return measurement
        else throw Exception("$name is not a StringMeasurement")
    }

    fun getDoubleMeasurement(name: String): DoubleMeasurement? {
        val measurement = measurements[name]
        if (measurement is DoubleMeasurement?) return measurement
        else throw Exception("$name is not a DoubleMeasurement")
    }

    fun getBooleanMeasurement(name: String): BooleanMeasurement? {
        val measurement = measurements[name]
        if (measurement is BooleanMeasurement?) return measurement
        else throw Exception("$name is not a BooleanMeasurement")
    }
}

