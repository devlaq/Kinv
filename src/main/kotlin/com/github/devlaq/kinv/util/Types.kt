package com.github.devlaq.kinv.util


data class KinvPosition(
    val row: Int,
    val col: Int
) {
    constructor(pair: Pair<Int, Int>): this(pair.first, pair.second)

    fun toAccessPosition(): KinvAccessPosition {
        return KinvAccessPosition(row - 1, col - 1)
    }
}

data class KinvAccessPosition(
    val row: Int,
    val col: Int
) {
    fun toPosition(): KinvPosition {
        return KinvPosition(row + 1, col + 1)
    }
}

fun Pair<Int, Int>.slotToKinvPosition(): KinvPosition {
    return KinvPosition(this)
}

fun Pair<Int, Int>.toKinvAccessPosition(): KinvAccessPosition {
    return slotToKinvPosition().toAccessPosition()
}

fun Int.slotToKinvPosition(): KinvPosition {
    return (this / 9 + 1 to this % 9 + 1).slotToKinvPosition()
}

fun Int.slotToKinvAccessPosition(): KinvAccessPosition {
    return slotToKinvPosition().toAccessPosition()
}
