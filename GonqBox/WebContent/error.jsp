<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gonqbox" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<gonqbox:standard-header title="GonqBox" />
<fmt:message bundle="${sessionScope.uitranslations}" key="${requestScope.error_key}" />
<gonqbox:standard-footer />