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
	name="tags" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('ADMINISTRATOR')">
		
		<spring:message code="category.edit" var="edit"></spring:message>
		<display:column title="${edit}" sortable="true">
		<spring:url value="tag/administrator/edit.do" var="editURL">
		<spring:param name="tagId" value="${row.id}"/>
		</spring:url>
		<a href="${editURL}"><spring:message code="tag.edit"/></a>
		
		</display:column>	
		</security:authorize>		
	
	

	
	<!-- Attributes -->
 
	<spring:message code="tag.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

 	<security:authorize access="hasRole('MANAGER')">
		
		<spring:message code="tag.name.add1" var="nameHeader1" />
		<display:column title="${nameHeader1}" sortable="false" >

		<spring:url value="value/manager_/edit.do" var="editURL">
		<spring:param name="tagId" value="${row.id}"/>
		<spring:param name="tripId" value="${tripId}"/>		
		</spring:url>
		<a href="${editURL}"><spring:message code="tag.add"/></a>
		</display:column>			
	</security:authorize> 
	

	
</display:table>
	
<!-- Action links -->

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<a href="tag/administrator/create.do"> <spring:message
				code="tag.create" />
		</a>
	</div>
</security:authorize>