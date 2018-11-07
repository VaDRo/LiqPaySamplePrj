package com.carwasher.testliqpay.service;

public class ConfigurationService {
    private static ConfigurationService _instance;
    //private String LiqPayPublicKey = "i322*********";
    //private String LiqPayPrivateKey = "mEZL2X********************************";
    private String LiqPayPublicKey = "YOUR_LIQPAY_PUBLIC_KEY";
    private String LiqPayPrivateKey = "YOUR_LIQPAY_PRIVATE_KEY";
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
