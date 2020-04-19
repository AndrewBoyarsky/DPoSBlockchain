package com.boyarsky.dapos.core.tx.type.validator.impl;

import com.boyarsky.dapos.core.tx.Transaction;
import com.boyarsky.dapos.core.tx.type.TxType;
import com.boyarsky.dapos.core.tx.type.validator.TransactionTypeValidator;
import com.boyarsky.dapos.core.tx.type.validator.TxNotValidException;
import org.springframework.stereotype.Component;

@Component
public class ValidatorTransactionValidator implements TransactionTypeValidator {
    @Override
    public void validate(Transaction tx) throws TxNotValidException {

    }

    @Override
    public TxType type() {
        return TxType.VALIDATOR;
    }
}
