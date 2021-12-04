package util

object Util {
    fun <T> transpose(list: List<List<T>>): List<List<T>> {
        val result: MutableList<MutableList<T>> = MutableList(list[0].size) { MutableList(list.size) { list[0][0] } }
        for ((i, col) in list.withIndex()) {
            for ((j, item) in col.withIndex()) {
                result[j][i] = item
            }
        }
        return result
    }
}