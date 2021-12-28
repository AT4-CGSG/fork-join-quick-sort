package quicksort;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
   
/*
 * @author atsutsiev
 */
public class QuickSort extends RecursiveAction {
    private final int l, r;
    private final int[] a;
    private final int span;

    public QuickSort(final int[] a) { this(a, 0, a.length - 1, 0); }

    private QuickSort(final int[] a, final int left, final int right, final int span) {
        this.a = a; this.l = left; this.r = right; this.span = span;
    }

    public void sortSync() {
        if (r - l < 1000) { Arrays.sort(a, l, r + 1); return; }

        if (l < r) {
            final int p = partition(l, r);
            new QuickSort(a, l, p - 1, span + 2).sortSync();
            new QuickSort(a, p + 1, r, span + 2).sortSync();
        }
    }

    @Override
    protected void compute() {
        if (span >= 4) {
            /* this way we're getting span=4
            *              *     d = 0
            *             / \
            *            *   *   d = 1
            *           /\   /\
            *          *  * *  * d = 2
            */
            sortSync();
            return;
        }

        if (l < r && l < a.length && r < a.length) {
            final int p = partition(l, r);
            final int p1 = partition(l, p - 1);
            final int p2 = partition(p + 1, r);

            final var t1 = new QuickSort(a, l, p1 - 1, span + 4);
            final var t2 = new QuickSort(a, p1 + 1, p - 1, span + 4);
            final var t3 = new QuickSort(a, p + 1, p2 - 1, span + 4);
            final var t4 = new QuickSort(a, p2 + 1, r, span + 4);
            t1.fork();
            t2.fork();
            t3.fork();
            t4.compute();
            t1.join();
            t2.join();
            t3.join();
        }
    }

    private int partition(final int left, final int right) {
        final int m = a[right];
        int i = left - 1;
        for (int j = left; j < right; j++) if (a[j] <= m) swap(++i, j);
        swap(++i, right);
        return i;
    }

    private void swap(final int i, final int j) { final int t = a[i]; a[i] = a[j]; a[j] = t; }
}