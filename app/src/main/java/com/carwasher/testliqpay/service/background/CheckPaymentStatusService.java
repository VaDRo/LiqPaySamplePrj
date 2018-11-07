package com.carwasher.testliqpay.service.background;

import android.os.AsyncTask;

import com.carwasher.testliqpay.ilisteners.ICheckPaymentStatusListener;
import com.carwasher.testliqpay.service.ConfigurationService;
import com.carwasher.testliqpay.service.background.params.CheckPaymentStatusParams;

import java.util.HashMap;

import ua.privatbank.paylibliqpay.ErrorCode;
import ua.privatbank.paylibliqpay.LiqPay;
import ua.privatbank.paylibliqpay.LiqPayCallBack;

public class CheckPaymentStatusService
        extends AsyncTask<CheckPaymentStatusParams, Integer, CheckPaymentStatusParams>
        implements LiqPayCallBack {

    private CheckPaymentStatusParams params;


    @Override
    protected CheckPaymentStatusParams doInBackground(CheckPaymentStatusParams... checkPaymentStatusParams) {
        params = checkPaymentStatusParams[0];
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("version", params.getVersion());
        map.put("public_key", params.getPublicKey());
        map.put("order_id", params.getOrderId());

        LiqPay.api(params.getExecutionContext(), "status", map, ConfigurationService.getInstance().getLiqPayPrivateKey(), this);
        return params;
    }

    @Override
    protected void onPostExecute(CheckPaymentStatusParams params) {
        super.onPostExecute(params);
    }

    @Override
    public void onResponseSuccess(String s) {
        params.setOperationResult(s);
        ICheckPaymentStatusListener listener = params.getListener();
        listener.CheckPaymentStatusResult(params);
    }

    @Override
    public void onResponceError(ErrorCode errorCode) {
        params.setOperationError(errorCode);
        ICheckPaymentStatusListener listener = params.getListener();
        listener.CheckPaymentStatusResult(params);
    }
}
