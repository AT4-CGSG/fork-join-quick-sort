package bfs

interface BFS {
    fun perform(graph: Array<IntArray?>, start: Int)
    fun prepare(): Array<Int?>
}