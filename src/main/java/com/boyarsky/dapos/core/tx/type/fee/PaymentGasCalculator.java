package com.boyarsky.dapos.core.tx.type.fee;

import com.boyarsky.dapos.core.tx.Transaction;
import com.boyarsky.dapos.core.tx.type.TxType;
import org.springframework.stereotype.Component;

@Component
public class PaymentGasCalculator implements GasCalculator {

    @Override
    public TxType type() {
        return TxType.PAYMENT;
    }

    @Override
    public int gasRequired(Transaction tx) {
        return 0;
    }
}
