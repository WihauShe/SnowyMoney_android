<%--
  Created by IntelliJ IDEA.
  User: Wihau
  Date: 2020/4/9
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //得到项目的根目录
    String path = request.getContextPath();
    pageContext.setAttribute("path", path, PageContext.PAGE_SCOPE);
%>
<html>
<head>
    <title>举报列表</title>
    <link rel="icon"type="image/x-icon" href="${path}/img/snowymoney.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${path}/css/reports.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/js/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${path}/js/jquery-easyui-1.7.0/themes/icon.css">
    <script type="text/javascript" src="${path}/js/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="${path}/js/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${path}/js/jzoom.js"></script>
    <script type="text/javascript" src="${path}/js/reposts.js"></script>
</head>
<body>
    <div class="logo_module">
        <img src="img/whitesnowy.png" /><span>SnowyMoney</span>
    </div>
    <div class="border">
        <table id="reports"></table>
    </div>
    <div id="p" class="easyui-window" title="Evidence" style="width:700px;height:600px" >
        <iframe id="showPicture" href="#" style="width:100%;height:100%">
        </iframe>
    </div>
</body>
</html>
