package com.github.devlaq.kinv

import com.github.devlaq.kinv.util.KinvAccessPosition
import com.github.devlaq.kinv.util.KinvPosition
import com.github.devlaq.kinv.util.toKinvAccessPosition

sealed class KinvSelector(val list: List<KinvAccessPosition>) {
    fun exclude(vararg positions: Pair<Int, Int>) {
        exclude(positions.map { it.toKinvAccessPosition() })
    }

    fun exclude(positions: List<KinvAccessPosition>): KinvListSelector {
        val newList = list.toMutableList()
        positions.forEach { p ->
            newList.removeIf { it == p }
        }

        return KinvListSelector(newList)
    }

    fun include(vararg positions: Pair<Int, Int>) {
        include(positions.map { it.toKinvAccessPosition() })
    }

    fun include(positions: List<KinvAccessPosition>): KinvSelector {
        val newList = list.toMutableList()
        positions.forEach {
            if(!newList.contains(it)) newList.add(it)
        }

        return KinvListSelector(newList)
    }

    fun border(row: Int) = include(KinvBorderSelector(row).list)

    fun fill(from: KinvAccessPosition, to: KinvAccessPosition) = include(KinvFillSelector(from, to).list)
    fun fill(from: KinvPosition, to: KinvPosition) = fill(from.toAccessPosition(), to.toAccessPosition())
    fun fill(from: Pair<Int, Int>, to: Pair<Int, Int>) = fill(from.toKinvAccessPosition(), to.toKinvAccessPosition())
}

class KinvEmptySelector: KinvSelector(listOf())

class KinvListSelector(list: List<KinvAccessPosition>): KinvSelector(list)

class KinvFillSelector(from: KinvAccessPosition, to: KinvAccessPosition): KinvSelector(createList(from, to)) {

    constructor(from: KinvPosition, to: KinvPosition): this(from.toAccessPosition(), to.toAccessPosition())

    companion object {
        private fun createList(from: KinvAccessPosition, to: KinvAccessPosition): List<KinvAccessPosition> {
            val list = mutableListOf<KinvAccessPosition>()

            for (row in from.row .. to.row) {
                for (col in from.col .. to.col) {
                    list.add((row to col).toKinvAccessPosition())
                }
            }

            return list
        }
    }
}

class KinvBorderSelector(row: Int): KinvSelector(createList(row)) {
    companion object {
        private fun createList(row: Int): List<KinvAccessPosition> {
            val base = KinvEmptySelector()

            return base.fill(1 to 1, 1 to 9) // top
                .fill(1 to 1, row to 1) // left
                .fill(1 to 9, row to 9) // right
                .fill(row to 1, row to 9).list // bottom
        }
    }
}
