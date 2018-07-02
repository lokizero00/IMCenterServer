package com.loki.server.utils;

public class PayConst {
	public static final String CHARSET = "utf-8";
    public static final String FORMAT = "json";
    public static final String SIGN_TYPE = "RSA2";
    public static final String PRODUCT_CODE="QUICK_MSECURITY_PAY";

    //支付宝前缀
    public static final String ALI_CONFIG_NAME = "AliPayConfig";

    public static final String ALI_ = "AliPayConfig";

    public enum PayStyle{
        ALIPAY(1,"支付宝支付"), WXPAY(2,"微信支付"),BALANCEPAY(3,"余额支付");

        private int index;
        private String name;

        PayStyle(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }
    }

    public enum DepositType{
        DEPOSIT_VEHICLE(0,"车子押金"),
        DEPOSIT_BATTERY(1,"电池押金"),
        DEPOSIT_ALL(2,"电池和车子押金");

        private int index;
        private String name;

        DepositType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

    }

    public enum DepositReturnType{
        DEPOSIT_VEHICLE("00","非押金退还，"),
        DEPOSIT_ALL("01","押金退还");

        private String index;
        private String name;

        DepositReturnType(String index, String name) {
            this.index = index;
            this.name = name;
        }

        public String getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

    }

    public enum DepositPayBackType{
        RETURN_WAIT(0,"待退还"),
        RETURN_SUCCESS(1,"退还成功"),
        RETURN_ERROR(2,"退还失败");

        private int index;
        private String name;

        DepositPayBackType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

    }
}
