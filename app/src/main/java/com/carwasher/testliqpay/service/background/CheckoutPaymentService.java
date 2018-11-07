package com.carwasher.testliqpay.service.background;

import android.os.AsyncTask;

import com.carwasher.testliqpay.ilisteners.ICheckoutResultListener;
import com.carwasher.testliqpay.service.ConfigurationService;
import com.carwasher.testliqpay.service.background.params.CheckoutPaymentParams;

import java.util.HashMap;

import ua.privatbank.paylibliqpay.ErrorCode;
import ua.privatbank.paylibliqpay.LiqPay;
import ua.privatbank.paylibliqpay.LiqPayCallBack;

public class CheckoutPaymentService
        extends AsyncTask<CheckoutPaymentParams, Integer, CheckoutPaymentParams>
        implements LiqPayCallBack{

    private CheckoutPaymentParams params;

    @Override
    protected CheckoutPaymentParams doInBackground(CheckoutPaymentParams... checkoutPaymentParams)
    {
        params = checkoutPaymentParams[0];
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("version", params.getVersion());
        map.put("public_key", params.getPublicKey());
        map.put("action", params.getAction());
        map.put("amount", params.getAmount());
        map.put("currency", params.getCurrency());
        map.put("description", params.getDescription());
        map.put("order_id", params.getOrderId());
        /*map.put("language", params.getLanguage());
        map.put("server_url", params.getMyServerUrl());
        map.put("card", params.getCard());
        map.put("card_exp_month", params.getCard_exp_month());
        map.put("card_exp_year", params.getCard_exp_year());
        map.put("card_exp_cvv", params.getCard_exp_cvv());*/

        LiqPay.checkout(params.getExecutionContext(), map, ConfigurationService.getInstance().getLiqPayPrivateKey(), this);
        return params;
    }

    @Override
    protected void onPostExecute(CheckoutPaymentParams params) {
        super.onPostExecute(params);
        //ICheckoutResultListener listener = params.getListener();
        //listener.checkoutResult(params);
    }

    @Override
    public void onResponseSuccess(String s) {
        params.setOperationResult(s);
        ICheckoutResultListener listener = params.getListener();
        listener.checkoutResult(params);
    }

    @Override
    public void onResponceError(ErrorCode errorCode) {
        params.setOperationError(errorCode);
        ICheckoutResultListener listener = params.getListener();
        listener.checkoutResult(params);
    }
}
