package com.carwasher.testliqpay.service.background.params;

import android.content.Context;

import com.carwasher.testliqpay.ilisteners.ICheckPaymentStatusListener;
import com.carwasher.testliqpay.service.ConfigurationService;
import com.carwasher.testliqpay.vo.PaymentResult;

import ua.privatbank.paylibliqpay.ErrorCode;

public class CheckPaymentStatusParams {
    private PaymentResult paymentResult;
    private String version = "3";
    private String public_key;
    private Context executionContext = null;
    private ICheckPaymentStatusListener listener;
    private String operationResult;
    private ErrorCode operationError;

    public CheckPaymentStatusParams(Context context, PaymentResult result){
        executionContext = context;
        paymentResult = result;
        public_key = ConfigurationService.getInstance().getLiqPayPublicKey();
    }

    public Context getExecutionContext(){
        return executionContext;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPublicKey() {
        return public_key;
    }

    public void setPublicKey(String public_key) {
        this.public_key = public_key;
    }

    public ICheckPaymentStatusListener getListener() {
        return listener;
    }

    public void setListener(ICheckPaymentStatusListener listener) {
        this.listener = listener;
    }

    public String getOrderId(){
        return paymentResult.getOrderId();
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public ErrorCode getOperationError() {
        return operationError;
    }

    public void setOperationError(ErrorCode operationError) {
        this.operationError = operationError;
    }


}
