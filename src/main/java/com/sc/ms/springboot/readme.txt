三大特性：

1：自动装配：
    @EnableAutoConfiguration

2：嵌入式容器：
    WebServer:tomcat  jetty等...
    WebFlux: netty Web Server

3:生产准备特性：
    指标信息：/actuator/metrics
    健康检查：/actuator/health
    外部化配置：/actuator/configprops 可通过外部化配置的方式修改应用的行为


传统Servlet应用
   1：三大组件：
        Servlet
            实现：@WebServlet注解对应的Servlet类并继承HttpServlet类
            URL映射：通过@WebServlet注解中的WebUrlPartten属性进行设置
            注册：通过SpringBoot提供的@SelvletComponentScan注解进行配置扫描路径进行注册
        Filter
            @WebFilter
        Listener
            @Listener
   2：Servlet注册：
       1：Servlet注解
       2：SpringBean
       3：RegistrationBean
   3：异步非阻塞：
        1：异步Servlet：Servlet3.0提供的一个实现
        2：非阻塞Servlet是Servlet3.1提供的一个实现，可运行webFlux

SpringMVC应用
    MVC的视图：模版引擎，内容协商，异常处理等
        ViewResolver：
        View：
    MVC的REST：资源服务，资源跨域，服务发现等
        跨域：Spring 框架提供的 CrossOrigin
    MVC的核心：核心架构，核心组件，处理流程

WebFlux应用



二：走向自动装配

   1：Spring框架的收订装配

       Spring模式注解装配
            定义：一种用于声明在应用中扮演的角色的注解
            如：@Service @Component（是Spring模式注解的基础）
            装配方式：xml或@ComponentScan
       @Enable模块装配：
            用于将具有相同领域的功能组建集合，组合起来形成一个独立的单元，可以不用一个个的去配置
            如：@EnableWebMvc ，@EnableAutoConfiguration 等
            实现方式：注解方式，编程方式

   2:SpringBoot自动装配详解：
            1：规约大于配置，实现自动化配置的目的
            2：原理是基于模式注解，@Enable注解，条件装配以及工厂加载机制（实现类是SpringFactoriesLoader，资源配置：Spring-factories）
            3：实现方式：激活自动化装配，实现自动化装配，以及配置自动化装配的实现

三：SpringBoot理解

    1：理解Spring
        Spring 的模式注解：
        Spring 的应用上下文：
        Spring 工厂加载机制：
        Spring 上线文初始化器：
        Spring Environment抽象：
        Spring 应用事件/监听：

    2：衍生技术
        SpringApplication：
            定义：是一个Spring应用的引导类，提供了便利的自定义行为方法。
            场景：嵌入式Web应用或非Web应用
            运行：SpringApplication的run方法
            准备阶段：

                配置：指定SpringBean的源头
                    Java配置类或Xml配置 用于引导BeanDefinitionLoader进行读取，并且将配置源解析加载为Spring Bean的定义

                推断：推断Web应用类型和主引导类（MainClass）
                    通过当前类路径下是否包含相关的实现类来推断相关的Web类型的包括（其实就是看有没有对应的类型存在）：
                        WebReactive：WebApplicationType.REACTIVE
                        WebServlet:WebApplicationType.SERVLET
                        非Web：WebApplicationType.NONE
                    具体通过：SpringApplication.deduceWebApplicationType方法进行的推断
                        当然我们可以手动设置当前应用类型，来拒绝推断。
                    通过线程栈来推断主类。

                加载：加载应用上下文初始化器和应用监听器
                    加载应用上下文初始化器（ApplicationInitializer）：通过工厂模式实现类是SpringFactoryLoader通过加载资源文件（META-INF/spring-factories）其中可能存在多个实现了同一个接口的实现类，
                        所以在初始化的时候可以通过Order接口或注解区分排序，进出初始化进行加载的顺序读取。
                    加载应用监听器（ApplicationListener）：
                        工厂化加载，与加载应用上下文流程一致。

             运行阶段：
                加载：SpringApplication的运行监听器
                       SpringBoot通过 SpringApplicationRunListener通过工厂方法模式进行加载相应的事件监听器
                       注意：其构造器必须传SpringApplication以及args不然会报错，因为很多是基于SpringApplication来获取必要元素的
                运行：SpringApplication的运行监听器
                监听：Spring，SpringBoot的事件
                创建：应用上下文，Environment，以及其他
                    根据准备阶段的应用类型推断创建相应的应用上下文，同时不同的应用上下文对应着不同的Environment
                失败：故障分析报告
                回调：CommandLineRunner，ApplicationRunner

                扩展：Spring的事件/监听器编程模型
                    Spring事件类型：
                            ApplicationEvent：普通事件
                            ApplicationContextEvent：应用上下文事件，基类是ApplicationEvent
                    Spring应用监听器：
                            接口编程模型：实现ApplicationListener
                            注解编程模型：@EventListener
                    Spring应用事件广播器：
                            接口：ApplicationEventMulticaster
                            实现类：SimpleApplicationEventMulticaster
                                执行模式：同步或异步的方式
        SpringApplication Builder API：
        SpringApplication 运行监听器：
        SpringApplication 参数：
        SpringApplication 故障分析：
        SpringBoot 应用事件/监听：








