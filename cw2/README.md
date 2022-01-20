This is a comparison between sequential and parallel implementations of breadth first search.

Example runs are shown in [Main.kt](src/main/kotlin/Main.kt).

We're getting average performance boost around 1.4-2.2 times from parallel implementation compared to sequential.
Some example results for 10 consecutive runs on graphs with 100^3 vertexes:
1.980, 2.088, 2.208, 1.876, 2.083, 1.420, 2.183, 2.114, 2.174, 2.075

