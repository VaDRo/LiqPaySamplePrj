package com.carwasher.testliqpay.ilisteners;

import com.carwasher.testliqpay.service.background.params.CheckPaymentStatusParams;

public interface ICheckPaymentStatusListener {
    void CheckPaymentStatusResult(CheckPaymentStatusParams params);
}
