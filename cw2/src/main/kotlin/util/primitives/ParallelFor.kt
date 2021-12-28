package util.primitives

import java.util.function.Consumer

class ParallelFor : ComputationalTree() {
    fun run(task: Consumer<Int?>, left: Int, right: Int) {
        if (right - left >= BLOCK_SIZE) {
            val m = (left + right) / 2
            forkJoin({ ParallelFor().run(task, left, m) }) { ParallelFor().run(task, m, right) }
            return
        }

        (left until right).forEach { i -> task.accept(i) }
    }
}