import graph.CubeGraph
import bfs.BFS
import java.util.function.Supplier
import kotlin.jvm.JvmStatic
import bfs.SeqBFS
import bfs.ParBFS

object Main {
    private val graph = CubeGraph.generate(100)

    private fun <T : BFS?> testRun(notTylerButCreator: Supplier<T>): Pair<Long, Array<Int?>> {
        val algorithm: T = notTylerButCreator.get()
        val start = System.currentTimeMillis()
        algorithm?.perform(graph, 0)!!
        val totalTime = System.currentTimeMillis() - start
        return Pair(totalTime, algorithm.prepare())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val (seqTime, seqRes) = testRun { SeqBFS() }
        val (parTime, parRes) = testRun { ParBFS() }

        if (seqRes.size == parRes.size) {
            var succ = true
            for (i in seqRes.indices) {
                if (seqRes[i] != parRes[i]) { succ = false; println("Failed"); break }
            }
            if (succ) println("Great success!!")

        } else println("Failed")

        println(
            """Seq: $seqTime | Par: $parTime | Improvement: ${String.format("%.3f", 1.0 * seqTime / parTime)}"""
        )
    }
}