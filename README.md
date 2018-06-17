# Hollow: Quick comparision between in-memory and file-based stream computing
## Problem
Lets assume we want to crunch some data in given window. For the sake of simplicity I assumed that the windows are 
independent (ie. average not percentile).

My point is to compare two approaches, one which is based on memory available data (all at once) and other 
which computes the window based on data slice read from file. Once data is aggregated it is directed to output file.

To simplify the problem I assume that data beacons are constant in length. Provided example is based on data point 
(as understandable from Time Series databases, the value in given time (double + long)).

All files from `data` package is ready to use abstraction, `example` contains tested implementation.
## Results
Real results to be prepared, from quick observations (single thread, 5mln beacons, about 10 attempts, average):
* Memory based: **~500ms**
* File based: **~7000ms**

(computing + computing related operations only!)

Memory usage (about 10 attempts, max, GC executed before computation):
* Memory based: **210MB** 
* File based: **20MB**

## How to run example
1. Open project in your favorite IDE.
2. Run `com.jarbytes.data.hollow.example.InputDataGenerator` to prepare test data.
3. Run `com.jarbytes.data.hollow.example.MemoryExampleRunner` to run memory based test.
4. Run `com.jarbytes.data.hollow.example.FileExampleRunner` to run file based test.
