                            主要内容

JVM相关知识的复盘

1：GC的发生位置一般是在线程共享的内存区域，如堆区和方法区（元空间，jdk8之后的方法区被移动到了堆区）

2：四种 GC的回收算法，
        1：引用计数（较难处理循环引用，现在基本不用）
        2：复制算法（在年轻代中使用，在两个s区进行复制清空互换），优点：没有内存碎片 缺点：浪费空间 ，尤其有大对象的时候
        3：标记清除（在老年代中使用，先标记后清除）优点：节约内存空间 缺点：产生了内存碎片
        4：标记整理（在老年代中使用，先标记，后清除，再扫描移动整理确保没内存碎片）优点：节约了内存空间也保证没有内存碎片 缺点：耗费时间比较多
        最终引出--------->>>CG垃圾分代回收

3：GCRoot：跟搜索路径算法->可达性分析,通过GCRoot集合中的对象作为起始点进行引用可达性分析可以判断某个对象是否可回收（不可达就说明可回收，注意，必须是从GCRoot出发的引用遍历）。
    Q：哪些对象可作为GCRoot？
    A：GCRoot是一个集合，所以可以作为GCRoot的对象有：1：虚拟机栈（栈帧中的局部变量，也叫局部变量表）中的引用对象2：方法区中类的静态属性引用的对象3：方法区中常量引用的对象4：本地方法栈中的JNI引用对象！5：活跃的线程所引用的对象也是GCRoot哦！！！6：Java方法栈栈中的局部变量
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
        2：永久代内存不足（仅仅指的jdk1.7之前的版本,JDK8之后将常量池一到了堆区了，这样减少了永久代（MateSpace区）内存被占满的概率）
        3：CMS GC时发生了回收失败的情况（退化成FullGC）。
        4：新生代要进入到老年代的时候检测到老年代放不下（jvm会以上次移入老年去的对象数量进行预测）。
        5：手动调用System.gc()。
        6:使用RMI来管理的RPC服务，默认会一个小时执行一次FullGC。


    7：Java对象的内存布局
            在虚拟机中每个java对象都有一个对象头，对象头由标记字段和类型指针组成，在 64 位的 Java 虚拟机中，对象头的标记字段占 64 位，
            而类型指针又占了 64 位。也就是说，每一个 Java 对象在内存中的额外开销就是 16 个字节。以 Integer 类为例，它仅有一个 int 类型的私有字段，
            占 4 个字节。因此，每一个 Integer 对象的额外内存开销至少是 400%。这也是为什么 Java 要引入基本类型的原因之一。

    8：我们手动调用System.gc()为什么有可能不会马上进行垃圾回收。
        jvm要等到jvm中相关的执行线程达到相应的安全点才会执行回收，
        这里说一下执行线程在jvm中可能处于的状态：
            1：处在执行本地方法（JNI）过程中。
            2：处于解释执行字节码阶段
            3：处在执行即使编译器生成的机器码阶段
            4：处于阻塞阶段
        根据以上的不同阶段，安全点检测的过程也会有不同。

     9：JVM如何让解决多个线程同时在堆区New对象时，有可能使用同一片内存空间的问题：
        虚拟机的解决方案时TLAB技术，也就是没有线程都预先向JVM申请一段连续的内存只用于本线程操作，如果该块内存使用完了或不够用了，
        线程需要进行第二次申请，同时申请空间的操作时加了锁的所以可以防止不同线程申请的内存块重合的情况
        这样就巧妙解决了以上的问题。

     10：卡表技术（用于解决老年代对象指向新生代的对象情况，避免扫描整个老年代）
     Minor GC 的另外一个好处是不用对整个堆进行垃圾回收。但是，它却有一个问题，那就是老年代的对象可能引用新生代的对象。也就是说，在标记存活对象的时候，我们需要扫描老年代中的对象。如果该对象拥有对新生代对象的引用，那么这个引用也会被作为 GC Roots。

     这样一来，岂不是又做了一次全堆扫描呢？
     卡表
     HotSpot 给出的解决方案是一项叫做卡表（Card Table）的技术。该技术将整个堆划分为一个个大小为 512 字节的卡，并且维护一个卡表，用来存储每张卡的一个标识位。这个标识位代表对应的卡是否可能存有指向新生代对象的引用。如果可能存在，那么我们就认为这张卡是脏的。
     在进行 Minor GC 的时候，我们便可以不用扫描整个老年代，而是在卡表中寻找脏卡，并将脏卡中的对象加入到 Minor GC 的 GC Roots 里。当完成所有脏卡的扫描之后，Java 虚拟机便会将所有脏卡的标识位清零。
     由于 Minor GC 伴随着存活对象的复制，而复制需要更新指向该对象的引用。因此，在更新引用的同时，我们又会设置引用所在的卡的标识位。这个时候，我们可以确保脏卡中必定包含指向新生代对象的引用。

    11:java 提供的三种锁机制：

    今天我介绍了 Java 虚拟机中 synchronized 关键字的实现，按照代价由高至低可分为重量级锁、轻量级锁和偏向锁三种。

    重量级锁会阻塞、唤醒请求加锁的线程。它针对的是多个线程同时竞争同一把锁的情况。Java 虚拟机采取了自适应自旋，来避免线程在面对非常小的 synchronized 代码块时，仍会被阻塞、唤醒的情况。

    轻量级锁采用 CAS 操作，将锁对象的标记字段替换为一个指针，指向当前线程栈上的一块空间，存储着锁对象原本的标记字段。它针对的是多个线程在不同时间段申请同一把锁的情况。

    偏向锁只会在第一次请求时采用 CAS 操作，在锁对象的标记字段中记录下当前线程的地址。在之后的运行过程中，持有该偏向锁的线程的加锁操作将直接返回。它针对的是锁仅会被同一线程持有的情况。

    12:java 范型擦除：
        java代码经过编译器变异之后的范性都会被擦除，如果没有定义范型的上限类型那将会被编译成Object，如果制定了上限类型就会被虚拟机转化为上限类型。

        类型擦除带来的问题：方法重写的问题。
        泛型的类型擦除带来了不少问题。其中一个便是方法重写。在第四篇的课后实践中，我留了这么一段代码：

        class Merchant<T extends Customer> {
          public double actionPrice(T customer) {
            return 0.0d;
          }
        }

        class VIPOnlyMerchant extends Merchant<VIP> {
          @Override
          public double actionPrice(VIP customer) {
            return 0.0d;
          }
        }
        VIPOnlyMerchant 中的 actionPrice 方法是符合 Java 语言的方法重写的，毕竟都使用 @Override 来注解了。然而，经过类型擦除后，父类的方法描述符为 (LCustomer;)D，而子类的方法描述符为 (LVIP;)D。这显然不符合 Java 虚拟机关于方法重写的定义。

        解决方式：通过jvm虚拟机生成的桥接方法应用于调用范型擦除之后的子类方法。
        为了保证编译而成的 Java 字节码能够保留重写的语义，Java 编译器额外添加了一个桥接方法。该桥接方法在字节码层面重写了父类的方法，并将调用子类的方法。

        class VIPOnlyMerchant extends Merchant<VIP>
        ...
          public double actionPrice(VIP);
            descriptor: (LVIP;)D
            flags: (0x0001) ACC_PUBLIC
            Code:
                 0: dconst_0
                 1: dreturn

          public double actionPrice(Customer);
            descriptor: (LCustomer;)D
            flags: (0x1041) ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
            Code:
                 0: aload_0
                 1: aload_1
                 2: checkcast class VIP
                 5: invokevirtual actionPrice:(LVIP;)D
                 8: dreturn

        // 这个桥接方法等同于
        public double actionPrice(Customer customer) {
          return actionPrice((VIP) customer);
        }
        在我们的例子中，VIPOnlyMerchant 类将包含一个桥接方法 actionPrice(Customer)，它重写了父类的同名同方法描述符的方法。该桥接方法将传入的 Customer 参数强制转换为 VIP 类型，再调用原本的 actionPrice(VIP) 方法。
        当一个声明类型为 Merchant，实际类型为 VIPOnlyMerchant 的对象，调用 actionPrice 方法时，字节码里的符号引用指向的是 Merchant.actionPrice(Customer) 方法。Java 虚拟机将动态绑定至 VIPOnlyMerchant 类的桥接方法之中，并且调用其 actionPrice(VIP) 方法。
        需要注意的是，在 javap 的输出中，该桥接方法的访问标识符除了代表桥接方法的 ACC_BRIDGE 之外，还有 ACC_SYNTHETIC。它表示该方法对于 Java 源代码来说是不可见的。当你尝试通过传入一个声明类型为 Customer 的对象作为参数，调用 VIPOnlyMerchant 类的 actionPrice 方法时，Java 编译器会报错，并且提示参数类型不匹配。

    13:JVM即时编译：这是一项用于提高代码执行速度的技术，刚开始虚拟机中的代码是通过解释执行的但当某段代码被调用的次数（以及循环回边执行次数（OSR编译））超过一定阀值的时候（可以认为调整）虚拟机就会通过即时编译技术将该段代码直接编译成机器码运行在硬件之上从而提高了运行速度。
        即时编译器有：C1，C2，Graal（实验性质的）
        C1（编译效率较快）：用于执行时间短，对启动性能有要求的情况下使用。-client
        C2（生成代码执行效率块）：执行时间长，或者对峰值性能有要求的情况下使用。-server

        目前主要主流的是使用分层编译的方式（对C1和C2进行分层），使用了分层编译之后原先设置的阀值就不生效，而是虚拟机本身动态来调整。

    14：Java 虚拟机的 profiling 以及基于所收集的数据的优化和去优化。
        profile文件搜集用于进行编译器优化。
        即时编译器会进行预测优化，当程序没有按照预测的分支执行，那虚拟机就通过去优化的方式从编译执行切换回解释执行


 15：编译器方法内联：

    它指的是：在编译过程中遇到方法调用时，将目标方法的方法体纳入编译范围之中，并取代原方法调用的优化手段。
    方法内联不仅可以消除调用本身带来的性能开销，还可以进一步触发更多的优化。因此，它可以算是编译优化里最为重要的一环。
    以 getter/setter 为例，如果没有方法内联，在调用 getter/setter 时，程序需要保存当前方法的执行位置，创建并压入用于 getter/setter 的栈帧、访问字段、弹出栈帧，最后再恢复当前方法的执行。而当内联了对 getter/setter 的方法调用后，上述操作仅剩字段访问。

    总结与实践
    今天我介绍了方法内联的过程以及条件。

    方法内联是指，在编译过程中，当遇到方法调用时，将目标方法的方法体纳入编译范围之中，并取代原方法调用的优化手段。

    即时编译器既可以在解析过程中替换方法调用字节码，也可以在 IR 图中替换方法调用 IR 节点。这两者都需要将目标方法的参数以及返回值映射到当前方法来。

    方法内联有许多规则。除了一些强制内联以及强制不内联的规则外，即时编译器会根据方法调用的层数、方法调用指令所在的程序路径的热度、目标方法的调用次数及大小，以及当前 IR 图的大小来决定方法调用能否被内联。


  16:Java对象的逃逸分析：
  总结与实践
  今天我介绍了 Java 虚拟机中即时编译器的逃逸分析，以及基于逃逸分析的优化。

  在 Java 虚拟机的即时编译语境下，逃逸分析将判断新建的对象是否会逃逸。即时编译器判断对象逃逸的依据有两个：一是看对象是否被存入堆中，二是看对象是否作为方法调用的调用者或者参数。

  即时编译器会根据逃逸分析的结果进行优化，如锁消除以及标量替换。后者指的是将原本连续分配的对象拆散为一个个单独的字段，分布在栈上或者寄存器中。

  部分逃逸分析是一种附带了控制流信息的逃逸分析。它将判断新建对象真正逃逸的分支，并且支持将新建操作推延至逃逸分支。


  17:Jvm注解处理器

    java虚拟机加载流程：
    如上图所示 出处 [1]，Java 源代码的编译过程可分为三个步骤：

    1：将源文件解析为抽象语法树；
    2：调用已注册的注解处理器；
    3：生成字节码。
    如果在第 2 步调用注解处理器过程中生成了新的源文件，那么编译器将重复第 1、2 步，解析并且处理新生成的源文件。每次重复我们称之为一轮（Round）。

    也就是说，第一轮解析、处理的是输入至编译器中的已有源文件。如果注解处理器生成了新的源文件，则开始第二轮、第三轮，解析并且处理这些新生成的源文件。当注解处理器不再生成新的源文件，编译进入最后一轮，并最终进入生成字节码的第 3 步。

    所有的注解处理器类都需要实现接口Processor。该接口主要有四个重要方法。其中，init方法用来存放注解处理器的初始化代码。之所以不用构造器，是因为在 Java 编译器中，注解处理器的实例是通过反射 API 生成的。也正是因为使用反射 API，每个注解处理器类都需要定义一个无参数构造器。

    通常来说，当编写注解处理器时，我们不声明任何构造器，并依赖于 Java 编译器，为之插入一个无参数构造器。而具体的初始化代码，则放入init方法之中。

    在剩下的三个方法中，getSupportedAnnotationTypes方法将返回注解处理器所支持的注解类型，这些注解类型只需用字符串形式表示即可。

    getSupportedSourceVersion方法将返回该处理器所支持的 Java 版本，通常，这个版本需要与你的 Java 编译器版本保持一致；而process方法则是最为关键的注解处理方法。

    JDK 提供了一个实现Processor接口的抽象类AbstractProcessor。该抽象类实现了init、getSupportedAnnotationTypes和getSupportedSourceVersion方法。

    它的子类可以通过@SupportedAnnotationTypes和@SupportedSourceVersion注解来声明所支持的注解类型以及 Java 版本。

    如：
    @SupportedAnnotationTypes("foo.CheckGetter")
    @SupportedSourceVersion(SourceVersion.RELEASE_10)
    public class CheckGetterProcessor extends AbstractProcessor {

      @Override
      public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO: annotated ElementKind.FIELD
        for (TypeElement annotatedClass : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(CheckGetter.class))) {
          for (VariableElement field : ElementFilter.fieldsIn(annotatedClass.getEnclosedElements())) {
            if (!containsGetter(annotatedClass, field.getSimpleName().toString())) {
              processingEnv.getMessager().printMessage(Kind.ERROR,
                  String.format("getter not found for '%s.%s'.", annotatedClass.getSimpleName(), field.getSimpleName()));
            }
          }
        }
        return true;
      }

      private static boolean containsGetter(TypeElement typeElement, String name) {
        String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        for (ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
          if (!executableElement.getModifiers().contains(Modifier.STATIC)
              && executableElement.getSimpleName().toString().equals(getter)
              && executableElement.getParameters().isEmpty()) {
            return true;
          }
        }
        return false;
      }
    }


    总结与实践
    今天我介绍了 Java 编译器的注解处理器。

    注解处理器主要有三个用途。一是定义编译规则，并检查被编译的源文件。二是修改已有源代码。三是生成新的源代码。其中，第二种涉及了 Java 编译器的内部 API，因此并不推荐。第三种较为常见，是 OpenJDK 工具 jcstress，以及 JMH 生成测试代码的方式。

    Java 源代码的编译过程可分为三个步骤，分别为解析源文件生成抽象语法树，调用已注册的注解处理器，和生成字节码。如果在第 2 步中，注解处理器生成了新的源代码，那么 Java 编译器将重复第 1、2 步，直至不再生成新的源代码。


    总之，注解相当于给某些代码贴了个标签。我们既可以通过注解处理器在编译时解析这些标签，也可以在运行时通过反射解析这些标签。解析后都会有一系列动作，这些动作就是对标签语义的诠释。
    我们平时用的较多的是通过反射来调用。