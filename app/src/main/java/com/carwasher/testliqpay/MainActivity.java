package com.carwasher.testliqpay;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.carwasher.testliqpay.enums.Currencies;
import com.carwasher.testliqpay.enums.PaymentStatuses;
import com.carwasher.testliqpay.ilisteners.ICheckPaymentStatusListener;
import com.carwasher.testliqpay.ilisteners.ICheckoutResultListener;
import com.carwasher.testliqpay.service.ConfigurationService;
import com.carwasher.testliqpay.service.LiqPayService;
import com.carwasher.testliqpay.service.background.params.CheckPaymentStatusParams;
import com.carwasher.testliqpay.service.background.params.CheckoutPaymentParams;
import com.carwasher.testliqpay.vo.PaymentResult;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ICheckoutResultListener, ICheckPaymentStatusListener {

    private AppCompatTextView mResult;
    private AppCompatTextView mResultError;
    private AppCompatTextView mRetryCntr;
    private AppCompatButton StopRefreshBtn;
    private PaymentResult mPaymentResult;
    private Timer mTimer;
    private CheckPaymentResultTask checkPaymentResultTask;
    private boolean mAutoRefresh = false;
    private MainActivity thisActivity;
    private int mCounter = 0;
    private AppCompatButton mRunmeBtn;
    private LinearLayout mLoadingLayout;
    private Spinner mCurrencySpinner;
    private AppCompatEditText mPaymentAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = this;

        mResult = findViewById(R.id.result);
        mResultError = findViewById(R.id.resultError);
        mRetryCntr = findViewById(R.id.retryCntr);
        mLoadingLayout = findViewById(R.id.loadingPanel);
        mPaymentAmount = findViewById(R.id.paymentAmount);

        mRunmeBtn = findViewById(R.id.simplePay);
        mRunmeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSimplePayButtonClicked();
            }
        });

        StopRefreshBtn = findViewById(R.id.stopRefresh);
        StopRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopRefreshButtonClicked();
            }
        });

        mCurrencySpinner = findViewById(R.id.currencySpinner);
        Currencies[] items = Currencies.values();
        ArrayAdapter<Currencies> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mCurrencySpinner.setAdapter(adapter);
        mCurrencySpinner.setSelection(Arrays.asList(items).indexOf(Currencies.UAH));

        isPermissionGranted();
    }

    private void onSimplePayButtonClicked(){
        mLoadingLayout.setVisibility(View.VISIBLE);
        mRunmeBtn.setEnabled(false);
        String currency = mCurrencySpinner.getSelectedItem().toString();
        String amount2pay = mPaymentAmount.getText().toString();
        try{
            Double.parseDouble(amount2pay);
        }catch(Exception e){
            //user specified non-double value
            mResultError.setText("Exception: specified non-currency value");
            return;
        }

        LiqPayService.getInstance().checkout(getApplicationContext(), amount2pay, currency, this);
    }

    private void onStopRefreshButtonClicked(){
        mLoadingLayout.setVisibility(View.GONE);
        mRunmeBtn.setEnabled(true);
        enableAutoRefreshPaymentStatus(false);
        if (mTimer != null){
            mTimer.cancel();
        }
    }

    @Override
    public void checkoutResult(CheckoutPaymentParams params) {
        try {
            mPaymentResult = new PaymentResult(params.getOperationResult());
            /*if (params.getOperationError() != null)
                mResultError.setText(params.getOperationError().toString());*/
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateResultStats();
                }
            });

            if (mPaymentResult.getErrorCode() == null
                    && mPaymentResult.getPaymentStatus() != PaymentStatuses.success
                    && mPaymentResult.getTransactionId() != null)
            {
                //Start timer that will check payment result
                enableAutoRefreshPaymentStatus(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateResultStats(){
        mResult.setText(mPaymentResult.getDescription());
        mResultError.setText(mPaymentResult.getFullError());
        if (mCounter > 0){
            String msg = "Payment status refreshed: " + String.valueOf(mCounter);
            mRetryCntr.setText(msg);
        }
        else
        {
            mRetryCntr.setText("");
        }
        if (mPaymentResult.getPaymentStatus() == PaymentStatuses.success
                || (mPaymentResult.getErrorCode() != null && !mPaymentResult.getErrorCode().isEmpty()))
            enableAutoRefreshPaymentStatus(false);
    }

    private void refreshPaymentStatus(){
        if (!mAutoRefresh)
            return;
        mTimer = new Timer();
        checkPaymentResultTask = new CheckPaymentResultTask();
        mTimer.schedule(checkPaymentResultTask, ConfigurationService.getInstance().getRetryInterval());
    }

    private void enableAutoRefreshPaymentStatus(boolean enable){
        mAutoRefresh = enable;
        StopRefreshBtn.setEnabled(enable);
        if (!enable)
            mLoadingLayout.setVisibility(View.GONE);

        refreshPaymentStatus();
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 2: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //do ur specific task after read phone state granted
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void CheckPaymentStatusResult(CheckPaymentStatusParams params) {
        try {
            mPaymentResult.updateResult(params);
        } catch (JSONException e) {
            mResultError.setText("Exception: " + e.getMessage());
            enableAutoRefreshPaymentStatus(false);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCounter++;
                updateResultStats();
                if (mAutoRefresh)
                {
                    refreshPaymentStatus();
                }
            }
        });
    }

    class CheckPaymentResultTask extends TimerTask{

        @Override
        public void run() {
            LiqPayService.getInstance().checkPayment(getApplicationContext(), mPaymentResult, thisActivity);

        }
    }
}
