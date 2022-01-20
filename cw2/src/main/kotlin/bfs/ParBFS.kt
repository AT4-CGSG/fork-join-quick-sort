package bfs

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.atomic.AtomicInteger
import util.primitives.ParallelFor
import util.primitives.ParallelScan
import util.primitives.ParallelFilter

class ParBFS : BFS {
    var buff: Array<AtomicInteger?>? = null

    override fun prepare(): Array<Int?> {
        val res = arrayOfNulls<Int?>(buff!!.size)

        for (i in buff!!.indices) {
            if (buff!![i] != null)
                res[i] = buff!![i]!!.get()
        }

        return res
    }

    override fun perform(graph: Array<IntArray?>, start: Int) {
        val result = arrayOfNulls<AtomicInteger>(graph.size)
        val frontier = AtomicReference(IntArray(1))

        frontier.get()[0] = start

        ParallelFor().run({ i -> AtomicInteger(-1).also { result[i!!] = it } }, 0, graph.size)
        result[start]!!.set(0)

        while (frontier.get().isNotEmpty()) {
            var weights = IntArray(frontier.get().size)
            ParallelFor().run({ i -> weights[i!!] = graph[frontier.get()[i]]!!.size }, 0, weights.size)
            weights = ParallelScan().run(weights)

            val curFrontier = IntArray(weights[weights.size - 1])
            ParallelFor().run({ i -> curFrontier[i!!] = -1 }, 0, curFrontier.size)
            ParallelFor().run({ i ->
                val cur = frontier.get()[i!!]

                if (cur == -1) return@run

                val dist = result[cur]!!.get()
                var myNextStart = weights[i]
                graph[cur]!!
                    .filter { result[it]!!.compareAndSet(-1, dist + 1) }
                    .forEach { curFrontier[myNextStart++] = it }
            }, 0, frontier.get().size)

            frontier.set(ParallelFilter().run(curFrontier) { x: Int? -> x != -1 })
        }

        buff = result
    }
}