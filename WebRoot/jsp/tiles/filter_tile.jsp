<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<% 
/*
 * Gene Tile - used in: GeneExpression form, CGH form
 */

String act = request.getParameter("act");
%>

<script language="javascript">

	function updateG(){
    	Defaults.getSessionGeneBag(createGList);
	}
	function createGList(data){
    	
    	DWRUtil.removeOptions("geneList", data);
    	DWRUtil.addOptions("geneList", data);
	}
	function updateR(){
    	Defaults.getSessionReporterBag(createRList);
	}
	function createRList(data){
    	DWRUtil.removeOptions("geneList", data);
    	DWRUtil.addOptions("reporterList", data);
	}
	
</script>
<script type='text/javascript' src='/rembrandt/dwr/interface/Defaults.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>

	
<fieldset class="gray">
<legend class="red">
	<logic:present name="hierarchicalClusteringForm">
	Step 1: 
	</logic:present>
	<logic:present name="principalComponentForm">
	Step 2: 
	</logic:present>
	Filter Genes/Reporters
<app:help help=" ...Optionally, you can upload a text file containing Gene identifiers by clicking the browse button. There must only be one entry per line and a return must be added at the end of the file. Choose �All Genes query� if you do not wish to specify a list of genes, but would like to see the data for all the genes analyzed. You must apply the �All Genes query� to a pre-existing result set. (see help on Refine query page for more details)" />
</legend>

<input type="radio" class="radio" name="filterType" value="default" />Default<br /><br />

<input type="radio" class="radio" name="filterType" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advFilter','pm');return false;">&nbsp;+&nbsp;</a>

	<div id="advFilter" class="divHide">
		<br>
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<html:text property="variancePercentile" size="4" />&nbsp;&nbsp;%
		<br><br>	
		  	<html:checkbox property="diffExpGenes" styleClass="radio" value="diffExpGenes" />
		  Use differentially expressed genes &nbsp;&nbsp;
		      <html:select property="geneSetName" styleId="geneList" disabled="false" onclick="javascript:updateG()">
				
			  </html:select>
			  or <a href="#" onclick="window.open('uploadSamples.html', 'upload', 'screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=380,height=230,scrollbars=yes,resizable=no');return false;">Upload</a>
						
		<br><br>	  
			<html:checkbox property="diffExpReporters" styleClass="radio" value="diffExpReporters" />
		  Use differentially expressed reporters &nbsp;&nbsp;
		      <html:select property="reporterSetName" styleId="reporterList" disabled="false" onclick="javascript:updateR()">
				
			  </html:select>
			  or <a href="#" onclick="window.open('uploadSamples.html', 'upload', 'screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=380,height=230,scrollbars=yes,resizable=no');return false;">Upload</a>
		<br><br>	
			
			<center><input type="button" value="SET THESE FILTERS AS DEFAULT" /></center>
						
		</div>	  
	  
	