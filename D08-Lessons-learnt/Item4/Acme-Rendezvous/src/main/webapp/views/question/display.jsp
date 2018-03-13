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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<display:table name="Question" class="displaytag"
  requestURI="${requestURI}" id="row">
  	
  	
	<!-- Attributes -->

	<spring:message code="question.name" var="Question" />
<display:column title="${Question}" sortable="true">
	<B><spring:message code="question.name" />:
		<jstl:out value="${row.name}"></jstl:out></B>
</display:column>
	
	
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="question.answer.create" var="Answer" />
	<display:column title="${Answer}" sortable="true">
	<jstl:if test="${fn:contains(row.rendezvouse.assistants, row.user)}">
		<spring:url value="answer/user/create.do" var="editURL">
			<spring:param name="questionId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="question.answer.create.reply" /></a>
	</jstl:if>
	</display:column> 
	</security:authorize>

	
	
	
	
	
	<spring:message code="question.answer" var="Answer" />
	<display:column title="${Answer}" sortable="true">
		<spring:url value="answer/list.do" var="editURL">
			<spring:param name="questionId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="question.answer" /></a>
	</display:column> 
	
	

</display:table>