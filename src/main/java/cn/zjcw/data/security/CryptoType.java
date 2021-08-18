package cn.zjcw.data.security;

/**
 * 加密分类
 * @author 王鹏飞
 * @date 2021817
 */
public enum CryptoType {

    AES("AES", "AES"),
    DES3("DES3", "DES");


    private String code;
    private String message;

    CryptoType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static CryptoType getByCode(final String code) {

        final CryptoType[] values = CryptoType.values();

        for (final CryptoType value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
