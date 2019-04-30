                                GC相关知识复盘

 1：阅读GC日志
   GC：
   [GC (Allocation Failure) [PSYoungGen（GC类型）: 1910K（GC前占用的内存）->496K（GC后占用的内存）(2560K（总共占用的内存）)] 1910K（GC前JVM堆内存占用）->576K（GC后JVM堆内存占用）(9728K（JVM堆内存总占用空间）), 0.0016814（GC耗费的时间） secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

   FullGC：
   [Full GC (Allocation Failure) [PSYoungGen: 496K->0K(2560K)] [ParOldGen: 92K->522K(7168K)] 588K->522K(9728K), [Metaspace: 3298K->3298K(1056768K)], 0.0050083 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]

2：OOM异常全解析：
    要注意的是StackOverflowError和OutOfMemoryError都是错误而不是异常，只是平常口语可能叫异常！

    1：java.lang.StackOverflowError:java线程栈溢出
        方法调用层次深导致方法栈被撑爆了，比较常见的是递归过深导致的。

    2：java.lang.OutOfMemoryError:Java heap space
        对象过多堆内存被占满导致的异常

    3：java.lang.OutOfMemoryError:GC overhead limit exceeded
        在GC执行时间过长也就是98%的时间都用于处理垃圾回收的情况下，不一会内存又被填满又开始GC的恶性循环，这是就会抛出这个异常。

    4：java.lang.OutOfMemoryError:Direct buffer memory
        直接内存也叫直接内存（MetaSpace所属的空间就是直接内存，也就是本地内存）缓存挂了。出现的原因是NIO引起的，因为NIO程序经常使用allocateDirect创建字节BUff进行数据缓存，所以不在堆内存中GC管理不到，所以哪些Buffer很肯能没被回收导致本地内存被用光。
        本地内存：JVM以外可用的内存，默认是物理内存容量的1/4；

    5：java.lang.OutOfMemoryError:unable to create new native thread
        在高并发应用中经常出现，该异常与对应的平台有关，原因是应用进程创建了过多的线程超过了系统的承受极限就会报这个错误。
        解决方式：1：降低线程数 2：提升平台的线程承载数

    6：java.lang.OutOfMemoryError:Metaspace
        元空间内存溢出，元空间使用的是本地内存不是在堆中的，里面存放着类模版已经常量池之类的。
