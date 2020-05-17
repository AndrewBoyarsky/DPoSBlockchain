package com.boyarsky.dapos.core.repository.feeprov;

import com.boyarsky.dapos.core.model.account.AccountId;
import com.boyarsky.dapos.core.model.fee.FeeProvider;
import com.boyarsky.dapos.core.model.fee.PartyFeeConfig;
import com.boyarsky.dapos.core.model.fee.State;
import com.boyarsky.dapos.core.repository.XodusRepoContext;
import com.boyarsky.dapos.core.repository.aop.Transactional;
import com.boyarsky.dapos.utils.CollectionUtils;
import com.boyarsky.dapos.utils.Convert;
import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.StoreTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class XodusFeeProviderRepository implements FeeProviderRepository {
    private static final String storeName = "fee-provider";
    private static final String accountFeeStore = "accountFee";
    private final XodusRepoContext context;

    @Autowired
    public XodusFeeProviderRepository(XodusRepoContext context) {
        this.context = context;
    }

    @Override
    @Transactional(requiredExisting = true)
    public void save(FeeProvider feeProvider) {
        StoreTransaction tx = context.getTx();
        Entity toSave = null;
        if (feeProvider.getDbId() != null) {
            toSave = tx.getEntity(feeProvider.getDbId());
        }
        if (toSave == null) {
            toSave = find(feeProvider.getId(), tx);
        }
        if (toSave == null) {
            toSave = tx.newEntity(storeName);
            toSave.setProperty("id", feeProvider.getId());
        }
        toSave.setProperty("balance", feeProvider.getBalance());
        toSave.setProperty("state", feeProvider.getState().getCode());
        toSave.setProperty("account", Convert.toHexString(feeProvider.getAccount().getAddressBytes()));
        toSave.setProperty("fromConfig", Convert.toHexString(Convert.toBytes(feeProvider.getFromFeeConfig())));
        toSave.setProperty("toConfig", Convert.toHexString(Convert.toBytes(feeProvider.getToFeeConfig())));
        toSave.setProperty("height", feeProvider.getHeight());
    }

    private Entity find(long id, StoreTransaction tx) {
        return CollectionUtils.requireAtMostOne(tx.find(storeName, "id", id));
    }

    FeeProvider map(Entity entity) {
        FeeProvider feeProvider = new FeeProvider();
        feeProvider.setId((Long) entity.getProperty("id"));
        feeProvider.setAccount(AccountId.fromBytes(Convert.parseHexString((String) entity.getProperty("account"))));
        feeProvider.setBalance((Long) entity.getProperty("balance"));
        feeProvider.setFromFeeConfig(new PartyFeeConfig(Convert.toBuff(Convert.parseHexString((String) entity.getProperty("fromConfig")))));
        feeProvider.setToFeeConfig(new PartyFeeConfig(Convert.toBuff(Convert.parseHexString((String) entity.getProperty("toConfig")))));
        feeProvider.setHeight((Long) entity.getProperty("height"));
        feeProvider.setState(State.ofCode((Byte) entity.getProperty("state")));
        return feeProvider;
    }

    @Override
    @Transactional(readonly = true)
    public FeeProvider get(long id) {
        Entity entity = find(id, context.getTx());
        if (entity != null) {
            return map(entity);
        }
        return null;
    }


    @Override
    @Transactional(readonly = true)
    public List<FeeProvider> getByAccount(AccountId id) {
        StoreTransaction tx = context.getTx();
        EntityIterable all = tx.find(storeName, "account", Convert.toHexString(id.getAddressBytes()));
        List<FeeProvider> result = new ArrayList<>();
        for (Entity entity : all) {
            result.add(map(entity));
        }
        return result;
    }
}
