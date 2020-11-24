package com.example.podcast.helpers


fun interpolate(
    value: Float,
    input: ClosedFloatingPointRange<Float>,
    output: ClosedFloatingPointRange<Float>,
): Float {
    val (inputMin, inputMax) = input
    val (outputMin, outputMax) = output

    if (outputMin == outputMax) {
        return outputMin
    }
    return if (inputMin == inputMax) {
        if (value <= inputMin) {
            outputMin
        } else outputMax
    } else outputMin + (outputMax - outputMin) * (value - inputMin) / (inputMax - inputMin)
}

private operator fun <T : Comparable<T>> ClosedFloatingPointRange<T>.component1(): T {
    return this.start
}

private operator fun <T : Comparable<T>> ClosedFloatingPointRange<T>.component2(): T {
    return this.endInclusive
}
