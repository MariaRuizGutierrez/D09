<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${RequestURI }" modelAttribute="configurationSystem">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<B><acme:textbox code="configurationSystem.name" path="name"/></B>
	<br />
	
	<B><acme:textbox code="configurationSystem.banner" path="banner" /></B>
	<br />
		
	<B><acme:textarea code="configurationSystem.englishWelcomeMessage" path="englishWelcomeMessage" /></B>
	<br />
	
	<B><acme:textarea code="configurationSystem.spanishWelcomeMessage" path="spanishWelcomeMessage" /></B>
	<br />
			
	

<!-- 	//BOTONES -->
	
	<input type="submit" name="save"
		value="<spring:message code="configurationSystem.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="configurationSystem.cancel" />"
		onclick="javascript:  window.location.replace('welcome/index.do');" />
	<br />
</form:form>