<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Gene Ontology (GO) Classifications</legend>
<%
	String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br>
<b class="message">(Type GO format as 'GO:XXXXXXX' where number is XXXXXXX)</b><br>
<html:textarea property="goClassification" rows="5" cols="40" value=""></html:textarea>
<html:errors property="goClassification"/>
<input type="button" class="sbutton" value="GO Browser..." onclick="javascript:spawn('http://www.godatabase.org/cgi-bin/amigo/go.cgi', 780, 500);">
<br>
<!--<html:checkbox property="goMolecularFunction" styleClass="radio" /> Molecular Function
			<html:checkbox property="goBiologicalProcess" styleClass="radio" /> Biological Process
			<html:checkbox property="goCellularComp" styleClass="radio" /> Cellular Component</br>-->
			<html:errors property="goCellularComp"/>&nbsp;&nbsp;
			<html:errors property="goBiologicalProcess"/>&nbsp;&nbsp;
			<html:errors property="goMolecularFunction"/>

</fieldset>

<!-- </html:form> -->