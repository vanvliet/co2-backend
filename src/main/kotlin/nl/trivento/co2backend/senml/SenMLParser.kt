package nl.trivento.co2backend.senml

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.time.Instant
import kotlin.math.pow

private val logger = LoggerFactory.getLogger("SenMLParser")

@Serializable
data class RawSenML(
    val bn: String? = null,
    val bt: Double? = null,
    val bu: String? = null,
    val bv: Double? = null,
    val n: String? = null,
    val t: Double? = null,
    val u: String? = null,
    val v: Double? = null,
    val vs: String? = null,
    val vb: Boolean? = null,
    val vd: String? = null
)

data class NormalizedSenML(
    val n: String,
    val t: Instant,
    val u: String?,
    val v: Double?,
    val vs: String?,
    val vb: Boolean?,
    val vd: String?
)

fun List<NormalizedSenML>.toMeasurementCollection(): MeasurementCollection {

    val measurementCollection = MeasurementCollection()

    this.forEach {

        when {
            it.vs !== null -> measurementCollection.addMeasurement(StringMeasurement(it.n, it.t, it.u, it.vs))
            it.v !== null -> measurementCollection.addMeasurement(DoubleMeasurement(it.n, it.t, it.u, it.v))
            it.vb !== null -> measurementCollection.addMeasurement(BooleanMeasurement(it.n, it.t, it.u, it.vb))
            it.vd !== null -> measurementCollection.addMeasurement(DataMeasurement(it.n, it.t, it.u, it.vd))
            else -> throw Exception("${it.n} is not a subclass of Measurement")
        }
    }
    return measurementCollection
}

private val json = Json {
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
}

fun String.toRawSenMLs(): List<RawSenML> =
    try {
        json.decodeFromString(this)
    } catch (e: kotlinx.serialization.SerializationException) {
        logger.error(e.message ?: "Unknown exception while deserializing SenML pack")
        emptyList()
    }

fun List<RawSenML>.normalize(): List<NormalizedSenML> {
    val normalizedSenMLs = mutableListOf<NormalizedSenML>()

    var bn: String? = null
    var bt: Double? = null
    var bu: String? = null
    var bv: Double? = null
    this.forEach {
        bn = it.bn ?: bn
        bt = it.bt ?: bt
        bu = it.bu ?: bu
        bv = it.bv ?: bv

        val n = calculateName(bn, it.n)
        val v = calculateValue(bv, it.v)

        if (n != null && (v != null || it.vs != null || it.vb != null || it.vd != null)) {
            val normalizedSenML = NormalizedSenML(
                    n,
                    t = calculateTime(bt ?: 0.0, it.t ?: 0.0),
                    u = calculateUnit(bu, it.u),
                    v,
                    vs = it.vs,
                    vb = it.vb,
                    vd = it.vd
            )
            normalizedSenMLs.add(normalizedSenML)
        }
    }

    return normalizedSenMLs
}

private fun calculateName(bn: String?, n: String?): String? =
    if (bn != null) {
        if (n != null) {
            val lastBnChar = bn.last()
            // the trailing separator in the bn often misses from the payloads,
            // so add it manually if last char in bn is not alphanumeric
            when {
                (lastBnChar in 'a'..'z' || lastBnChar in 'A'..'Z' || lastBnChar in '0'..'9') -> "$bn:$n"
                else -> "$bn$n"
            }
        }
        else
            bn
    } else {
        n
    }

private fun calculateUnit(bu: String?, u: String?): String? =
    u ?: bu

private fun calculateValue(bv: Double?, v: Double?): Double? =
    if (bv != null && v != null)
        bv + v
    else v

private fun calculateTime(bt: Double, t: Double): Instant {
    val totalTime = bt + t

    return if (totalTime > 2.0.pow(28))
        Instant.ofEpochMilli((totalTime * 1000).toLong())
    else
        Instant.now().plusSeconds(totalTime.toLong())
}
