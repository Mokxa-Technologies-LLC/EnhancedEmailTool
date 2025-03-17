package org.joget.enhancedemailtool.db.dao.impl;

import org.joget.apps.app.service.AppUtil;
import org.joget.commons.spring.model.AbstractSpringDao;
import org.joget.commons.util.LogUtil;
import org.joget.enhancedemailtool.db.dao.EmailAuditDao;
import org.joget.enhancedemailtool.db.dto.Emailaudit;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class EmailAuditDaoImpl extends AbstractSpringDao implements EmailAuditDao {

	public boolean save(final Emailaudit log) throws Exception {
try {
            TransactionTemplate transactionTemplate = (TransactionTemplate) AppUtil.getApplicationContext().getBean("transactionTemplate");
            Boolean result = (Boolean)transactionTemplate.execute(new TransactionCallback() {
        
                public Object doInTransaction(TransactionStatus ts) {
                    saveOrUpdate("Emailaudit",log);
                    return true;
                }
            });
            return result;
        } catch (Exception e) {
            LogUtil.error(EmailAuditDaoImpl.class.getName(), e, "Add Log Error!");
            return false;
        }

	};
}
