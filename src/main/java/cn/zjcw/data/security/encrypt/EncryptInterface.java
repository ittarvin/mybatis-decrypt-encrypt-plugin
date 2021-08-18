package cn.zjcw.data.security.encrypt;

import cn.zjcw.data.security.CryptoType;

/**
 * 加秘接口声明
 * @author 王鹏飞
 * @date 2021817
 */
public interface EncryptInterface {

    /**
     * 数据加密
     * @param content
     * @return
     */
    String encrypt(String content);

}
