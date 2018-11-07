package com.carwasher.testliqpay.service;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.carwasher.testliqpay.ilisteners.ICheckPaymentStatusListener;
import com.carwasher.testliqpay.ilisteners.ICheckoutResultListener;
import com.carwasher.testliqpay.service.background.CheckPaymentStatusService;
import com.carwasher.testliqpay.service.background.CheckoutPaymentService;
import com.carwasher.testliqpay.service.background.params.CheckPaymentStatusParams;
import com.carwasher.testliqpay.service.background.params.CheckoutPaymentParams;
import com.carwasher.testliqpay.vo.PaymentResult;

public class LiqPayService {
    private static LiqPayService _instance;
    private LiqPayService(){}

    public static LiqPayService getInstance(){
        if (_instance == null)
        {
            _instance = new LiqPayService();
        }
        return _instance;
    }

    public void checkout(Context context, String amount2pay, String currency, ICheckoutResultListener listener){
        CheckoutPaymentParams params = new CheckoutPaymentParams(context);
        params.setAmount(amount2pay);
        params.setCurrency(currency);
        params.setListener(listener);
        new CheckoutPaymentService().execute(params);
    }

    public void checkPayment(Context context, PaymentResult paymentResult, ICheckPaymentStatusListener listener){
        CheckPaymentStatusParams params = new CheckPaymentStatusParams(context, paymentResult);
        params.setListener(listener);
        new CheckPaymentStatusService().execute(params);
    }
}
