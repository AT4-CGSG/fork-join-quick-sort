package util.primitives

import java.util.function.Function

class ParallelMap : ComputationalTree() {
    fun run(function: Function<Int?, Int>, data: IntArray) {
        ParallelFor().run({ i: Int? -> data[i!!] = function.apply(data[i]) }, 0, data.size)
    }
}