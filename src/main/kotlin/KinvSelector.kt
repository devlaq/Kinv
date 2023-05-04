sealed class KinvSelector(val list: List<Pair<Int, Int>>) {
    fun exclude(vararg positions: Pair<Int, Int>) = exclude(positions.toList())

    fun exclude(positions: List<Pair<Int, Int>>): KinvListSelector {
        val newList = list.toMutableList()
        positions.forEach { p ->
            newList.removeIf { it == p }
        }

        return KinvListSelector(newList)
    }

    fun include(vararg positions: Pair<Int, Int>) = include(positions.toList())

    fun include(positions: List<Pair<Int, Int>>): KinvSelector {
        val newList = list.toMutableList()
        positions.forEach {
            if(!newList.contains(it)) newList.add(it)
        }

        return KinvListSelector(newList)
    }

    fun border(row: Int) = include(KinvBorderSelector(row).list)
    fun fill(from: Pair<Int, Int>, to: Pair<Int, Int>) = include(KinvFillSelector(from, to).list)
}

class KinvEmptySelector: KinvSelector(listOf())

class KinvListSelector(list: List<Pair<Int, Int>>): KinvSelector(list)

class KinvFillSelector(from: Pair<Int, Int>, to: Pair<Int, Int>): KinvSelector(createList(from, to)) {
    companion object {
        private fun createList(from: Pair<Int, Int>, to: Pair<Int, Int>): List<Pair<Int, Int>> {
            val fromRow = from.first
            val fromCol = from.second
            val toRow = to.first
            val toCol = to.second

            val list = mutableListOf<Pair<Int, Int>>()

            for (row in fromRow .. toRow) {
                for (col in fromCol .. toCol) {
                    list.add(row to col)
                }
            }

            return list
        }
    }
}

class KinvBorderSelector(row: Int): KinvSelector(createList(row)) {
    companion object {
        private fun createList(row: Int): List<Pair<Int, Int>> {
            val base = KinvEmptySelector()

            return base.fill(1 to 1, 1 to 9) // top
                .fill(1 to 1, row to 1) // left
                .fill(1 to 9, row to 9) // right
                .fill(row to 1, row to 9).list // bottom
        }
    }
}
