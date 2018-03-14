<%--
 * list.jsp
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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="questions" requestURI="question/user/list.do" id="row">
	
	<!-- Action links -->


	<security:authorize access="hasRole('USER')">
		<spring:message code="question.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
		<spring:url value="question/user/edit.do" var="editURL">
		<spring:param name="questionId" value="${row.id}"/>
		</spring:url>
		<a href="${editURL}"><spring:message code="question.edit"/></a>
		</display:column>		
	</security:authorize>

	<spring:message code="question.answer" var="answerHeader" />
	<display:column title="${answerHeader}" sortable="true">
		<spring:url value="answer/list.do" var="auditRecordURL">
			<spring:param name="questionId" value="${row.id}" />
		</spring:url>
			<a href="${auditRecordURL}"><spring:message code="question.answer" /></a>
	</display:column>
	
	
	<!-- Attributes -->
	<acme:column code="question.name" property="name"/>
	
	
	
	
</display:table>
<security:authorize access="hasRole('USER')">
	<div>
		<a href="question/user/create.do"> 
			<spring:message	code="question.create" />
		</a>
	</div>
</security:authorize>
