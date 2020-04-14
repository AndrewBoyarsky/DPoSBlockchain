package com.boyarsky.dapos.core.tx.type.attachment;

import java.nio.ByteBuffer;

public class NoFeeAttachment extends AbstractAttachment {
    private long payer;

    public NoFeeAttachment(ByteBuffer buffer) {
        super(buffer);
        this.payer = buffer.getLong();
    }

    public NoFeeAttachment(byte version, long payer) {
        super(version);
        this.payer = payer;
    }

    @Override
    public int size() {
        return 8;
    }

    @Override
    public void putBytes(ByteBuffer buffer) {
        buffer.putLong(payer);
    }
}