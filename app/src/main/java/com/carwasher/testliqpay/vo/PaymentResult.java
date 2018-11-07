package com.carwasher.testliqpay.vo;

import com.carwasher.testliqpay.enums.PaymentStatuses;
import com.carwasher.testliqpay.service.background.params.CheckPaymentStatusParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ua.privatbank.paylibliqpay.ErrorCode;

public class PaymentResult {
    //Sample value: {"action":"pay","payment_id":761571739,"status":"wait_accept","version":3,"type":"buy","paytype":"card",
    //      "public_key":"i32272911111","acq_id":414963,"order_id":"123456","liqpay_order_id":"LCD2ffffB1532117893818656",
    //      "description":"account top-up","sender_phone":"380996618172","sender_first_name":"FName",
    //      "sender_last_name":"LName","sender_card_mask2":"516114*45","sender_card_bank":"pb","sender_card_type":"mc",
    //      "sender_card_country":804,"ip":"37.55.140.255","amount":0.01,"currency":"UAH","sender_commission":0,
    //      "receiver_commission":0,"agent_commission":0,"amount_debit":0.01,"amount_credit":0.01,"commission_debit":0,
    //      "commission_credit":0,"currency_debit":"UAH","currency_credit":"UAH","sender_bonus":0,"amount_bonus":0,
    //      "authcode_debit":"400662","rrn_debit":"000932773782","mpi_eci":"7","is_3ds":false,"create_date":1532115107598,
    //      "transaction_id":761571739,"signature":"H7Q1NtndKr0111RkkD5gT8fx4Fs="}
    //Sample value2: {"action":"pay","status":"failure","err_code":"cancel","err_description":"cancel","version":3,
    //      "paytype":"card","public_key":"i3227295019","order_id":"123456","liqpay_order_id":"123456",
    //      "description":"account top-up","amount":"0.01","currency":"uah","is_3ds":false,"create_date":1532467918708,
    //      "code":"cancel","signature":"57WQffEZGbfMvEvv0sonA0VFgWs="}
    private String paymentResultSource;
    private PaymentStatuses paymentStatus;
    private String paymentStatusDescription;
    private String acqId;
    private String orderId;
    private String paymentId;
    private String transactionId;
    private String liqpayOrderId;
    private String senderPhone;
    private String senderCardMask;
    private String amount;
    private String currency;
    private String createdDateString;
    private Date createdDate;
    private ErrorCode errorCode;

    public PaymentResult(ErrorCode error) {
        errorCode = error;
    }

    public PaymentResult(String result) throws JSONException {
        setFromPaymentResultSource(result);
    }

    private void setFromPaymentResultSource(String result) throws JSONException {
        if (result == null || result.isEmpty())
        {
            errorCode = ErrorCode.checkout_canseled;
            return;
        }
        paymentResultSource = result;
        JSONObject obj = new JSONObject(paymentResultSource);

        String val;
        try{
            paymentStatusDescription = obj.optString("err_description");
        }catch(Exception e){}
        val = obj.optString("status");
        paymentStatus = PaymentStatuses.valueOf(val);
        paymentId = obj.optString("payment_id");
        acqId = obj.optString("acq_id");
        orderId = obj.optString("order_id");
        transactionId = obj.optString("transaction_id");
        liqpayOrderId = obj.optString("liqpay_order_id");
        senderPhone = obj.optString("sender_phone");
        senderCardMask = obj.optString("sender_card_mask2");
        amount = obj.optString("amount");
        currency = obj.optString("currency");
        createdDateString = obj.optString("create_date");
        createdDate = new java.util.Date(Long.parseLong(createdDateString) *1000);
    }

    public PaymentStatuses getPaymentStatus() {
        return paymentStatus;
    }

    public String getAcqId() {
        return acqId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getLiqpayOrderId() {
        return liqpayOrderId;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public String getSenderCardMask() {
        return senderCardMask;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCreatedDateString() {
        return createdDateString;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getDescription(){
        if (paymentResultSource == null || paymentResultSource.isEmpty())
            return "";
        return "Payment status: " + paymentStatus + ", amount " + amount + currency +
                ", transaction " + transactionId + ", liqpayOrderId " + liqpayOrderId;
    }

    public String getErrorCode(){
        if (errorCode == null)
            return null;
        return errorCode.toString();
    }

    public String getFullError(){
        if (errorCode == null && paymentStatusDescription == null)
            return null;
        return ((errorCode == null) ? "" : errorCode.toString())  + " : " + ((paymentStatusDescription == null) ? "" : paymentStatusDescription);
    }
    //Sample result:
    //{"result":"ok","action":"pay","payment_id":765048868,"status":"wait_accept","version":3,"type":"buy","paytype":"card","public_key":"i3227295019","acq_id":414963,"order_id":"4149","liqpay_order_id":"5M028QXC1532469120028317","description":"account top-up","sender_phone":"380996618172","sender_first_name":"Вадим","sender_last_name":"Романенко","sender_card_mask2":"516874*45","sender_card_bank":"pb","sender_card_type":"mc","sender_card_country":804,"ip":"37.55.140.255","amount":0.01,"currency":"UAH","sender_commission":0.0,"receiver_commission":0.0,"agent_commission":0.0,"amount_debit":0.01,"amount_credit":0.01,"commission_debit":0.0,"commission_credit":0.0,"currency_debit":"UAH","currency_credit":"UAH","sender_bonus":0.0,"amount_bonus":0.0,"authcode_debit":"750667","rrn_debit":"000936023018","mpi_eci":"7","is_3ds":false,"create_date":1532469109090,"transaction_id":765048868}
    public void updateResult(CheckPaymentStatusParams params) throws JSONException {
        setFromPaymentResultSource(params.getOperationResult());
    }

}
