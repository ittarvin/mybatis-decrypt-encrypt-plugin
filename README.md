###  mybatis 加密，解秘插件 支持 AES，DES

#### 使用规则

##### pom.xml 添加引用

```java
<dependency>
    <groupId>com.hsbxjj.middleman</groupId>
    <artifactId>zjcw-data-security-mybatis-plugin</artifactId>
</dependency>
```



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



##### properties 密钥配置

```java
#des
slatKey=12345678Y48P3wKwEtVEx7M5
vectorKey=12345678
    
#aes
aesKey=617568c789ef9fe4aec8848866c29d2c
```
