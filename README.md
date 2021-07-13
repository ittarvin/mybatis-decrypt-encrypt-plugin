# mybatis-decrypt-encrypt-plugin
mybatis  DES3 加解密插件


### 引用方法

#### pom.xml

        <dependency>
            <groupId>cn.zjcw</groupId>
            <artifactId>mybatis-decrypt-encrypt-plugin</artifactId>
        </dependency>

#### mybatis-config.xml

     <plugins>
        <plugin interceptor="cn.zjcw.data.security.des.decrypt.AutoDecryptPlugin">
            <property name="slatKey" value="24位字符串"/>
            <property name="vectorKey" value="8位字符串"/>
        </plugin>

        <plugin interceptor="cn.zjcw.data.security.des.encrypt.AutoEncryptPlugin">
            <property name="slatKey" value=""/>
            <property name="vectorKey" value=""/>
        </plugin>
    </plugins>

#### java service

    @Override
    @AutoDecrypt(columns = {"bankAccountNo","bankCardPhone","idCardNo","realName"})
    public List cashout() {
       ......
    }


    @Override
    @AutoEncrypt(columns = {"bankNum"})
    public int add() {
       ......
    }