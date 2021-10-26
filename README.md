This is a comparison between sequential and parallel implementations of quick sorting algorithm.

The parallel implementation is based on ForkJoinPool and uses 4 threads to perform sorting.

Example runs are shown in [QuickSortTest.java](src/test/java/QuickSortTest.java).

We're getting average performance boost around 3-3.5 times from parallel implementation compared to sequential.
