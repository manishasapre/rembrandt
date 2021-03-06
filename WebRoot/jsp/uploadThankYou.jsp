<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>

<html>
<head><title>Upload Successful</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
</head>

<body>
<div style="padding:10px;width:320;height:180;background:#FFFAE1;border:1px dashed red;">
<h4 class="red">Thank you. Your file has been uploaded</h4>
<br />Your file should now appear in it's respective dropdown menu throughout the application.

<center><a href="#" onclick="window.close(); return false;">Close Window</a></center>
</body>
</html>