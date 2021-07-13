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
            <property name="slatKey" value="12345678Y48P3wKwEtVEx7M5"/>
            <property name="vectorKey" value="12345678"/>
        </plugin>

        <plugin interceptor="cn.zjcw.data.security.des.encrypt.AutoEncryptPlugin">
            <property name="slatKey" value="12345678Y48P3wKwEtVEx7M5"/>
            <property name="vectorKey" value="12345678"/>
        </plugin>
    </plugins>

#### java service

    @Override
    @AutoDecrypt(columns = {"bankAccountNo","bankCardPhone","idCardNo","realName"})
    public List cashout() {
        TbCashoutAutoExample tbCashoutAutoExample = new TbCashoutAutoExample();
        tbCashoutAutoExample.createCriteria().andBankAccountNoIsNotNull();

        return tbCashoutAutoMapper.selectByExample(tbCashoutAutoExample);
    }


    @Override
    @AutoEncrypt(columns = {"bankNum"})
    public int add() {

        TbCashoutAuto record = new TbCashoutAuto();
        record.setBankNum("202100713");

        return  tbCashoutAutoMapper.insertSelective(record);
    }