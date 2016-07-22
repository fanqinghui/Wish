<%@ page import="com.foundation.common.Contants" %>
<%
	response.setHeader("Content-type", "text/html;charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().print(Contants.UNIFYTHROWSERROR);
%>