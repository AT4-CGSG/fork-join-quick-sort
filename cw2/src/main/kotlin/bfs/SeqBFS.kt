package bfs

import java.util.*

class SeqBFS : BFS {
    override fun perform(graph: Array<IntArray?>, start: Int) {
        val visited = BooleanArray(graph.size)
        val queue: Queue<Int> = PriorityQueue()
        Arrays.fill(visited, false)
        queue.add(start)

        while (!queue.isEmpty()) {
            val u = queue.poll()
            if (!visited[u]) {
                visited[u] = true
                for (v in graph[u]!!) {
                    if (u == v) continue
                    if (!visited[v]) queue.add(v)
                }
            }
        }
    }
}