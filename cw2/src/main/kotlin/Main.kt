import graph.CubeGraph
import bfs.BFS
import java.util.function.Supplier
import kotlin.jvm.JvmStatic
import bfs.SeqBFS
import bfs.ParBFS

object Main {
    private val graph = CubeGraph.read()

    private fun <T : BFS?> testRun(notTylerButCreator: Supplier<T>): Long {
        val algorithm: T = notTylerButCreator.get()
        val start = System.currentTimeMillis()
        algorithm?.perform(graph, 0)
        return System.currentTimeMillis() - start
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val seqTime = testRun { SeqBFS() }
        val parTime = testRun { ParBFS() }
        println(
            """Seq: $seqTime | Par: $parTime | Improvement: ${String.format("%.3f", 1.0 * seqTime / parTime)}"""
        )
    }
}