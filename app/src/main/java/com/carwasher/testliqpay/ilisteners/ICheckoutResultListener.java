package com.carwasher.testliqpay.ilisteners;

import com.carwasher.testliqpay.service.background.params.CheckoutPaymentParams;

public interface ICheckoutResultListener {
    void checkoutResult(CheckoutPaymentParams params);
}
