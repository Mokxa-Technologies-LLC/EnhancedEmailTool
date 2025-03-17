package org.joget.enhancedemailtool.app;

import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joget.apps.app.dao.FormDefinitionDao;
import org.joget.apps.app.lib.EmailTool;
import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.model.FormDefinition;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.form.dao.FormDataDao;
import org.joget.apps.form.model.Form;
import org.joget.apps.form.model.FormRowSet;
import org.joget.apps.form.service.FormPdfUtil;
import org.joget.apps.form.service.FormService;
import org.joget.apps.form.service.FormUtil;
import org.joget.commons.util.LogUtil;
import org.joget.commons.util.StringUtil;
import org.joget.directory.dao.UserDao;
import org.joget.directory.model.Employment;

import org.joget.directory.model.User;
import org.joget.enhancedemailtool.db.dao.impl.EmailAuditDaoImpl;
import org.joget.enhancedemailtool.db.dto.Emailaudit;

import org.joget.plugin.base.DefaultApplicationPlugin;

import org.joget.plugin.base.PluginWebSupport;
import org.joget.workflow.model.WorkflowAssignment;

public class EmailAuditTool extends DefaultApplicationPlugin implements PluginWebSupport {

	private EmailAuditDaoImpl emailAudtiService = null;
	private FormDataDao formDataDao;
	private UserDao userDao;

	public String getName() {
		return "Enhanced Email Tool";
	}

	public String getDescription() {
		return "Enhanced Email Tool";
	}

	public String getVersion() {
		return "1.0";
	}
	public String getLabel() {
		return "Enhanced Email Tool";
	}

	public String getClassName() {
		return getClass().getName();
	}

	public String getPropertyOptions() {
		return AppUtil.readPluginResource(getClass().getName(), "/properties/enhancedEmailTool.json", null, true, null);
	}
	
	// permission check for to, cc, bcc (this logic is missing - confirm with Harminder)
//	public boolean permissionCheck(String isConditional, String email, Form form, String usernameField,
//			String fieldToCheck, String valueToCheck, String sendEmailIfRowNotFound) {
//
//		if (!"true".equalsIgnoreCase(isConditional))
//			return true;
//		if (!"id".equalsIgnoreCase(usernameField))
//			usernameField = "c_" + usernameField;
//		if (fieldToCheck.isEmpty()) {
//			// code to send emails if all conditon failed
//			return true;
//		}
//		
//		// fetch user data based on email (to, cc, bcc?)
//		Collection<User> users = userDao.findUsers("where email=?", new Object[] { email }, null, null, null, null);
//		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
//			User user = (User) iterator.next();
//
//			// check for user employments
//			Set<Employment> employments = user.getEmployments();
//
//			for (Iterator iterator3 = employments.iterator(); iterator3.hasNext();) {
//				Employment employment = (Employment) iterator3.next();
//				
//				// hardcoded logic - do we need?
//				if ("Customer".equalsIgnoreCase(employment.getOrganizationId())) {
//					FormRowSet permission = formDataDao.find("SystemSpecifications", "CustSpecification",
//							"where id =? ", new Object[] { "" + employment.getDepartmentId() }, null, null, null, null);
//
//					if (permission.size() > 0) {
//						if (permission.get(0).containsKey(fieldToCheck))
//							if (!valueToCheck.equalsIgnoreCase(permission.get(0).getProperty(fieldToCheck))) {
//
//								return false;
//							}
//
//					}
//
//					FormRowSet alerts = formDataDao.find("Alerts", "Customeralerts", "where id =? ",
//							new Object[] { "" + employment.getDepartmentId() }, null, null, null, null);
//
//					if (alerts.size() > 0) {
//						if (alerts.get(0).containsKey(fieldToCheck))
//							if (!valueToCheck.equalsIgnoreCase(alerts.get(0).getProperty(fieldToCheck))) {
//
//								return false;
//							}
//
//					}
//				
//				// hardcoded logic - do we need?
//				} else if ("Monitor".equalsIgnoreCase(employment.getOrganizationId())) {
//
//					FormRowSet permission = formDataDao.find("ReportingSpecificationsMonitor", "MonitorSpecification",
//							"where id =? ", new Object[] { "" + employment.getDepartmentId() }, null, null, null, null);
//
//					if (permission.size() > 0) {
//						if (permission.get(0).containsKey(fieldToCheck))
//							if (!valueToCheck.equalsIgnoreCase(permission.get(0).getProperty(fieldToCheck))) {
//
//								return false;
//							}
//
//					}
//					
//					// hardcoded logic - do we need?
//					FormRowSet alerts = formDataDao.find("AlertsMonitor", "MonitorAlerts", "where id =? ",
//							new Object[] { "" + employment.getDepartmentId() }, null, null, null, null);
//
//					if (alerts.size() > 0) {
//						if (alerts.get(0).containsKey(fieldToCheck))
//							if (!valueToCheck.equalsIgnoreCase(alerts.get(0).getProperty(fieldToCheck))) {
//
//								return false;
//							}
//
//					}
//				}
//				
//				// hardcoded logic - do we need?
//				else if ("Haulier".equalsIgnoreCase(employment.getOrganizationId())) {
//
//					FormRowSet permission = formDataDao.find("SystemSpecificationsHaulier", "HaulierSpecification",
//							"where id =? ", new Object[] { "" + employment.getDepartmentId() }, null, null, null, null);
//
//					if (permission.size() > 0) {
//						if (permission.get(0).containsKey(fieldToCheck))
//							if (!valueToCheck.equalsIgnoreCase(permission.get(0).getProperty(fieldToCheck))) {
//
//								return false;
//							}
//
//					}
//					
//					// hardcoded logic - do we need?
//					FormRowSet alerts = formDataDao.find("AlertsHaulier", "HaulierAlerts", "where id =? ",
//							new Object[] { "" + employment.getDepartmentId() }, null, null, null, null);
//
//					if (alerts.size() > 0) {
//						if (alerts.get(0).containsKey(fieldToCheck)) {
//							if (!valueToCheck.equalsIgnoreCase(alerts.get(0).getProperty(fieldToCheck))) {
//
//								return false;
//							}
//						}
//
//					}
//				}
//
//			}
//
//			try {
//
//				FormRowSet permission = formDataDao.find(form, "where " + usernameField + "=? ",
//						new Object[] { user.getUsername() }, null, null, null, null);
//
//				if (permission.size() > 0) {
//					if (valueToCheck.equalsIgnoreCase(permission.get(0).getProperty(fieldToCheck))) {
//						return true;
//					} else {
//						return false;
//					}
//
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if ("true".equalsIgnoreCase(sendEmailIfRowNotFound)) {
//				return true;
//			}
//
//		}
//
//		return false;
//	}

	public Object execute(final Map properties) {
		formDataDao = (FormDataDao) AppUtil.getApplicationContext().getBean("formDataDao");
		userDao = (UserDao) AppUtil.getApplicationContext().getBean("userDao");
		WorkflowAssignment wfAssignment = (WorkflowAssignment) properties.get("workflowAssignment");
		AppDefinition appDef = (AppDefinition) properties.get("appDef");

		try {

			emailAudtiService = AppContext.getInstance().getAppContext().getBean("emailAuditDao",
					EmailAuditDaoImpl.class);
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		final String cc = (String) properties.get("cc");
		final String bcc = (String) properties.get("bcc");
		String toParticipantId = (String) properties.get("toParticipantId");
		String toSpecific = (String) properties.get("toSpecific");

		final String emailSubject = (String) properties.get("subject");
		final String emailMessage = (String) properties.get("message");

		String isConditional = (String) properties.get("isConditional");
		String isSingle = (String) properties.get("isSingle");
		String usernameField = (String) properties.get("idFeild");
		String permissonFormDefId = (String) properties.get("permissionformDefId");

		String fieldToCheck = "";
		String valueToCheck = "";
		String sendEmailIfRowNotFound = "";

		String workflowValue = "";

		if (!"multiple".equalsIgnoreCase(isSingle)) {

			fieldToCheck = (String) properties.get("mailtype");
			valueToCheck = (String) properties.get("valueToCheck");
			sendEmailIfRowNotFound = (String) properties.get("sendEmailIfRowNotFound");
		} else {

			workflowValue = (String) properties.get("variableToCheck");
			workflowValue = AppUtil.processHashVariable(workflowValue, wfAssignment, null, null);

			Object mailConditions = getProperty("mailConditions");

			for (Object opt : (Object[]) mailConditions) {
				Map optMap = (Map) opt;

				if (workflowValue.equals(optMap.get("variableValue"))) {
					fieldToCheck = (String) optMap.get("mailtype");
					valueToCheck = (String) optMap.get("valueToCheck");
					sendEmailIfRowNotFound = (String) optMap.get("sendEmailIfRowNotFound");
					break;
				}
			}

		}

		Form permissionForm = null;

		permissionForm = getForm(permissonFormDefId);

		Object files = getProperty("files");
		if (files != null) {

			if (files instanceof Object[]) {

				for (Object opt : (Object[]) files) {

					Map optMap = (Map) opt;
					String path = AppUtil.processHashVariable((String) optMap.get("path"), wfAssignment, null, null);

					optMap.put("path", path);


				}

			}
		
		}

		if (fieldToCheck != null)
		{	

			if (fieldToCheck.isEmpty()) {

				try {

					if (emailAudtiService != null) {

						Emailaudit audit = new Emailaudit("", cc, bcc, emailSubject, emailMessage,
								"All conditons failed for variable value " + workflowValue);

						emailAudtiService.save(audit);

					}
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}
		String isHtml = (String) properties.get("isHtml");

		try {

			Map<String, String> replaceMap = null;
			if ("true".equalsIgnoreCase(isHtml)) {
				replaceMap = new HashMap<>();
				replaceMap.put("\\n", "<br/>");
			}

			// create the email message

			List<String> toAddress = new ArrayList<>();
			List<String> ccAddress = new ArrayList<>();
			List<String> bccAddress = new ArrayList<>();
			if (cc != null && cc.length() != 0) {
				Collection<String> ccs = AppUtil.getEmailList(null, cc, wfAssignment, appDef);
				for (String address : ccs) {

//					if (permissionCheck(isConditional, address, permissionForm, usernameField, fieldToCheck,
//							valueToCheck, sendEmailIfRowNotFound)) {
						ccAddress.add(StringUtil.encodeEmail(address));
//					} else {

//					}

				}
			}
			
			// no permission check for bcc?
			if (bcc != null && bcc.length() != 0) {

				Collection<String> ccs = AppUtil.getEmailList(null, bcc, wfAssignment, appDef);
				for (String address : ccs) {

					bccAddress.add(StringUtil.encodeEmail(address));

				}
			}

			String emailToOutput = "";

			if ((toParticipantId != null && toParticipantId.trim().length() != 0)
					|| (toSpecific != null && toSpecific.trim().length() != 0)) {

				Collection<String> tss = AppUtil.getEmailList(toParticipantId, toSpecific, wfAssignment, appDef);
				for (String address : tss) {

					// code to check if email needed to be sent.
//					if (permissionCheck(isConditional, address, permissionForm, usernameField, fieldToCheck,
//							valueToCheck, sendEmailIfRowNotFound)) {
						toAddress.add(StringUtil.encodeEmail(address));
						emailToOutput += address + ", ";
//					} else {
//						// LogUtil.info(this.getClassName(), "Not Sending "+emailSubject +" To
//						// "+address);
//					}
				}
			} else {

				try {

					if (emailAudtiService != null) {
						Emailaudit audit = new Emailaudit("", cc, bcc, emailSubject, emailMessage,
								"no email specified");

						emailAudtiService.save(audit);
						LogUtil.info(this.getClassName(), "No Email Specified");
					}
				} catch (Exception e) {

					e.printStackTrace();
				}

				return null;
				// throw new PluginException("no email specified");

			}

			final String to = emailToOutput;

			properties.put("toParticipantId", "");
			properties.put("toSpecific", String.join(",", to));

			if (bccAddress.size() > 0) {
				properties.put("bcc", "");
				ccAddress.addAll(bccAddress);
			}

			properties.put("cc", String.join(",", ccAddress));

			if (toAddress.size() == 0) {
				if (ccAddress.size() > 0) {
					properties.put("toSpecific", String.join(",", ccAddress));
					properties.put("cc", "");
				} else if ("".equals(properties.get("bcc")) == false) {

					properties.put("toSpecific", properties.get("bcc"));
					properties.put("bcc", "");
				}
			}
			try {

				// Check if email fields are empty return
				String emails = (String) properties.get("toSpecific");
				if (emails.length() == 0) {

					Emailaudit audit = new Emailaudit(to, cc, bcc, emailSubject, emailMessage, "no email specified");
					emailAudtiService.save(audit);

					LogUtil.info(this.getClassName(), "No Email Specified");
					return null;
				}

				EmailTool tool = new EmailTool();

				tool.execute(properties);
				try {

					if (emailAudtiService != null) {

						Emailaudit audit = new Emailaudit(to, cc, bcc, emailSubject, emailMessage);

						emailAudtiService.save(audit);
					}
				} catch (Exception e) {

					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

			} catch (Exception ex) {

				LogUtil.error(EmailTool.class.getName(), ex, "");
				try {

					if (emailAudtiService != null) {

						Emailaudit audit = new Emailaudit(to, cc, bcc, emailSubject, emailMessage,
								"ERROR" + ex.getMessage());

						emailAudtiService.save(audit);
					}
				} catch (Exception e) {

				}
			}

		} catch (Exception e) {

		}

		return null;
	}

	protected Form getForm(String formDefId) {

		Form form = null;
		if (formDefId != null) {
			if (!formDefId.isEmpty()) {
				AppDefinition appDef = AppUtil.getCurrentAppDefinition();
				if ((appDef != null)) {
					FormDefinitionDao formDefinitionDao = (FormDefinitionDao) AppUtil.getApplicationContext()
							.getBean("formDefinitionDao");
					FormService formService = (FormService) AppUtil.getApplicationContext().getBean("formService");
					FormDefinition formDef = formDefinitionDao.loadById(formDefId, appDef);
					if (formDef != null) {
						String json = formDef.getJson();

						form = ((Form) formService.createElementFromJson(json));

						Boolean readonly = Boolean.valueOf("true".equalsIgnoreCase(getPropertyString("readonly")));
						Boolean readonlyLabel = Boolean
								.valueOf("true".equalsIgnoreCase(getPropertyString("readonlyLabel")));
						if ((readonly.booleanValue()) || (readonlyLabel.booleanValue())) {
							FormUtil.setReadOnlyProperty(form, readonly, readonlyLabel);
						}
					}
				}
			}
		}

		return form;
	}

	@Override
	public void webService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AppDefinition appDef = AppUtil.getCurrentAppDefinition();
		// LogUtil.info(getClassName(),
		// "formDefId--"+request.getParameter("formDefId")+"--id"+request.getParameter("id"));

		byte[] data = FormPdfUtil.createPdf(request.getParameter("formDefId"), request.getParameter("id"), appDef, null,
				null, null, null, null, null, null, null);
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + request.getParameter("id") + ".pdf");
		response.setContentLength(data.length);

		OutputStream writer = response.getOutputStream();
		writer.write(data);
		writer.close();

	}

}
