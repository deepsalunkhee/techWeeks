# Spring Dependency Injection (DI) Cheat Sheet

## Manual (Plain Java) DI

- **Concept**: No container – dependencies are created and injected manually (typically via constructors or setters).
    
- **Example**:
    
    ```java
    class Address { 
        private String city; 
        public Address(String city) { this.city = city; } 
        public String getCity() { return city; }
    }
    
    class Student {
        private String name;
        private Address address;
        // Constructor injection (manual)
        public Student(String name, Address address) {
            this.name = name;
            this.address = address;
        }
        public String toString() { return name + " from " + address.getCity(); }
    }
    
    public class MainApp {
        public static void main(String[] args) {
            // Manually create and wire objects
            Address addr = new Address("Pune");
            Student student = new Student("Alice", addr);
            System.out.println(student);
        }
    }
    ```
    
    As one tutorial notes, _“in core Java, DI is achieved manually by passing dependencies through the constructor or setter methods.”_. Manual DI works but couples code and is hard to scale or test.
    

## XML-based Configuration

- **Bean Definition**: In `applicationContext.xml` (or similar), declare beans and their dependencies.
    
- **Setter Injection**: Use `<property>` with `ref`. For example:
    
    ```xml
    <bean id="indexService" class="com.example.IndexService"/>
    <bean id="indexApp" class="com.example.IndexApp">
        <property name="service" ref="indexService"/>
    </bean>
    ```
    
    This wires `indexService` into `IndexApp`. (By default, beans are singleton.).
    
- **Constructor Injection**: Use `<constructor-arg>`. Example:
    
    ```xml
    <bean id="indexService" class="com.example.IndexService"/>
    <bean id="indexApp" class="com.example.IndexApp">
        <constructor-arg ref="indexService"/>
    </bean>
    ```
    
    This calls the matching constructor of `IndexApp` with the `IndexService` bean.
    
- **Factory Methods**: Spring supports factory-method injection. For a _static_ factory:
    
    ```xml
    <bean id="myBean" class="com.example.MyFactory" factory-method="create">
        <constructor-arg value="42"/>
    </bean>
    ```
    
    Or an _instance_ factory:
    
    ```xml
    <bean id="factoryBean" class="com.example.FactoryBean"/>
    <bean id="myBean" factory-bean="factoryBean" factory-method="create">
        <constructor-arg value="42"/>
    </bean>
    ```
    
    These use the factory’s `create` method to produce the bean. The Baeldung tutorial demonstrates both static and instance factory-method usage in XML.
    
- **Bean Scope & Lifecycle**: You can set `<bean scope="prototype">` for non-singleton. (By default `<bean>` is singleton.) The XML also supports `lazy-init="true"` to defer instantiation until first use.
    

## Annotation-based Configuration (Component Scanning + Autowiring)

- **Stereotypes**: Annotate classes with `@Component` (or `@Service`, `@Repository`, `@Controller`) to mark them as Spring-managed beans. Use `@ComponentScan` (in XML or Java config) to detect them. In Spring Boot, `@SpringBootApplication` enables component scanning by default.
    
- **@Autowired**: The primary Spring DI annotation for injection by type. It can go on fields, constructors, or setter methods. For example, field injection:
    
    ```java
    @Component
    public class FooService {
        @Autowired
        private FooRepository repo;  // injected by type
    }
    ```
    
    Spring injects the `FooRepository` bean into `FooService`.
    
- **Injection Types**:
    
    - _Field injection_: `@Autowired private Dependency dep;` (no setter needed).
        
    - _Setter injection_:
        
        ```java
        @Component
        public class FooService {
            private FooFormatter formatter;
            @Autowired 
            public void setFormatter(FooFormatter f) { 
                this.formatter = f; 
            }
        }
        ```
        
    - _Constructor injection_: (recommended for required dependencies)
        
        ```java
        @Component
        public class FooService {
            private final FooFormatter formatter;
            @Autowired  // optional if only one constructor (Spring 4.3+)
            public FooService(FooFormatter formatter) {
                this.formatter = formatter;
            }
        }
        ```
        
- **@Qualifier and @Primary**: When multiple beans of the same type exist, Spring needs help choosing. Use `@Qualifier("beanName")` on the injection point or on the bean definition to disambiguate. For example:
    
    ```java
    @Component("fooFormatter")
    class FooFormatter implements Formatter { ... }
    @Component("barFormatter")
    class BarFormatter implements Formatter { ... }
    
    @Component
    class FormatterUser {
        @Autowired
        @Qualifier("fooFormatter")  // specify which implementation to inject
        private Formatter formatter;
    }
    ```
    
    This avoids the “NoUniqueBeanDefinitionException” by naming the bean. Alternatively, mark one bean as `@Primary` so it’s chosen by default. E.g.:
    
    ```java
    @Component @Primary class MainService implements Service {...}
    @Component class OtherService implements Service {...}
    ```
    
    Here `MainService` will be injected when `Service` is autowired without qualifier.
    
- **@Inject and @Resource**: Spring also supports standard JSR-330 `@Inject` (works like `@Autowired`) and JSR-250 `@Resource` (by-name injection). For example:
    
    ```java
    @Component
    public class MovieLister {
        @Resource(name="myMovieFinder")
        private MovieFinder finder; 
        // Spring will inject the bean named "myMovieFinder" here:contentReference[oaicite:11]{index=11}.
    }
    ```
    
    If `@Resource` has no name, Spring uses the field/property name as the bean name.
    

## Java-based Configuration (`@Configuration`, `@Bean`)

- **Configuration Classes**: Annotate a class with `@Configuration` to indicate it contains bean definitions. Inside it, use `@Bean` on methods whose return values are beans. This replaces XML. Spring docs explain: _“The @Bean annotation is used to indicate that a method instantiates, configures, and initializes a new object to be managed by the Spring IoC container.”_
    
- **Example**:
    
    ```java
    @Configuration
    public class AppConfig {
        @Bean
        public Service myService() {
            return new ServiceImpl();  // Spring manages this bean
        }
        @Bean
        public Client myClient(Service myService) {
            return new Client(myService);  // Spring injects the myService bean
        }
    }
    ```
    
    Here `myClient` depends on `myService`; Spring automatically injects by calling `myService()` method. A `@Configuration` class allows inter-bean dependencies via method calls.
    
- **@ComponentScan**: Often used with `@Configuration` to auto-detect `@Component` classes:
    
    ```java
    @Configuration
    @ComponentScan("com.example.app")
    public class AppConfig { }
    ```
    
- **Scopes & Lazy**: You can scope `@Bean` methods with `@Scope` (e.g. `@Scope("prototype")` for a new instance each time) or `@Lazy` (to defer instantiation until first needed) just as with component beans.
    

## Injection Styles (Field vs Setter vs Constructor)

- **Field Injection**: Put `@Autowired` on a private field. Example:
    
    ```java
    @Component
    public class FooService {
        @Autowired 
        private FooRepository repo;
        // (Spring injects FooRepository by type)
    }
    ```
    
- **Setter Injection**: `@Autowired` on the setter method. Example:
    
    ```java
    @Component
    public class FooService {
        private FooRepository repo;
        @Autowired
        public void setRepository(FooRepository repo) {
            this.repo = repo;
        }
    }
    ```
    
- **Constructor Injection**: `@Autowired` (or implicit) on the constructor. Example:
    
    ```java
    @Component
    public class FooService {
        private final FooRepository repo;
        @Autowired
        public FooService(FooRepository repo) {
            this.repo = repo;
        }
    }
    ```
    
    Constructor injection is often preferred for mandatory dependencies (and for immutability). Spring will throw an exception if it can’t satisfy a required constructor argument.
    

## Advanced DI Features

- **@Primary**: Marks a bean as the default when multiple candidates exist. If one bean is `@Primary`, it’s injected in preference to others of the same type. Example:
    
    ```java
    @Bean @Primary
    public DataSource defaultDataSource() { ... }
    @Bean
    public DataSource backupDataSource() { ... }
    ```
    
    Here `defaultDataSource` is chosen when injecting `DataSource` without qualifier.
    
- **@Profile**: Beans can be included only for certain profiles. Annotate a bean or configuration class with `@Profile("dev")`, for example, to activate it only when the “dev” profile is active. Profiles are set via `spring.profiles.active` (environment variable, JVM arg, properties, etc). Example:
    
    ```java
    @Component
    @Profile("dev")
    public class DevDatasourceConfig { ... }
    ```
    
    This bean is registered only in the “dev” environment. You can also use logical operators (e.g. `@Profile("prod & !test")`).
    
- **@Conditional and Spring Boot Conditionals**: Spring Core supports `@Conditional` (with a custom `Condition` class) to decide bean registration at runtime. Spring Boot adds many convenience conditionals like `@ConditionalOnProperty`, `@ConditionalOnClass`, etc. For example:
    
    ```java
    @Bean
    @ConditionalOnProperty(name="featureX.enabled", havingValue="true")
    public FeatureXService featureXService() { ... }
    ```
    
    This bean is created only if the `featureX.enabled=true` property is set. (Spring Boot uses these in its auto-configuration.)
    
- **@Lazy Initialization**: By default, Spring creates singleton beans eagerly at startup. Annotate a bean or configuration with `@Lazy` to defer instantiation until first needed. Example:
    
    ```java
    @Bean @Lazy
    public HeavyService heavyService() { ... }
    ```
    
    With this, `HeavyService` won’t be created until `getBean(HeavyService.class)` is first called. You can also put `@Lazy` on a `@Configuration` class to make all its beans lazy.
    
- **Scopes**: Spring supports multiple bean scopes (singleton, prototype, request, session, application, websocket). The default is **singleton** (one instance per Spring context).
    
    - **prototype**: Every `getBean()` call returns a new instance.
        
        ```java
        @Bean
        @Scope("prototype")
        public MyBean myBean() { return new MyBean(); }
        ```
        
        Here `myBean()` returns a new object each time.
        
    - **request/session/application**: Available in web contexts (one per HTTP request, HTTP session, or ServletContext, respectively). Use `@Scope("request")` etc in web-aware config.
        
- **Factory Methods (Java config)**: You can call factory methods inside `@Bean` methods. For example, to use a static factory:
    
    ```java
    @Bean
    public Service myService() {
        return ServiceFactory.create();  // static factory
    }
    ```
    
    Or use an instance factory bean:
    
    ```java
    @Bean public FactoryBean factoryBean() { return new FactoryBean(); }
    @Bean
    public Service myService() {
        return factoryBean().create();  // calls another @Bean
    }
    ```
    
    Spring will manage these beans and their initialization.
    

## Lifecycle Callbacks and Method Injection

- **@PostConstruct and @PreDestroy**: Annotate a method to run after bean creation or before bean destruction (for singleton beans in a context close). Example:
    
    ```java
    @Component
    public class DatabaseInitializer {
        @Autowired private UserRepository repo;
        
        @PostConstruct
        public void initData() {
            // populate default users, etc.
        }
        
        @PreDestroy
        public void cleanup() {
            // close resources, etc.
        }
    }
    ```
    
    Spring calls `@PostConstruct` once after the bean is fully initialized, and `@PreDestroy` just before removing the bean.
    
- **@Lookup (Method Injection)**: Use `@Lookup` on an abstract or concrete method to have Spring override it and return a bean of the method’s return type on each call. This is commonly used to get a fresh prototype bean from a singleton. Example:
    
    ```java
    @Component
    public class TaskService {
        @Lookup
        public TaskProcessor getProcessor() { 
            /* Spring overrides this to return a new TaskProcessor */ 
            return null; 
        }
        public void process() {
            TaskProcessor proc = getProcessor(); // always a new instance (if TaskProcessor is prototype-scoped)
            proc.execute();
        }
    }
    ```
    
    Here, each call to `getProcessor()` returns a new `TaskProcessor` bean from the context.
    

## Spring Boot Enhancements

- **@SpringBootApplication**: A convenience annotation that combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. In a Spring Boot app, you typically don’t need XML or much manual config – the application automatically scans for components and wires beans. For example:
    
    ```java
    @SpringBootApplication
    public class MyApp {
        public static void main(String[] args) {
            SpringApplication.run(MyApp.class, args);
        }
    }
    ```
    
    This bootstraps Spring and auto-scans the package and subpackages for `@Component` classes, so you can simply use `@Autowired` in your beans.
    
- **Auto-configuration**: Spring Boot auto-configures many common beans (DataSource, EntityManager, etc.) based on classpath and properties. You can still override or define beans with `@Bean`. The auto-config support often uses conditionals internally (e.g. `@ConditionalOnClass`).
    
- **Configuration Properties**: In Boot, you can inject settings via `@Value` or bind them to `@ConfigurationProperties` beans, which themselves are managed by Spring. For DI, this means you can have beans whose fields are injected from `application.properties` or YAML.
    
