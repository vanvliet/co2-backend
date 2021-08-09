package nl.trivento.co2backend.senml

import  java.time.Instant

open class Measurement(
    open val name: String
)

data class DoubleMeasurement(
    override val name: String,
    val timeStamp: Instant,
    val unit: String?,
    val value: Double
): Measurement(name)

data class StringMeasurement(
    override val name: String,
    val timeStamp: Instant,
    val unit: String?,
    val value: String
): Measurement(name)

data class BooleanMeasurement(
    override val name: String,
    val timeStamp: Instant,
    val unit: String?,
    val value: Boolean
): Measurement(name)

data class DataMeasurement(
    override val name: String,
    val timeStamp: Instant,
    val unit: String?,
    val value: String
): Measurement(name)

