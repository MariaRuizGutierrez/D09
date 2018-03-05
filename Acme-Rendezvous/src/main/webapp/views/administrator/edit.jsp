<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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




<form:form action="administrator/edit.do"
	modelAttribute="administratorForm">

	<form:hidden path="administrator.id" />
	<form:hidden path="administrator.version" />
	<%-- <form:hidden path="administrator.userAccount" />
	<form:hidden path="administrator.userAccount.authorities" /> --%>



	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${administratorForm.administrator.id == 0}">
			<acme:textbox code="administrator.username"
				path="administrator.userAccount.username" />
			<acme:password code="administrator.password"
				path="administrator.userAccount.password" />
			<acme:password code="administrator.password" path="passwordCheck" />
			<br />
		</jstl:if>

		<acme:textbox code="administrator.name" path="administrator.name" />
		<acme:textbox code="administrator.surname"
			path="administrator.surname" />
		<acme:textbox code="administrator.emailAddress"
			path="administrator.emailAddress" />
		<acme:textbox code="administrator.postalAddress"
			path="administrator.postalAddress" />
		<acme:textbox code="administrator.phoneNumber"
			path="administrator.phoneNumber" />

		<jstl:if test="${administratorForm.administrator.id == 0}">
			<form:label path="conditions">
				<spring:message code="actor.legal.accept" /> - <a
					href="welcome/legal.do"><spring:message
						code="actor.legal.moreinfo" /></a>
			</form:label>
			<form:checkbox id="conditions" path="conditions" />
			<form:errors cssClass="error" path="conditions" />
		</jstl:if>


		<input type="submit" name="save"
			value="<spring:message code="administrator.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
			value="<spring:message code="administrator.cancel" />"
			onclick="javascript: window.location.replace('welcome/index.do');" />
		<br />

	</security:authorize>

</form:form>