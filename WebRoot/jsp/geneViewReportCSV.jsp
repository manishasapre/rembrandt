<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="
gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper,
gov.nih.nci.nautilus.ui.bean.SessionQueryBag,
gov.nih.nci.nautilus.constants.NautilusConstants,
org.dom4j.Document"
%>

<%
	Document reportXML = (Document)request.getAttribute(NautilusConstants.REPORT_BEAN);
	ReportGeneratorHelper.renderReport(reportXML,"report.xsl",out);
%>