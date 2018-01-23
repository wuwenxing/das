package com.gw.das.service.market;

import com.gw.das.common.context.PersistContext;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;

public interface PersistService {
    public PersistContext persist(String batchNo, String fileName, AccountAnalyzeEntity entity);
    public void persistCallback(String persistId, String flag);
}
