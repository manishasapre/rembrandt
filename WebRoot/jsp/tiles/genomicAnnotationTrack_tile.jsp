<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

<%
String act = request.getParameter("act");
%>

<!-- <html:form action="<%=act%>"> -->
<fieldset class="gray">
<legend class="red">Genomic Annotation Track &nbsp;
<app:help help="Future implementation"/>
</legend>
<br />
<html:text property="genomicTrack" disabled="true" />&nbsp;<input type="button" class="sbutton" value="Genomic Browser..." style="width:150px;" disabled="true">
<br /></fieldset>
<!-- </html:form> -->