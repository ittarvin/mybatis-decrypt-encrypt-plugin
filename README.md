###  mybatis 加密，解秘插件 支持 AES，DES

#### 使用规则

##### pom.xml 添加引用

```java
<dependency>
    <groupId>com.hsbxjj.middleman</groupId>
    <artifactId>zjcw-data-security-mybatis-plugin</artifactId>
</dependency>
```

#### 方法一

##### mybatis-config.xml 插件引用

```java
<plugins>
   
    <plugin interceptor="cn.zjcw.data.security.AutoDecryptPlugin">
    </plugin>

    <plugin interceptor="cn.zjcw.data.security.AutoEncryptPlugin">
    </plugin>

</plugins>
```



##### Java 代码引用

```java
@Override
@AutoEncrypt(columns = {"bankCardPhone"},
        cryptoType = CryptoType.AES)
public List<Cashout>  select(String value) {
    List<Cashout> list = cashoutMapper.selectByBankCardPhone(value);
    return list;
}
```

##### properties 密钥配置（可选）

```java
#des
slatKey=12345678Y48P3wKwEtVEx7M5
vectorKey=12345678
    
#aes
aesKey=617568c789ef9fe4aec8848866c29d2c
```

#### 方法二（推荐）

##### properties 增加shardingjdbc 配置

```java
spring.shardingsphere.datasource.ds.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds.driveClassName=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds.jdbcUrl=jdbc:mysql://ip:3306/db?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false
spring.shardingsphere.datasource.ds.username=service
spring.shardingsphere.datasource.ds.password=
spring.shardingsphere.datasource.ds.max-total=100


spring.shardingsphere.encrypt.encryptors.encryptor_aes.type=zjcwaes
spring.shardingsphere.encrypt.encryptors.encryptor_aes.props.aes.key.value=617568c789ef9fe4aec8848866c29d2c

spring.shardingsphere.encrypt.tables.tb_cashout.columns.phone_no.plainColumn=phone_no
spring.shardingsphere.encrypt.tables.tb_cashout.columns.phone_no.cipherColumn=phone_no_cipher
spring.shardingsphere.encrypt.tables.tb_cashout.columns.phone_no.encryptor=encryptor_aes



spring.shardingsphere.props.sql.show=true
spring.shardingsphere.props.query.with.cipher.column=true
```
