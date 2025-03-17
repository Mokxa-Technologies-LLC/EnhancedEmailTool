package org.joget.enhancedemailtool.db.dao;

import org.joget.enhancedemailtool.db.dto.Emailaudit;
public interface EmailAuditDao {
    public boolean save(Emailaudit log) throws Exception;
}
