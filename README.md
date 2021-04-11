## LogAspect类

LogAspect类用于日志记录



@Aspect注解

标注LogAspect为Aspect类



方法增强

@Before

@After

@AfterReturning



内部类RequestLog

用于封装访问者的IP，以及访问的URL，访问的方法，和方法参数



> JoinPoint类

JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象.

通过JoinPoint类获取执行的方法的各种信息

**JoinPoint类常用API**

| 方法名                    | 功能                                                         |
| ------------------------- | ------------------------------------------------------------ |
| Signature getSignature(); | 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息 |
| Object[] getArgs();       | 获取传入目标方法的参数对象                                   |
| Object getTarget();       | 获取被代理的对象                                             |
| Object getThis();         | 获取代理对象                                                 |



>  ServletRequestAttributes类

用于获取RequestURL（URL）和RemoteAddr（IP）

通过ServletRequestAttributes类获取当前Request，HttpServletRequest获取请求信息（请求头、请求行、参数）。

```java
ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
HttpServletRequest request = attributes.getRequest();
```







## ControllerExceptionHandler类&NotFoundException

通过@ControllerAdvice和@ExceptionHandler自定义异常处理类，将所有错误信息返回到指定的错误页面。



@ExceptionHandler(Exception.class)表示拦截所有异常



```java
if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
    throw e;
}
```

找到带有ResponseStatus注解的类，不去接管这个异常类。



> @ResponseStatus

带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析。可以实现自定义的一些异常,同时在页面上进行显示。





## LoginInterceptor类

登陆拦截器



实现HandlerInterceptor接口，重写preHandle方法，通过session中是否有用户信息来判断，如果为NULL，页面重定向到登录界面，并返回false，否则返回true。





## WebConfig类

实现WebMvcConfigurer接口，重写addInterceptors，将自定义的拦截规则注册到Spring配置中，并告诉拦截器拦截那些请求，不拦截那些请求



在Spring Boot 1.5版本都是靠重写WebMvcConfigurerAdapter的方法来添加自定义拦截器，消息转换器等。SpringBoot 2.0 后，该类被标记为@Deprecated。因此我们只能靠实现WebMvcConfigurer接口来实现自己定义的Handler，Interceptor，ViewResolver，MessageConverter。



> WebMvcConfigurer

**常用的方法：**

接口中都有默认的实现

```java
/* 拦截器配置 */
void addInterceptors(InterceptorRegistry var1);
/* 视图跳转控制器 */
void addViewControllers(ViewControllerRegistry registry);
/*静态资源处理*/
void addResourceHandlers(ResourceHandlerRegistry registry);
/* 默认静态资源处理器 */
void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
/* 这里配置视图解析器*/
void configureViewResolvers(ViewResolverRegistry registry);
/* 配置内容裁决的一些选项*/
void configureContentNegotiation(ContentNegotiationConfigurer configurer);
/** 解决跨域问题 **/
public void addCorsMappings(CorsRegistry registry) ;
```





## POJO

JPA的注解

**1 ） @Entity(name="EntityName")**
必须 ,name 为可选 , 对应数据库中一的个表 

**2 ） @Table(name="",catalog="",schema="")**
可选 , 通常和 @Entity 配合使用 , 只能标注在实体的 class 定义处 , 表示实体对应的数据库表的信息
name: 可选 , 表示表的名称 . 默认地 , 表名和实体名称一致 , 只有在不一致的情况下才需要指定表名
catalog: 可选 , 表示 Catalog 名称 , 默认为 Catalog("").
schema: 可选 , 表示 Schema 名称 , 默认为 Schema("").

 **3 ） @id**
必须
@id 定义了映射到数据库表的主键的属性 , 一个实体只能有一个属性被映射为主键 . 置于 getXxxx() 前 .

**4 ）@GeneratedValue(strategy=GenerationType,generator="")**
可选
strategy: 表示主键生成策略 , 有 AUTO,INDENTITY,SEQUENCE 和 TABLE 4 种 , 分别表示让 ORM 框架自动选择 ,
根据数据库的 Identity 字段生成 , 根据数据库表的 Sequence 字段生成 , 以有根据一个额外的表生成主键 , 默认为 AUTO
generator: 表示主键生成器的名称 , 这个属性通常和 ORM 框架相关 , 例如 ,Hibernate 可以指定 uuid 等主键生成方式 .

**5 ）@Basic(fetch=FetchType,optional=true)**
可选
@Basic 表示一个简单的属性到数据库表的字段的映射 , 对于没有任何标注的 getXxxx() 方法 , 默认即为 @Basic
fetch: 表示该属性的读取策略 , 有 EAGER 和 LAZY 两种 , 分别表示主支抓取和延迟加载 , 默认为 EAGER.
optional: 表示该属性是否允许为 null, 默认为 true

**6 ) @Column**
可选
@Column 描述了数据库表中该字段的详细定义 , 这对于根据 JPA 注解生成数据库表结构的工具非常有作用 .
name: 表示数据库表中该字段的名称 , 默认情形属性名称一致
nullable: 表示该字段是否允许为 null, 默认为 true
unique: 表示该字段是否是唯一标识 , 默认为 false
length: 表示该字段的大小 , 仅对 String 类型的字段有效
insertable: 表示在 ORM 框架执行插入操作时 , 该字段是否应出现 INSETRT 语句中 , 默认为 true
updateable: 表示在 ORM 框架执行更新操作时 , 该字段是否应该出现在 UPDATE 语句中 , 默认为 true. 对于一经创建就不可以更改的字段 , 该属性非常有用 , 如对于 birthday 字段 .
columnDefinition: 表示该字段在数据库中的实际类型 . 通常 ORM 框架可以根据属性类型自动判断数据库中字段的类型 , 但是对于 Date 类型仍无法确定数据库中字段类型究竟是 DATE,TIME 还是 TIMESTAMP. 此外 ,String 的默认映射类型为 VARCHAR, 如果要将 String 类型映射到特定数据库的 BLOB 或 TEXT 字段类型 , 该属性非常有用 .

**7 )@Transient**
可选
@Transient 表示该属性并非一个到数据库表的字段的映射 ,ORM 框架将忽略该属性 .
如果一个属性并非数据库表的字段映射 , 就务必将其标示为 @Transient, 否则 ,ORM 框架默认其注解为 @Basic

**8 ) @ManyToOne(fetch=FetchType,cascade=CascadeType )**
可选
@ManyToOne 表示一个多对一的映射 , 该注解标注的属性通常是数据库表的外键
optional: 是否允许该字段为 null, 该属性应该根据数据库表的外键约束来确定 , 默认为 true
fetch: 表示抓取策略 , 默认为 FetchType.EAGER
cascade: 表示默认的级联操作策略 , 可以指定为 ALL,PERSIST,MERGE,REFRESH 和 REMOVE 中的若干组合 , 默认为无级联操作
targetEntity: 表示该属性关联的实体类型 . 该属性通常不必指定 ,ORM 框架根据属性类型自动判断 targetEntity.


**9) @JoinColumn**
可选
@JoinColumn 和 @Column 类似 , 介量描述的不是一个简单字段 , 而一一个关联字段 , 例如 . 描述一个 @ManyToOne 的字段 .
name: 该字段的名称 . 由于 @JoinColumn 描述的是一个关联字段 , 如 ManyToOne, 则默认的名称由其关联的实体决定 .
例如 , 实体 Order 有一个 user 属性来关联实体 User, 则 Order 的 user 属性为一个外键 ,
其默认的名称为实体 User 的名称 + 下划线 + 实体 User 的主键名称


**10) @OneToMany(fetch=FetchType,cascade=CascadeType)**
可选
@OneToMany 描述一个一对多的关联 , 该属性应该为集体类型 , 在数据库中并没有实际字段 .
fetch: 表示抓取策略 , 默认为 FetchType.LAZY, 因为关联的多个对象通常不必从数据库预先读取到内存
cascade: 表示级联操作策略 , 对于 OneToMany 类型的关联非常重要 , 通常该实体更新或删除时 , 其关联的实体也应当被更新或删除
例如 : 实体 User 和 Order 是 OneToMany 的关系 , 则实体 User 被删除时 , 其关联的实体 Order 也应该被全部删除

**11 )@OneToOne(fetch=FetchType,cascade=CascadeType)**
可选
@OneToOne 描述一个一对一的关联
fetch: 表示抓取策略 , 默认为 FetchType.LAZY
cascade: 表示级联操作策略

**12 ) @ManyTo Many**
可选
@ManyToMany 描述一个多对多的关联 . 多对多关联上是两个一对多关联 , 但是在 ManyToMany 描述中 , 中间表是由 ORM 框架自动处理
targetEntity: 表示多对多关联的另一个实体类的全名 , 例如 :package.Book.classmappedBy: 表示多对多关联的另一个实体类的对应集合属性名称



**cascade属性： 指定级联操作的行为(可多选)**

- **CascadeType.PERSIST**：级联新增（又称级联保存）：对A对象保存时也会对B对象进行保存。并且，只有A类新增时，会级联B对象新增。若B对象在数据库存在则抛异常。对应`EntityManager`的`presist`方法。
- **CascadeType.MERGE**：级联合并（级联更新）：指A类新增或者变化，会级联B对象（新增或者变化）。对应`EntityManager`的`merge`方法。
- **CascadeType.REMOVE**：级联删除：只有A类删除时，会级联删除B类,即在设置的那一端进行删除时，另一端才会级联删除。对应`EntityManager`的`remove`方法。
- **CascadeType.REFRESH**：级联刷新：获取A对象时也重新获取最新的B对象。对`EntityManager`的`refresh(object)`方法。即会重新查询数据库里的最新数据（用的比较少）
- **CascadeType.DETACH**：级联分离。
- **CascadeType.ALL**：级联所有操作。





## dao

**JpaRepository接口**

```java
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>
```

可以看出JpaRepository继承了接口PagingAndSortingRepository和QueryByExampleExecutor。而，PagingAndSortingRepository又继承CrudRepository。
因此，JpaRepository接口同时拥有了基本CRUD功能以及分页功能。

当我们需要定义自己的Repository的时候，我们可以继承JpaRepository，从而获得Spring为我们预先定义的多种基本数据操作方法。


 @Modifying

这个注解是通知jpa，这是一个update或者delete操作，在更新或者删除操作时，此注解必须加，否则会抛出异常，必须加事务，不加事务，会报错

```java
@Transactional
@Modifying
@Query("update Blog b set b.views = b.views+1 where b.id = ?1")
int updateViews(Long id);
```



**JPA JPQL简介**

JPQL(Java持久性查询语言)是一种面向对象的查询语言，用于对持久实体执行数据库操作。 JPQL不使用数据库表，而是使用实体对象模型来操作SQL查询。 这里，JPA的作用是将JPQL转换为SQL。因此，它为开发人员提供了一个处理SQL任务的简单方式。

jpql不支持insert



## service

**Pageable分页工具**

导入包：**import** org.springframework.data.domain.Pageable;



Pageable 是Spring Data库中定义的一个接口，**用于构造翻页查询**，是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息（例如pageNumber、pageSize等），这样，Jpa就能够通过pageable参数来得到一个带分页信息的Sql语句。



我们只需要在方法的参数中直接定义一个pageable类型的参数，当Spring发现这个参数时，Spring会自动的根据request的参数来组装该pageable对象

Spring支持的request参数如下：

- page，第几页，从0开始，默认为第0页
- size，每一页的大小，默认为20
- sort，排序相关的信息

```java
@RequestMapping("list")
public Page<T> getEntryByPageable(@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) 
                                    Pageable pageable) {
    return dao.findAll(pageable);
}
```





**Specification**

Spring Data JPA支持JPA2.0的Criteria查询，相应的接口是JpaSpecificationExecutor。Criteria 查询：是一种类型安全和更面向对象的查询 。

这个接口基本是围绕着Specification接口来定义的， Specification接口中只定义了如下一个方法：

```java
Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb); 
```

* root：查询的根对象（查询的任何属性都可以从根对象中获取）
* CriteriaQuery：顶层查询对象，自定义查询方式（了解：一般不用）
* CriteriaBuilder：查询的构造器，封装了很多的查询条件，就相当于条件或者是条件组合。



JpaSpecificationExecutor 接口具有方法

```java
//查询全部，分页
//pageable：分页参数
//返回值：分页pageBean（page：是springdatajpa提供的）
Page<T> findAll(Specification<T> spec, Pageable pageable);

List<T> findAll(Specification<T> spec);    //不分页按条件查询  
```



CriteriaBuilder.equal()方法

进行精准的匹配，第一个参数：需要比较的属性。第二个参数：当前需要比较的取值。



CriteriaBuilder.like()方法

查询方式：模糊匹配

```java
predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
```



精确匹配,模糊搜索,in范围查询示例

```java
@PostMapping("/get")
    public List<Account> get(@RequestBody AccountRequest request) {
        Specification<Account> specification = new Specification<Account>() {

            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                //所有的断言 及条件
                List<Predicate> predicates = new ArrayList<>();
                //精确匹配id pwd
                if (request.getId() != null) {
                    predicates.add(builder.equal(root.get("id"), request.getId()));
                }
                if (request.getPwd() != null) {
                    predicates.add(builder.equal(root.get("password"), request.getPwd()));
                }
                //模糊搜索 name
                if (request.getName() != null && !request.getName().equals("")) {
                    predicates.add(builder.like(root.get("username"), "%" + request.getName() + "%"));
                }
                if (request.getEmail() != null && !request.getEmail().equals("")) {
                    predicates.add(builder.like(root.get("email"), "%" + request.getEmail() + "%"));
                }
                //in范围查询
                if (request.getTypes() != null) {
                    CriteriaBuilder.In<Object> types = builder.in(root.get("type"));
                    for (Integer type : request.getTypes()) {
                        types = types.value(type);
                    }
                    predicates.add(types);
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<Account> accounts = repository.findAll(specification);

        return accounts;
    }
```



[java-jpa-criteriaBuilder使用入门](https://blog.csdn.net/ID_Kong/article/details/70225032)
