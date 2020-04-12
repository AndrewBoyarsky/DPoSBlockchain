package com.boyarsky.dapos.core.tx.type.validator;

import com.boyarsky.dapos.core.tx.Transaction;
import com.boyarsky.dapos.core.tx.type.TxType;

public interface TransactionTypeValidator {

    void validate(Transaction tx) throws TxNotValidException;

    TxType type();


    class TxNotValidException extends RuntimeException {
        private Transaction tx;
        private int code;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public Transaction getTx() {
            return tx;
        }

        public TxNotValidException(String message, Transaction tx, int code) {
            super(message);
            this.tx = tx;
            this.code = code;
        }
        public TxNotValidException(String message, Transaction tx, int code, Throwable e) {
            super(message, e);
            this.tx = tx;
            this.code = code;
        }

    }
}