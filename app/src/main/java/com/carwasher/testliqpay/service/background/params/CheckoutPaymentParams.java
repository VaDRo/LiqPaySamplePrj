package com.carwasher.testliqpay.service.background.params;

import android.content.Context;

import com.carwasher.testliqpay.ilisteners.ICheckoutResultListener;
import com.carwasher.testliqpay.service.ConfigurationService;

import ua.privatbank.paylibliqpay.ErrorCode;

public class CheckoutPaymentParams {
    private String version = "3";
    private String public_key;
    private String action = "pay";
    private String amount = "0.01";
    private String currency = "uah";
    private String description = "account top-up";
    private String order_id;        //Generate new value of order_id on each call to LiqPay service
    private String language = "ru";
    private String my_server_url = "http://washercar.com/";
    //TODO: remove this sample data
    /*private String card = "5457095645459696";
    private String card_exp_month = "11";
    private String card_exp_year = "17";
    private String card_exp_cvv = "171";*/
    private Context executionContext = null;
    private String operationResult;
    private ErrorCode operationError;
    private ICheckoutResultListener listener;

    public CheckoutPaymentParams(Context context){
        executionContext = context;
        public_key = ConfigurationService.getInstance().getLiqPayPublicKey();
        order_id = String.valueOf(Math.round(Math.random()*10000));
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMyServerUrl() {
        return my_server_url;
    }

    public void setMyServerUrl(String server_url) {
        this.my_server_url = server_url;
    }

    /*public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCard_exp_month() {
        return card_exp_month;
    }

    public void setCard_exp_month(String card_exp_month) {
        this.card_exp_month = card_exp_month;
    }

    public String getCard_exp_year() {
        return card_exp_year;
    }

    public void setCard_exp_year(String card_exp_year) {
        this.card_exp_year = card_exp_year;
    }

    public String getCard_exp_cvv() {
        return card_exp_cvv;
    }

    public void setCard_exp_cvv(String card_exp_cvv) {
        this.card_exp_cvv = card_exp_cvv;
    }*/

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

    public ICheckoutResultListener getListener() {
        return listener;
    }

    public void setListener(ICheckoutResultListener listener) {
        this.listener = listener;
    }
}
