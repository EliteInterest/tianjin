package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class KeyValueInfo implements Serializable{
    public String key;
    public String value;
    public String value1;
    public String value2;
    public String code;

    public KeyValueInfo() {
    }

    public KeyValueInfo(String key, String value, String value1) {
        this.key = key;
        this.value = value;
        this.value1 = value1;
    }

    public KeyValueInfo(String key, String value, String value1, String value2) {
        this.key = key;
        this.value = value;
        this.value1 = value1;
        this.value2 = value2;
    }

    public KeyValueInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[key=" + key + ", value=" + value + "]";
    }
}
