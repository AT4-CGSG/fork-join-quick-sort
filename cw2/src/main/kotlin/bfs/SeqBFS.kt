package bfs

import java.util.*

class SeqBFS : BFS {
    var buff: IntArray? = null

    override fun prepare(): Array<Int?> {
        val res = arrayOfNulls<Int?>(buff!!.size)
        buff!!.forEachIndexed { i, a -> res[i] = a }
        return res
    }

    override fun perform(graph: Array<IntArray?>, start: Int) {
        val dist = IntArray(graph.size)
        val visited = BooleanArray(graph.size)
        val queue: Queue<Int> = LinkedList()
        Arrays.fill(visited, false)
        queue.add(start)
        dist[start] = 0

        while (!queue.isEmpty()) {
            val u = queue.remove()
            if (!visited[u]) {
                visited[u] = true
                for (v in graph[u]!!) {
                    if (u == v) continue
                    if (!visited[v])  {
                        dist[v] = dist[u] + 1
                        queue.add(v)
                    }
                }
            }
        }

        buff = dist
    }
}