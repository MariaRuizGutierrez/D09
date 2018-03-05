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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="stories" id="row" requestURI="story/explorer/list.do" pagesize="5" keepStatus="true">

	<spring:message code="stories.title" var="title" />
	<display:column property="title" title="${title}" sortable="true" />
	
	<spring:message code="stories.text" var="text" />
	<display:column property="text" title="${text}" sortable="true" />

	<spring:message code="stories.atachment" var="attachments" />
	<display:column property="attachments" title="${attachments}" sortable="true" />
	
	<spring:message code="stories.trip" var="trip" />
	<display:column property="trip.ticker" title="${trip}" sortable="true" />
	
	<spring:message code="story.edit" var="edit"></spring:message>
			<display:column title="${edit}" sortable="true">
				<spring:url value="story/explorer/edit.do" var="editURL">
					<spring:param name="storyId" value="${row.id }" />
				</spring:url>
				<a href="${editURL}"><spring:message code="story.edit" /></a>
			</display:column>
		
	

</display:table>