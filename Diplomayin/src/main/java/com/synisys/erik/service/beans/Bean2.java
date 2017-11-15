package com.synisys.erik.service.beans;

public class Bean2 {
    private Bean1 bean1;

    private String systemName;

    public Bean2(String systemName) {
        this.systemName = systemName;
    }

    public Bean1 getBean1() {
        return bean1;
    }

    public void setBean1(Bean1 bean1) {
        this.bean1 = bean1;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
