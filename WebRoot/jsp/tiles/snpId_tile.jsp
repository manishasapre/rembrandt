<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" > -->
<fieldset class="gray">
<legend class="red">SNP Id
<app:help help="Choose one type of SNP identifiers (dbSNP ID or Affymetrix SNP Probeset ID) from the pick list. Then enter the corresponding comma delimited IDs to be searched in the text box. Another option to load a list of IDs is to upload a file using the Browse button, file must be of type *.txt with each entry in a new line. Upper limit for this option is 500 entries in the txt file." />
</legend>
<!-- <b class="message">(Paste comma separated SNP list, or upload file using Browse button)</b>-->
<br /><br />


 <html:select property="snpList" disabled="false">
   <html:optionsCollection property="snpTypes" />
    </html:select>



&nbsp;

<html:radio property="snpId" value="specify" styleClass="radio" onfocus="javascript:onRadio(this,0);" />

<html:text property="snpListSpecify" disabled="false" onfocus="javascript:radioFold(this);" value="" onblur="javascript:cRadio(this, document.forms[0].snpId[0]);"/>
&nbsp;&nbsp;
<html:radio property="snpId" value="upload" styleClass="radio" onfocus="javascript:onRadio(this,1);" />
<html:file property="snpListFile" disabled="true" onblur="javascript:cRadio(this, document.forms[0].snpId[1]);" onfocus="javascript:document.forms[0].snpId[1].checked = true;" />
<!-- <app:help help="Only files of type \"*.txt\" with each entry in a new line are accepted. Upper limit for this option is 500 entries in the txt file." />-->
<br />
&nbsp;&nbsp;Validated SNPs:&nbsp;&nbsp;

<html:radio property="validatedSNP" value="all"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;All
<html:radio property="validatedSNP" value="excluded"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;Excluded
<html:radio property="validatedSNP" value="included" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Included
<html:radio property="validatedSNP" value="only" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Only
<br>
<html:errors property="snpId"/>
</fieldset>
<!-- </html:form> -->
