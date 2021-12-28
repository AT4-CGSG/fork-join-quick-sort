package util.primitives

import java.util.function.Function

class ParallelFilter : ComputationalTree() {
    fun run(data: IntArray, predicate: Function<Int?, Boolean>): IntArray {
        val flags = IntArray(data.size)
        ParallelFor().run({ i: Int? -> if (predicate.apply(data[i!!])) { flags[i] = 1 } }, 0, data.size)
        val sums = ParallelScan().run(flags)
        val res = IntArray(sums[sums.size - 1])
        ParallelFor().run({ i: Int? -> if (flags[i!!] == 1) { res[sums[i]] = data[i] } }, 0, data.size)
        return res
    }
}