
### 需求分析
1. 注册/登录
2. 用户管理（管理员）
3. 用户校验

### 技术选型
前端：三件套 + React + 组件库 Ant Design + Umi + Ant Design Pro
后端: java + spring + spring mvc + mybatis + mybatis-pro + springboot，mysql
部署：服务器/容器（平台）

### 开发过程
#### 初始化
##### 前端初始化 
1. Ant Design Pro框架
2. 引入组件
3. 项目瘦身

##### 后端初始化
1. 准备环境（mysql）
	1. 本地安装mysql
	2. 使用云数据库
	3. docker容器
2. 初始化后端项目，引入框架
	1. github上拉现成代码（不建议）
	2. spring官网去生成项目
	3. IDEA中创建项目

###### SpringBoot（[Spring | Home](https://spring.io/)）

快速启动/快速集成项目，不需要自己写xml配置文件，

需要导入的依赖
- lomhok
- springweb
- mysqldriver
- configuration-processor
- mybatis

###### Mybatis-Plus[MyBatis-Plus 🚀 为简化开发而生](https://baomidou.com/)
持久层框架(操作数据库)

Mybatis是对JDBC的封装，Mybatis-Plus是对Mybatis的增强，也就是说会默认生成一些常用的crud语言，不用专门去写SQL语句。

这里写一个简单的小demo:

先创建一个用户表
```sql
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
```

将Mybatis-plus依赖导入到你springboot项目下的xml位置
```xml
<dependency>  
    <groupId>com.baomidou</groupId>  
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>  
    <version>3.5.12</version>  
</dependency>
```

下面编写一个实体类
```java
@Data
@TableName("`user`")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

下面编写一个接口类继承BaseMapper类
```java
public interface UserMapper extends BaseMapper<User> {  
  
}
```

下面编写一个测试类
```java
@SpringBootTest  
public class SampleTest {  
    @Autowired  
    private UserMapper userMapper;  
  
    @Test  
    public void testSelect() {  
       System.out.println(("----- selectAll method test ------"));  
       List<User> userList = userMapper.selectList(null);  
       Assert.isTrue(5 == userList.size(), "");  
       userList.forEach(System.out::println);  
    }  
}
```


###### 数据库配置文件写法
application.yml
```java
spring:  
  application:  
    name: userCenter  
  datasource:  
    driver-class-name: com.mysql.cj.jdbc.Driver  
    url: jdbc:mysql://localhost:3306/数据库名  
    username: root  
    password: 密码  
server:  
  port: 8080 
```


##### 数据库设计
用户表：
* id（主键）
* user_name(昵称)
* avatarUrl头像
* gender(性别)
* account(账号)
* password(密码)
* phone(电话)
* email(邮箱)
* is_valid(是否有效，是否被封号等)
---
* create_time(创建时间)
* update_time更新时间(数据更新时间)
* is_delete(是否删除、表示逻辑删除【不从表中删除，而是通过01来判断】)

建表语句
```sql
CREATE TABLE IF NOT EXISTS `user` (  
                                      `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',  
                                      `user_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户昵称',  
                                      `avatar_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '头像URL',  
                                      `gender` TINYINT NULL DEFAULT NULL COMMENT '性别(0-未知 1-男 2-女)',  
                                      `login_account` VARCHAR(50) NULL COMMENT '登录账号',  
                                      `user_password` VARCHAR(255) NULL COMMENT '密码(建议存储加密后的密文)',  
                                      `phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '手机号',  
                                      `email` VARCHAR(255) NULL DEFAULT NULL COMMENT '邮箱',  
                                      `user_status` TINYINT NULL DEFAULT 1 COMMENT '是否有效(0-封号 1-正常)',  
                                      `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  
                                      `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  
                                      `is_delete` TINYINT NULL DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',  
                                      PRIMARY KEY (`id`),  
                                      UNIQUE KEY `uk_account` (`login_account`),  
                                      KEY `idx_phone` (`phone`),  
                                      KEY `idx_email` (`email`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
```

#### 登录/注册
```text

userCenter/
├── .idea/                          # IDE配置目录
├── .mvn/                           # Maven Wrapper配置
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.chengfu.usercenter/  # 注意：实际应为com/chengfu/usercenter
│   │   │       ├── controller/      # 控制器
│   │   │       ├── mapper/         # MyBatis Mapper接口
│   │   │       ├── model/          # 数据实体
│   │   │       ├── service/        # 业务服务
│   │   │       ├── utils/          # 工具类
│   │   │       └── UserCenterApplication.java  # 启动类
│   │   └── resources/
│   │       ├── static/             # 静态资源
│   │       ├── templates/          # 模板文件
│   │       └── application.yml     # 配置文件
│   └── test/
│       └── java/
│           └── com.chengfu.usercenter/  # 测试包
│               └── UserCenterApplicationTests.java  # 测试类
├── target/                         # 编译输出目录
├── .gitattributes                  # Git属性配置
├── .gitignore                      # Git忽略规则
├── HELP.md                         # 帮助文档
├── mvnw                            # Maven Wrapper脚本(Linux)
├── mvnw.cmd                        # Maven Wrapper脚本(Windows)
└── pom.xml                         # Maven项目配置
```

###### 后端
**自动生成器的使用**

使用MybatiX插件生成实体类、对应的Mapper接口、实现类、实现接口.
1. 自动生成domain
2. mapper(操作数据库对象)
3. mapper.xml(mapper与数据库之间的关联)
4. service(增删改查)
5. servicelmpl(service实现类)


**注册逻辑**
1. 用户在前端输入账户和密码以及校验码
2. 后端需要校验账户的账号、密码、校验密码是否符合要求
	1. 账号不小于4位
	2. 密码不小于8位(不加强校验)
	3. 账户不能重复
	4. 账户不好含特殊字符
	5. 密码和校验密码相同
	6. 其他
3. 对密码进行加密(密码不要明文存储到数据库中)
4. 向数据库插入用户数据


**登录功能**
**接口设计**
接受参数用户账户，密码
请求头：POST
请求体：JSON
返回值：用户信息（脱敏）


**登录逻辑**
1. 用户在前端输入账号和密码传输到后端
2. 后端接受用户输入的账号密码进行校验
3. 后端对用户账号、密码和数据库中的密文密码进行比对
4. 用户信息脱敏，隐藏敏感信息
5. 如果校验正确，那么就可以将用户存储到session中


#### 用户管理
设计关键:用户鉴权
1. 用户查询
2. 用户删除

#### 用户登出
将用户登录态的session信息移除
