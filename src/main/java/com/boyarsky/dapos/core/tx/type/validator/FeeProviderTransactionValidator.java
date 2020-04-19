package com.boyarsky.dapos.core.tx.type.validator;

import com.boyarsky.dapos.core.tx.Transaction;
import com.boyarsky.dapos.core.tx.type.TxType;
import org.springframework.stereotype.Component;

@Component
public class FeeProviderTransactionValidator implements TransactionTypeValidator {
    @Override
    public void validate(Transaction tx) throws TxNotValidException {

    }

    @Override
    public TxType type() {
        return TxType.SET_FEE_PROVIDER;
    }
}