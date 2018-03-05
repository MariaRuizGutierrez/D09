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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="announcement/user/edit.do" modelAttribute="announcement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rendezvouse" />
	<form:hidden path="madeMoment" />


	<acme:textbox code="announcement.title1" path="title"/>

	<br />
	
	<acme:textarea code="announcement.description1" path="description"/>

	<br />
	<br />

	
	<!-- botones -->

	<input type="submit" name="save"
		value="<spring:message code="announcement.save"/>" />&nbsp;
		
		<input type="button" name="cancel"
		value="<spring:message code="rendezvous.cancel"/>"
		onclick="javascript: window.location.replace('rendezvous/user/list.do')" />
	<br />

</form:form>