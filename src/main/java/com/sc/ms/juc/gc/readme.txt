                                GC相关知识复盘

 1：阅读GC日志
   GC：
   [GC (Allocation Failure) [PSYoungGen（GC类型）: 1910K（GC前占用的内存）->496K（GC后占用的内存）(2560K（总共占用的内存）)] 1910K（GC前JVM堆内存占用）->576K（GC后JVM堆内存占用）(9728K（JVM堆内存总占用空间）), 0.0016814（GC耗费的时间） secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

   FullGC：
   [Full GC (Allocation Failure) [PSYoungGen: 496K->0K(2560K)] [ParOldGen: 92K->522K(7168K)] 588K->522K(9728K), [Metaspace: 3298K->3298K(1056768K)], 0.0050083 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

