package util.primitives

class ParallelScan : ComputationalTree() {
    fun run(data: IntArray): IntArray {
        val res = IntArray(data.size + 1)
        IntArray(data.size).also {
            build(data, 0, 0, data.size, it)
            compute(data, 0, 0, 0, data.size, it, res)
            res[data.size] = data[data.size - 1] + res[data.size - 1]
        }
        return res
    }
}