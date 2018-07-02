package com.loki.server.utils;

public class OrderConst {

    /**
     * 0:待出租，1：已出租；2：故障中
     * @author 电池状态
     */
    public enum batteryInfoState {

        WAIT(0,"待出租"),
        USING(1,"已出租"),
        FAIL(2,"故障中");

        private int key;

        private String text;

        private batteryInfoState(int key,String text) {
            this.key=key;
            this.text=text;
        }

        public int getKey() {
            return key;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * 押金类型 0：车子押金 1：电池押金
     */
    public enum DepositType{
        DEPOSIT_VEHICLE(0,"车子押金"),
        DEPOSIT_BATTERY(1,"电池押金");

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
}
