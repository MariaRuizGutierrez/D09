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


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="auditRecords" requestURI="auditRecord/auditor/list.do" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('AUDITOR')">
		<spring:message code="auditRecord.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
		
		<jstl:if test="${row.draftMode==true}">
		<spring:url value="auditRecord/auditor/edit.do" var="editURL">
		<spring:param name="auditRecordId" value="${row.id}"/>
		</spring:url>
		<a href="${editURL}"><spring:message code="auditRecord.edit"/></a>
		</jstl:if>	
		</display:column>	
		
	
		<spring:message code="auditRecord.display" var="Display" />
		<display:column title="${Display}" sortable="true">
		<spring:url value="auditRecord/auditor/display.do" var="displayURL">
		<spring:param name="auditRecordId" value="${row.id}"/>
		</spring:url>
		<a href="${displayURL}"><spring:message code="auditRecord.display"/></a>
		</display:column>		
	</security:authorize>
	
	<!-- Attributes -->

	<spring:message code="auditRecord.title" var="titleHeader" />:
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	
	<spring:message code="auditRecord.draftMode" var="draftModeHeader" />:
	<display:column property="draftMode" title="${draftModeHeader}" sortable="true" />
	

	
	<%-- <spring:message code="auditRecord.format.date" var="pattern"></spring:message>
	<spring:message code="auditRecord.realisedMoment" var="realisedMomentHeader" />
	<display:column property="realisedMoment" title="${realisedMomentHeader}" sortable="true" format="${pattern}"/>
	 --%>
	

</display:table>

