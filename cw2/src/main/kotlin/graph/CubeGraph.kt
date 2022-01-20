package graph

object CubeGraph {
    fun generate(size: Int): Array<IntArray?> {
        val graph = arrayOfNulls<IntArray>(size * size * size)
        for (i in 0 until size) for (j in 0 until size) for (k in 0 until size) {
            graph[i * size * size + j * size + k] = neighbours(i, j, k, size)
        }
        return graph
    }

    private fun neighbours(i: Int, j: Int, k: Int, size: Int): IntArray {
        val res: MutableList<Int> = ArrayList()
        if (i > 0) res.add((i - 1) * size * size + j * size + k)
        if (j > 0) res.add(i * size * size + (j - 1) * size + k)
        if (k > 0) res.add(i * size * size + j * size + k - 1)
        if (i < size - 1) res.add((i + 1) * size * size + j * size + k)
        if (j < size - 1) res.add(i * size * size + (j + 1) * size + k)
        if (k < size - 1) res.add(i * size * size + j * size + k + 1)
        val arr = IntArray(res.size)
        res.indices.forEach { t -> arr[t] = res[t] }
        return arr
    }
}