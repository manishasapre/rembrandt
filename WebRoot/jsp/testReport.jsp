<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.*" %>

<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script>
			

<%

	PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
	BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();

	
	String key = "frb_test";
	if(request.getParameter("key")!=null)
		key = (String) request.getParameter("key");
	
//	if(ptc.getObjectFromSessionCache(session.getId(), "test") == null)	{
		Object o = btc.getObjectFromSessionCache(session.getId(), "test");
		Finding finding = (Finding) o; 
		//generate the XML and cached it
		ReportGeneratorHelper.generateReportXML(finding);
//	}
	Object ob = ptc.getObjectFromSessionCache(session.getId(), key);
	if(ob != null && ob instanceof FindingReportBean)	{
		out.println("yes");
		try	{
			FindingReportBean frb = (FindingReportBean) ob;
			Document reportXML = (Document) frb.getXmlDoc();
		
			ReportGeneratorHelper.renderReport(request, reportXML,RembrandtConstants.DEFAULT_XSLT_FILENAME,out);
		}
		catch(Exception e)	{
			out.println("no worky");
		}
	}
	else	{
		out.println("this no worky");
	}
%>