package com.carwasher.testliqpay.service;

public class ConfigurationService {
    private static ConfigurationService _instance;
    //private String LiqPayPublicKey = "i322*********";
    //private String LiqPayPrivateKey = "mEZL2X********************************";
    private String LiqPayPublicKey = "i90401010173";
    private String LiqPayPrivateKey = "C9R2kSALAqSZOjuzUZZixwHeTczTlWW2XFZcg0li";
    private int RetryInterval = 10000;     //Retry interval, milliseconds

    private ConfigurationService() {    }

    public static ConfigurationService getInstance() {
        if (_instance == null)
            _instance = new ConfigurationService();
        return _instance;
    }

    public String getLiqPayPrivateKey(){
        return LiqPayPrivateKey;
    }

    public String getLiqPayPublicKey() {
        return LiqPayPublicKey;
    }

    public int getRetryInterval() { return RetryInterval; }
}
