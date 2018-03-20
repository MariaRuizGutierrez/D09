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

<form:form action="manager/edit.do" modelAttribute="managerForm">

	<form:hidden path="manager.id" />
	<form:hidden path="manager.servicesOffered"/>
	
	
	<jstl:choose>
			<jstl:when test="${managerForm.manager.id != 0}">
				<acme:textbox path="manager.userAccount.username"
					code="manager.username" readonly="true" /><br/>
			</jstl:when>
			<jstl:otherwise>
				<acme:textbox path="manager.userAccount.username"
					code="manager.username" /><br/>
			</jstl:otherwise>
		</jstl:choose>
		<jstl:choose>
			<jstl:when test="${managerForm.manager.id==0}">
				<acme:password code="manager.password"
					path="manager.userAccount.password" /><br/>
				<acme:password code="manager.password" path="passwordCheck" /><br/>
			</jstl:when>
			<jstl:otherwise></jstl:otherwise>
		</jstl:choose>	
	
	<acme:textbox code="manager.name" path="manager.name"/>
	<br />
	<acme:textbox code="manager.surname" path="manager.surname"/>
	<br />
	<acme:textbox code="manager.vat" path="manager.vat"/>
	<br />
	<acme:textbox code="manager.postalAddress" path="manager.postalAddress"/>
	<br />
	<acme:textbox code="manager.phoneNumber" path="manager.phoneNumber" placeHolder="+CC(AC)PN,+CC PN,PN" />
	<br />
	<acme:textbox code="manager.emailAddress" path="manager.emailAddress"/>
	<br />
	
	<acme:submit name="save" code="manager.save"/>
	
	<acme:cancel url="welcome/index.do" code="manager.cancel"/>
	<br />
	<br/>
	<jstl:if test="${managerForm.manager.id == 0}">
   		<form:label path="conditions">
		<spring:message code="actor.legal.accept"/> - <a href="welcome/legal.do"><spring:message code="actor.legal.moreinfo"/></a>
		</form:label>
		<form:checkbox id="conditions" path="conditions"/>
		<form:errors cssClass="error" path="conditions"/>
   </jstl:if>
 <br/>
	</form:form>
	
	
		
	

