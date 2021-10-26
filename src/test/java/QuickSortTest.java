import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import quicksort.QuickSort;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

import static java.lang.System.nanoTime;
import static java.lang.System.out;
import static java.util.Arrays.setAll;
import static org.assertj.core.api.Assertions.assertThat;

public class QuickSortTest {
    private static final int[] randomFilled = new int[(int)1e+8];
    private static final ForkJoinPool fjPool = new ForkJoinPool();
    private QuickSort handle;

    @Before
    public void fillRandom() { setAll(randomFilled, i -> (int) (Math.random() * 303030)); }

    @AfterClass
    public static void shutdownPool() { fjPool.shutdown(); }

    @Test
    public void testChronograph() {
        int[] a = randomFilled.clone();

        // sync test
        handle = new QuickSort(a);
        final double syncTime = runWithChronograph(() -> { handle.sortSync(); return null; });

        // async test
        a = randomFilled.clone();
        handle = new QuickSort(a);
        final double asyncTime = runWithChronograph(() -> { fjPool.invoke(handle); return null; });

        out.println("Sync time: " + syncTime + "ms");
        out.println("Async time: " + asyncTime + "ms");
        out.println("Performance boost (times): " + syncTime / asyncTime);
    }

    @Test
    public void testCorrectness() {
        int[] a = randomFilled.clone();

        // sync test
        handle = new QuickSort(a);
        handle.sortSync();
        checkSort(a);

        // async test
        a = randomFilled.clone();
        handle = new QuickSort(a);
        fjPool.invoke(handle);
        checkSort(a);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void checkSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) assertThat(a[i] <= a[i + 1]);
    }

    private static <T> double runWithChronograph(final Supplier<T> s) {
        final long start = nanoTime();
        s.get();
        return (nanoTime() - start) / 1e+6;
    }
}