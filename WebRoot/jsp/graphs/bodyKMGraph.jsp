<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ page import="java.util.*" %>

<div><html:errors /> <%
   String km = "kmplotGE";
   String ta = "Simple_KM_sample_plot";
   if(  ((request.getParameter("plot") != null) && 
         (request.getParameter("plot").equalsIgnoreCase("kapMaiPlotGE")))
           || 
        ((request.getParameter("plot") == null) && 
         (request.getParameter("plotType").equalsIgnoreCase("GE_KM_PLOT"))) ){
         km = "kmplotGE";
         ta = "Simple_KM_gene_expression_plot";
   }
   else if(  ((request.getParameter("plot") != null) && 
         (request.getParameter("plot").equalsIgnoreCase("kapMaiPlotCN")))
           || 
        ((request.getParameter("plot") == null) && 
         (request.getParameter("plotType").equalsIgnoreCase("COPY_NUM_KM_PLOT"))) ){
        km = "kmplotCN";
        ta = "Simple_KM_copy_number_plot";
   }
   else if(  ((request.getParameter("plot") != null) && 
         (request.getParameter("plot").equalsIgnoreCase("kapMaiPlotGE")))
           || 
        ((request.getParameter("plot") == null) && 
         (request.getParameter("plotType").equalsIgnoreCase("SAMPLE_KM_PLOT"))) ){//HACK FOR NOW
         km = "kmplotGE";
         ta = "Simple_KM_sample_plot";
   }
	
	String baselineGroup = request.getParameter("baselineGroup")!=null ? (String)request.getParameter("baselineGroup") : "";
%><script type="text/javascript">Help.insertHelp("<%=ta%>", " align='right'", "padding:2px;");</script><br clear="all"/>
   
</div>

<html:form action="/kmGraph.do?method=redrawKMPlot">
	<input type="hidden" name="baselineGroup" value="<%=baselineGroup%>"/>
	<html:hidden property="geneOrCytoband" />
	<html:hidden property="plotType" />
	
	<logic:notEqual name="kmDataSetForm" property="plotType" value="SAMPLE_KM_PLOT">
	<div>
	<% if(baselineGroup.length()>0)	{	%>
		<b>Constrained to group: <%=baselineGroup%></b><br/><br/>
	<% } %>
		<table style="border:1px solid silver" cellpadding="4" cellspacing="4" summary="This table is used to format page content">
			<tr>
				<th></th><th></th>
			</tr>
			<tr>			
				<td>
					<!-- Upregulated/Amplified  -->
					<span style="font-size:.9em"><label for="upFold"><bean:write name="kmDataSetForm" property="upOrAmplified" /></label></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ge;&nbsp; 
				<!--check to see if it is copy number km plot or GE km plot. if it is, change the amplified fold change values-->
				<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">	
					<html:select styleId="upFold" property="upFold">
						<html:options property="folds" />
					</html:select>
				</logic:equal>
				<logic:equal name="kmDataSetForm" property="plotType" value="COPY_NUM_KM_PLOT">						
					<html:select styleId="upFold" property="upFold">
						<html:options property="copyNumberUpFolds" />
					</html:select>
				</logic:equal>
				<!--end after up fold change values have been determined-->
					
					
					
					
					<span style="font-size:.9em"><bean:write name="kmDataSetForm" property="changeType" /></span>
				</td>
				
				<td>			
					<!--make sure plot is GE and not Copy # before giving option of algorithm to use-->
					<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">						
						<!--Unified or regular algorithm-->
						<span style="font-size:.9em;margin-left:10px"><label for="reporterType">Reporter Type</label></span>			
						<html:select styleId="reporterType" property="reporterSelection" onchange="$('redrawGraphButton').disabled = 'true';$('redrawGraphButton').style.color='gray'; document.forms[0].selectedReporter.selectedIndex=0;  document.forms[0].submit();">
							<html:options property="algorithms" />
						</html:select> &nbsp; 	
					</logic:equal>		
				</td>
				
			</tr>
			
			
			<tr>
				<td>
				<!-- Downregulated/Deleted -->
				<span style="font-size:.9em"><label for="downFold"><bean:write name="kmDataSetForm" property="downOrDeleted" /></label></span>			
				
				<!--check to see if it is copy number km plot or GE km plot. if it is, change the deleted fold change values-->
				<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">						
					&nbsp;&ge;&nbsp; 
					<html:select styleId="downFold" property="downFold">
						<html:options property="folds" />
					</html:select>
				</logic:equal>
				<logic:equal name="kmDataSetForm" property="plotType" value="COPY_NUM_KM_PLOT">						
					&nbsp;&le;&nbsp; 
					<html:select styleId="downFold" property="downFold">
						<html:options property="copyNumberDownFolds" />
					</html:select>
				</logic:equal>
				<!--end after deleted fold change values have been determined-->
				 			
				<span style="font-size:.9em"><bean:write name="kmDataSetForm" property="changeType" /></span> 
				</td>
				
				<!--Reporters-->
				<td>
					<!--make sure plot is GE and not Copy # before giving option of algorithm to use-->
					<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">	
						<span style="font-size:.9em;margin-left:10px"><label for="selectedReporter">Reporters</label></span>
						<html:select styleId="selectedReporter" property="selectedReporter">
							<html:options property="reporters" />
						</html:select>
					</logic:equal>	
				</td>
			</tr>
			
			<tr>
				<td align="center" colspan="2" style="font-size:.9em;margin-left:10px">
					<p align="left">Select which plots should be visible in the redrawn graph:<label for="item">&#160;</label><br/></p>
				    <logic:iterate id="item" property="items" name="kmDataSetForm">
				      	<html:multibox styleId="item" property="selectedItems" name="kmDataSetForm">
				       		<bean:write name="item"/> 
				      	</html:multibox> 
				       	<bean:write name="item"/>
				    </logic:iterate>
				</td>
			</tr>
			
		</table>
	</div>
	<br/>
	<html:submit value="Redraw Graph" styleId="redrawGraphButton" />
	</logic:notEqual>
		
	<div>
	<logic:equal name="kmDataSetForm" property="plotVisible" value="true">
		<hr/>
		<b> 
			<font size="+1"> 
				<bean:write name="kmDataSetForm" property="chartTitle" /> 
			</font>
		</b>
		<br/>
		
		<!-- INSERT CHART HERE --> 
		<p>
			<graphing:KaplanMeierPlot bean="kmDataSetForm" dataset="selectedDataset" />
		</p>
		<p>
		<br/>
		
		<!--  BEGIN LEGEND: LINKS TO CLINICAL -->
		
		View Clinical Reports<br />
	
		
		<bean:define id="selectedItemsId" name="kmDataSetForm" property="selectedItems" />
		<% 
		String[] selectedGroups = (String[])selectedItemsId; 
		List selectedGrpList = Arrays.asList(selectedGroups);
		%>

		<!--check what type of plot it is as to display the correct link text-->
		<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">		
			<%
			if ( selectedGrpList != null && selectedGrpList.contains("Up-Regulated") ) {
			%>
				<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');"/>Upregulating Samples</a>
			<%
			}
			if ( selectedGrpList != null && selectedGrpList.contains("Down-Regulated") ) {
			%>
		 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');"/>Downregulating samples</a>
			<%
			}
			%>
			<!--  
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');"/>Upregulating Samples</a>
		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');"/>Downregulating samples</a>
			-->
		</logic:equal>
		<logic:equal name="kmDataSetForm" property="plotType" value="COPY_NUM_KM_PLOT">		
			<%
			if ( selectedGrpList != null && selectedGrpList.contains("Up-Regulated") ) {
			%>
				<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');"/>Samples with Amplification</a>
			<%
			}
			if ( selectedGrpList != null && selectedGrpList.contains("Down-Regulated") ) {
			%>
		 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');"/>Samples with Deletion</a>
			<%
			}
			%>
			<!--  
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');"/>Samples with Amplification</a>
		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');"/>Samples with Deletion</a>
			-->
		</logic:equal>
		<logic:notEqual name="kmDataSetForm" property="plotType" value="SAMPLE_KM_PLOT">
			<%
			if ( selectedGrpList != null && selectedGrpList.contains("Intermediate") ) {
			%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=inter',700,500,'clinicalPlots');"/>Intermediate Samples</a>
			<%
			}
			%>
			<!--  
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=inter',700,500,'clinicalPlots');"/>Intermediate Samples</a>
			-->		
		</logic:notEqual>
		<logic:equal name="kmDataSetForm" property="plotType" value="SAMPLE_KM_PLOT">
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=list1',700,500,'clinicalPlots');return false;"/><bean:write name="kmDataSetForm" property="storedData.samplePlot1Label" /></a>
			<logic:notEqual name="kmDataSetForm" property="storedData.samplePlot2Label" value="none">
				<a style="margin-left:20px" href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=list2',700,500,'clinicalPlots');return false;"/><bean:write name="kmDataSetForm" property="storedData.samplePlot2Label" /></a>
			</logic:notEqual>
		</logic:equal>
		
		<!-- Statistical Data  STARTS HERE --></p>
		<fieldset class="gray" style="text-align:left">
		<legend class="red">Statistical	Report:</legend>
		<table class="graphTable" border="0" cellpadding="2" cellspacing="0">
			<logic:present name="kmDataSetForm" property="geneOrCytoband">
				<tr>
					<td colspan="2" id="reportBold">
					<bean:write name="kmDataSetForm" property="geneOrCytoband" /></td>
				</tr>
			</logic:present>
			<logic:present name="kmDataSetForm" property="selectedReporter">
				<!--Show the selected Reporter -->
				<tr>
					<td colspan="2" id="reportBold">Reporter: <bean:write
						name="kmDataSetForm" property="selectedReporter" /></td>
				</tr>
			</logic:present>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
				<!--Show the number of samples in each category-->
			<tr>
				<td colspan="2" id="reportBold">Number of samples in group:</td>
			</tr>
    		<logic:greaterThan name="kmDataSetForm" property="storedData.upSampleCount"
				value="0">
				<tr>
					<td><bean:write name="kmDataSetForm" property="upOrAmplified" />:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.upSampleCount" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.downSampleCount"
				value="0">
				<tr>
					<td><bean:write name="kmDataSetForm" property="downOrDeleted" />:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.downSampleCount" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.intSampleCount"
				value="0">
				<tr>
					<td>Intermediate:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.intSampleCount" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.sampleList1Count"
				value="0">
				<tr>
					<td><bean:write name="kmDataSetForm" property="storedData.samplePlot1Label" /></td>
					<td><bean:write name="kmDataSetForm" property="storedData.sampleList1Count" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.sampleList2Count"
				value="0">
				<tr>
					<td><bean:write name="kmDataSetForm" property="storedData.samplePlot2Label" /></td>
					<td><bean:write name="kmDataSetForm" property="storedData.sampleList2Count" /></td>
				</tr>
			</logic:greaterThan>
			<tr>
				<td colspan="2">
				<hr width="100%" size="1" color="black" />
				</td>
			</tr>

			<tr>
				<td id="reportBold" colspan="3">Log-rank p-value(for significance of
				difference of survival between group of samples)</td>
			</tr>
			<logic:greaterThan name="kmDataSetForm" property="storedData.upVsIntPvalue"
				value="-100">
				<tr>
					<td><bean:write name="kmDataSetForm" property="upOrAmplified" />
					vs. Intermediate:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.upVsIntPvalue" /></td>
				</tr>
			</logic:greaterThan>

			<logic:greaterThan name="kmDataSetForm" property="storedData.upVsDownPvalue"
				value="-100">
				<tr>
					<td><bean:write name="kmDataSetForm" property="upOrAmplified" />
					 vs. <bean:write name="kmDataSetForm" property="downOrDeleted" />:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.upVsDownPvalue" /></td>
				</tr>
			</logic:greaterThan>

			<logic:greaterThan name="kmDataSetForm" property="storedData.downVsIntPvalue"
				value="-100">
				<tr>
					<td><bean:write name="kmDataSetForm" property="downOrDeleted" />
					vs. Intermediate:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.downVsIntPvalue" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.numberOfPlots"
				value="2">
				<tr>
					<td colspan="2">
					<hr width="100%" size="1" color="black" />
					</td>
				</tr>
				<logic:greaterThan name="kmDataSetForm" property="storedData.upVsRestPvalue"
					value="-100">
					<tr>
						<td><bean:write name="kmDataSetForm" property="upOrAmplified" />
						vs. all other samples:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.upVsRestPvalue" /></td>
					</tr>
				</logic:greaterThan>
				<logic:greaterThan name="kmDataSetForm" property="storedData.downVsRestPvalue"
					value="-100">
					<tr>
						<td><bean:write name="kmDataSetForm" property="downOrDeleted" />
						vs. all other samples:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.downVsRestPvalue" /></td>
					</tr>
				</logic:greaterThan>
				<logic:greaterThan name="kmDataSetForm" property="storedData.intVsRestPvalue"
					value="-100">
					<tr>
						<td>Intermediate vs. all other samples:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.intVsRestPvalue" /></td>
					</tr>
				</logic:greaterThan>
			</logic:greaterThan>
				<logic:greaterThan name="kmDataSetForm" property="storedData.sampleList1VsSampleList2"
					value="-100">
					<tr>
						<td><bean:write name="kmDataSetForm" property="storedData.samplePlot1Label" /> vs. <bean:write name="kmDataSetForm" property="storedData.samplePlot2Label" />:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.sampleList1VsSampleList2" /></td>
					</tr>
				</logic:greaterThan>

			<logic:lessEqual name="kmDataSetForm" property="storedData.numberOfPlots"
				value="1">
				<td>N/A ( Not Applicable )</td>
			</logic:lessEqual>
		</table>
		</fieldset>
		
	</logic:equal> <!-- TAG CREATION WOULD NEED TO CONTAIN THE ABOVE --> 
	<logic:equal name="kmDataSetForm" property="plotVisible" value="false">
		<p>To display graph, Please select a Reporter for the Gene: 
		<bean:write	name="kmDataSetForm" property="geneOrCytoband" /> and select "Redraw Graph"
		</p>
	</logic:equal>
</div>
</html:form>

