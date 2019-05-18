                            主要内容

JVM相关知识的复盘

1：GC的发生位置一般是在线程共享的内存区域，如堆区和方法区（元空间）

2：四种 GC的回收算法，
        1：引用计数（较难处理循环引用，现在基本不用）
        2：复制算法（在年轻代中使用，在两个s区进行复制清空互换），优点：没有内存碎片 缺点：浪费空间 ，尤其有大对象的时候
        3：标记清除（在老年代中使用，先标记后清除）优点：节约内存空间 缺点：产生了内存碎片
        4：标记整理（在老年代中使用，先标记，后清除，再扫描移动整理确保没内存碎片）优点：节约了内存空间也保证没有内存碎片 缺点：耗费时间比较多
        最终引出--------->>>CG垃圾分代回收

3：GCRoot：跟搜索路径算法->可达性分析,通过GCRoot集合中的对象作为起始点进行引用可达性分析可以判断某个对象是否可回收（不可达就说明可回收，注意，必须是从GCRoot出发的引用遍历）。
    Q：哪些对象可作为GCRoot？
    A：GCRoot是一个集合，所以可以作为GCRoot的对象有：1：虚拟机栈（栈帧中的局部变量，也叫局部变量表）中的引用对象2：方法区中类的静态属性引用的对象3：方法区中常量引用的对象4：本地方法栈中的JNI引用对象！5：活跃的线程所引用的对象也是GCRoot哦！！！
        注意：方法区或常量区使用的其实是本地内存，jdk1.8之后又叫做mateSpace。

4:JVM 参数类型：
    1：标准参数：1：-version, -help , -showVersion
    2：X参数：1:-Xint: 使用解释执行 2:-Xcomp：第一次使用就编译成本地代码（编译执行） 3:-Xmixed:使用混合模式
    3：XX参数：
        3.1:Boolean 类型：
            公式：-XX:+或者- 某个属性值（+表示开启 -表示关闭）
            Case：
                1:是否打印GC收集细节：1：-XX:+PrintGCDetails 2:-XX:-PrintGCDetails
                2:是否使用串行垃圾回收器：1：-XX:+UseSerialGC 2：-XX:-UseSerialGC


        3.2:Key-Value 类型：
              公式：-XX:属性Key=属性Value
              Case：
                1：设置最大元空间内存：-XX:MetaspaceSize=124M
                2: 设置对象从内young区到老年区的年龄值（经过GC的次数）:-XX:MaxTenuringThreshold = 15


        3.3:查看JVM是否开启了某种选项：如查看是否开启了GCDetails
                方式1：
                1：jps获取指定进程号
                2：使用jinfo -flag 参数类型 进程号 查看JVM是否开启了相关属性/ jinfo flags 进程号 获取所有虚拟机对应的参数值
                    jinfo -flag PrintGCDetails 22862 获取PrintGCDetails状态
                    jinfo -flag MetaspaceSize 23098 查看MetaSpaceSize的大小

                方式2：
                    通过设置虚拟机参数来实现：
                    1：查看JVM初始化的参数信息：-XX:+PrintFlagsInitial
                    2：查看被修改过的JVM参数信息：-XX:+PrintFlagsFinal -version，打印出来的值 在等号之前有冒号的说明这个参数是被更新修改过的，没有的就是默认的
                    3：打印命令行参数：-XX:+PrintCommandLineFlags -version 可以很方便的知道，目前JVM使用的垃圾回收器

        3.4:JVM参数的"坑"：
            Q：两个经典参数：-Xms ，-Xmx 这两个特殊参数你如何解释？
            A：-Xms 等价于 -XX:InitialHeapSize  -Xmx 等价于 -XX:MaxHeapSize，所以以上的几个参数其实是一种别名。

        3.5:一些基本的JVM调优参数(栈管运行，堆管存储)：
            1：-Xms 初始化堆内存 默认为物理内存的1/64 等价于-XX:InitialHeapSize
            2：-Xmx 最大堆内存 默认为物理内存的1/4 等价于-XX:MaxHeapSize
            3：-Xss 单个线程的线程栈大小 默认512K-1024K （大小和你运行的操作系统也有关系）等价于：-XX:ThreadStackSize,系统出厂默认值为0其实可以看作是默认的1024K
            4: -Xmn 新生区年轻代的大小 默认为总堆内存的1/3
            5: -XX:MetaSpaceSize 元空间默认21M大小 尽量调大（避免出现OOM）（1.8之后取代了永久代）大小元空间是方法区的落地实现，元空间位于本地内存不位于堆中理论上只受本地内存大小限制之所以容易出现OOM异常一般是由于初始化设置的空间大小不足。
            6: -XX:+PrintGCDetails 配置打印GC详细信息
            7: -XX:+UseSerialGC 配置使用串行垃圾收集器
            8: -XX:+PrintCommandLineFlags 配置打印JVM几个重要的相关参数
            9：-XX:SurvivorRatio 配置Young区中的伊甸园区和幸存区占比 默认是8：1：1 如：-XX:SurvivorRatio=4 结果：4：1：1
            10：-XX:NewRatio 配置新生代和老年代在整个堆内存中的占比默认是 1：2 如：-XX:NewRatio=4 结果：1：4
            11：-XX:MaxTenuringThreshold 是在对象从新生区到老年区要经过的GC次数默认是15次 如：-XX:MaxTenuringThreshold = 10 注意是有限制的 不能设置超过15
            等等..........

    5：Java中的四大引用：
        1：强引用（一般编程代码都是强引用）
            把一个对象赋给一个引用变量整个引用变量就是一个强引用，对于强引用就算出现了OOM异常JVM也不会对该类对象进行GC。

        2：软引用（缓存系统中用的比较多，降低OOM概率）
            是一种通过JDK提供的java.lang.ref.SoftReference实现的引用方式，内存足够的情况下不进行回收，内存不足的话就会被GC回收。

        3：弱引用（缓存系统中用的比较多，不太重要的缓存，降低OOM概率）
            是一种通过JDK提供的java.lang.ref.WeakReference实现的引用方式。若引用是一种不管内存够不够用只要有GC发生就一定会被GC回收

        4：虚引用（用于进行对象回收的工作进行监控）
            是一种通过JDK提供的java.lang.ref.PhantomReference实现的引用方式。该种引用就相当于没有引用一样，它不能被单独使用，必须配合referenceQueue进行使用，设置虚引用的唯一用处用于监控对象的回收状态。

    6：引用队列：
        若引用和虚引用在GC完成之前会被放置到引用队列中临时保存一下，以这种机制实现在对象被回收之前做一些事情！底层没有具体的数据结构，而只是使用一个next指针来串联形成单向链表

    触发FullGC的前提：
        1：Old区内存不足
        2：永久代内存不足（仅仅指的jdk1.7之前的版本）
        3：CMS GC时发生了回收失败的情况（退化成FullGC）。
        4：新生代要进入到老年代的时候检测到老年代放不下
        5：手动调用System.gc()
        6:使用RMI来管理的RPC服务，默认会一个小时执行一次FullGC
