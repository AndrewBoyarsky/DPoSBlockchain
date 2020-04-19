package com.boyarsky.dapos.core.service;

import com.boyarsky.dapos.core.model.LastSuccessBlockData;
import com.boyarsky.dapos.core.repository.BlockchainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Blockchain {
    private volatile long currentHeight;
    private final BlockchainRepository blockchainRepository;

    @Autowired
    public Blockchain(BlockchainRepository blockchainRepository) {
        this.blockchainRepository = blockchainRepository;
    }

    public long getCurrentBlockHeight() {
        return currentHeight;
    }

    public void beginBlock(long height) {
        this.currentHeight = height;
    }

    public void addNewBlock(byte[] hash) {
        blockchainRepository.insert(new LastSuccessBlockData(hash, currentHeight));
    }

    public LastSuccessBlockData getLastBlock() {
        return blockchainRepository.getLastBlock();
    }
}
