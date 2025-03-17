package org.joget.enhancedemailtool.db.dto;

import java.io.Serializable;
import java.util.Date;



public class Emailaudit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String id;

	private Date dateCreated;

	private Date dateModified;
	private String createdBy;
	private String CreatedByName;
	private String modifiedBy;
	private String ModifiedByName;



	private String to;


	private String bcc;

	private String cc;


	private String subject;

	
	private String body;


	private String error;

	public Emailaudit() {
		super();

	}

	public Emailaudit(String to, String bcc, String cc, String subject, String body) {
		super();
		this.to = to;
		this.bcc = bcc;
		this.cc = cc;
		this.subject = subject;
		this.body = body;
		this.dateCreated = new Date();
	}

	public Emailaudit(String to, String bcc, String cc, String subject, String body, String error) {
		super();
		this.to = to;
		this.bcc = bcc;
		this.cc = cc;
		this.subject = subject;
		this.body = body;
		this.error = error;
		this.dateCreated = new Date();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateModified
	 */
	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}


	public String getCreatedBy() {
        return createdBy;
    }
	public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
	public String getCreatedByName() {
        return CreatedByName;
    }
	public void setCreatedByName(String CreatedByName) {
		this.CreatedByName = CreatedByName;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
	public String getModifiedByName() {
		return ModifiedByName;
	}
	public void setModifiedByName(String ModifiedByName) {
        this.ModifiedByName = ModifiedByName;
    }
}
