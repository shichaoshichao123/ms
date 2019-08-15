                            Tomcat相关
1:如何优雅的关闭tomcat:
    在tomcat的config文件夹下面又一个叫catalina.policy（这是一个安全策略配置文件，用于控制相关的JVM权限）文件夹进行配置shutdownHook。

2：catalina.properties:
    用于控制tomcat的classLoader的，用于控制加载tomcat的相关类，如Comment classLoader等加载哪些jar包

3：logging.properties:
    是tomcat的日志配置文件，主要用于配置jvm，jdk的日志

4：service.xml:
    listener：tomcat内部提供了许多监听器类用于监听各种事件，基于监听器模式。
    globalNamingResources：全局的JNDI资源。
        service：
        engine：
        connector：服务连接器，分为阻塞和非阻塞式两种，默认实现式BIO 8版本之后采用NIO
            注意： NIO在读取请求头体和写相应体的时候也是采用阻塞的模式
        host：可以用于进行虚拟主机的配置
        context:
        valve：是tomcat内部的一个个阀门也称为过滤器，用责任链模式进行组织
        realm(领域配置):用于进行访问控制，鉴别等配置，涉及Security相关安全认证的，基于tomcat的安全配置文件（tomcat-user.xml）。
        注意：以上的几个配置是层层嵌套的，组成了一个服务的所有配置信息。

5:web.xml:
    配置了一些Tomcat一些默认的实现，是Servlet标准的部署文件，tomcat默认时间部分，如DefaultServlet等。

6:context.xml:
    全局的Context配置文件

7：ETag：相当于一个HashCode 通过最后修改时间（LM）进行静态数据加载的优化

8：lib文件夹：
    用于存放tomcat相关jar包。
9：logs文件夹：
    用于存放相关日志文件，当tomcat启动失败的时候建议多到localhost开头的日志文件。

10：tomcat部署web应用：
    方法1：将打好的war包放置到webapps目录

    方法2：修改service.xml文件：
     添加一个Context配置进行部署，其中docBase属性是指要部署项目war包所在的上级目录的根路径，可以是绝对路径也可以是相对路径。

    方法3：
        独立的context配置文件如：在conf/Catalina/localhost/abc.xml
        注意：这种独立的方式配置path属性是无效的
         该方式可以实现热部署以及热加载，在考虑性能的情况下不建议在生产环境使用

    方法四：通过项目中的Web-Info/context.xml的方式实现

11：tomcat出现乱码的时候解决方式：
    设置Connector中的URIEncoding的属性为UTF-8

12:tomcat中的连接器用的式那种线程池：
    内部使用的是Java提供的线程池

13：什么是JNDI：
    lookup：

tip:
    1:tomcat的每个xml配置文件在内部都有一个相应的类与之对应，在进行初始化启动的时候，tomcat通过解析相关的配置文件，再通过反射去调用与之对应的类进行初始化。

web技术栈：
        1:Servlet(Tomcat):
        2:WebFlux(Netty):

14:嵌入式Tomcat:
    1:tomcat的Maven插件:

    2:tomcat的API接口：
        Tomcat接口:
        Service:
        Context:
        Engine:

    3:SpringBoot嵌入式Tomcat:

15:API角度：Servlet3.0 实现web的自动装配 通过实现ServletContextInitializer
   容器的角度：插件的形式，进行嵌入式容器

