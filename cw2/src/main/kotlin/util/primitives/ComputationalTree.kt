package util.primitives

import java.lang.Runnable
import java.util.concurrent.RecursiveAction

abstract class ComputationalTree {
    protected fun build(data: IntArray, id: Int, left: Int, right: Int, tree: IntArray) {
        if (right - left >= BLOCK_SIZE) {
            val m = (left + right) / 2
            forkJoin({ build(data, 2 * id + 1, left, m, tree) }) { build(data, 2 * id + 2, m, right, tree) }
            tree[id] = tree[2 * id + 1] + tree[2 * id + 2]
            return
        }
        tree[id] = (left until right).sumBy { data[it] }
    }

    protected fun compute(data: IntArray, from: Int, id: Int, left: Int, right: Int, tree: IntArray, res: IntArray) {
        if (right - left >= BLOCK_SIZE) {
            val m = (left + right) / 2
            forkJoin( {
                    compute(data, from, 2 * id + 1, left, m, tree, res)
            } ) {
                compute(data, from + tree[2 * id + 1], 2 * id + 2, m, right, tree, res)
            }
            return
        }

        var tmp = from
        (left until right).forEach { i -> res[i] = tmp; tmp += data[i] }
    }

    protected fun forkJoin(left: Runnable, right: Runnable) {
        val leftAction: RecursiveAction = object : RecursiveAction() { override fun compute() { left.run() } }
        val rightAction: RecursiveAction = object : RecursiveAction() { override fun compute() { right.run() } }
        leftAction.fork(); rightAction.fork()
        leftAction.join(); rightAction.join()
    }

    protected companion object { const val BLOCK_SIZE = 1024 }
}