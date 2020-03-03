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






