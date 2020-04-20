
1:Spring Bean的作用域
    1：Singleton 单例，整个容器中只有一个实例对象
    2：prototype 该作用域，会针对每个getBean方法都会生成一个新的实例
    3：request 针对每个http请求生成一个新的实例
    4：session 为每个Session创建一个单独的bean实例
    5：globalSession 为每个全局的http Session（portlet环境下生效）创建相关bean实例
向Spring容器中注入对象的方式：
    1：@Configuration注解
    2：@Import/importSelector接口/importBeanDefinitionRegistrar接口
    3：BeanFactory接口

Spring的扩展方式
    设置初始化和销毁逻辑
    1：@Bean：init-method和destroy-method
    2：通过实现Bean的InitializingBean进行初始化逻辑扩展或DisposableBean进行销毁逻辑扩展
    3：使用@PostConstrcut 和 @PreDestroy 注解

    4：实现BeanPostProcessor接口并实现（用于自定义初始化前后的逻辑）
        postProcessorBeforeInitialization（在实例对象已经创建，在实例的任何初始化逻辑调用之前工作）
    和   postProcessorAfterInitialization（在实例对象已经创建，在实例的所有初始化逻辑调用之后工作）方法

2：Spring Bean的生命周期：

    1：创建：
        实例化bean及初始化属性-》通过特定Aware接口进行容器纬度的感知操作-》执行BeanPostProcessor的postProcessorBeforeInitialization方法对示实例化的Bean做一些自定义的处理逻辑
        -》如果示例话的Bean实现了InitializationBean接口就会执行对应的afterPropertiesSet方法做一些属性被设置之后的自定义逻辑-》 执行Bean类型自带的Init方法-》执行BeanPostProcessor的postProcessorAfter Initialization 进行一些Bean实例初始化之后的自定义逻辑。
        -》创建完成。
    2：销毁：
        1：若该被销毁的Bean实现了DisposableBean接口的话就一调用其Destroy方法
        2：如果该bean配置了destroy-method属性就会调用其配置好的自定的销毁方法

3：AOP：
    1：AOP的底层实现有JdkProxy（基于目标代理类和InvocationHandle接口实现动态代理）和
    cgLib（是一个动态生成代码的类库框架（ASM），以继承目标代理类的方式来实现动态代理，所以被标记成final的类是不能通过cglib实现动态代理的）

    2：由于Spring的AOP是基于getBean方法的，所以被代理的类必须被Spring IOC管理，不然就无能为力了。

