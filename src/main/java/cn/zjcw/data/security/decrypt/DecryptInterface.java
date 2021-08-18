package cn.zjcw.data.security.decrypt;

import cn.zjcw.data.security.CryptoType;

/**
 * 解秘接口声明
 * @author 王鹏飞
 * @date 2021817
 */
public interface DecryptInterface {

    /**
     * 数据解秘
     * @param content
     * @return
     */
    String decrypt(String content);
}
