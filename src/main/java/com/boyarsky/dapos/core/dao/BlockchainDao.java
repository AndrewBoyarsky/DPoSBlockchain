package com.boyarsky.dapos.core.dao;

import com.boyarsky.dapos.core.dao.model.LastSuccessBlockData;
import com.boyarsky.dapos.utils.Convert;
import jetbrains.exodus.ByteBufferByteIterable;
import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.StoreTransaction;
import jetbrains.exodus.env.Environment;
import jetbrains.exodus.env.Store;
import jetbrains.exodus.env.StoreConfig;
import jetbrains.exodus.env.Transaction;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class BlockchainDao {
    public static final String storeName = "blockchain";
    private static final String lastBlockDataKey = "lastBlock";
    private PersistentEntityStore store;

    @Autowired
    public BlockchainDao(PersistentEntityStore store) {
        this.store = store;
    }

    public LastSuccessBlockData getLastBlock() {
        return store.computeInReadonlyTransaction(this::getLastBlock);
    }

    public LastSuccessBlockData getLastBlock(StoreTransaction txn) {
        EntityIterable all = txn.getAll(lastBlockDataKey);
        long size = all.size();
        if (size == 0) {
            return null;
        } else if (size > 1) {
            throw new RuntimeException("More than 1 last block detected: " + size);
        }
        Entity first = all.getFirst();
        String hash = (String) first.getProperty("hash");
        long height = (Long) first.getProperty("height");
        return new LastSuccessBlockData(Convert.parseHexString(hash), height);
    }

    public void insert(LastSuccessBlockData blockData, StoreTransaction txn) {
        Entity prev = txn.getAll(lastBlockDataKey).getFirst();
        if (prev == null) {
            prev = txn.newEntity(lastBlockDataKey);
        }
        prev.setProperty("hash", Convert.toHexString(blockData.getAppHash()));
        prev.setProperty("height", blockData.getHeight());
    }
}
