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

<display:table name="trips" id="row" requestURI="trip/explorer/listTrip.do" pagesize="5" keepStatus="true">

	<spring:message code="trip.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" sortable="true" />
	
	<spring:message code="trip.title" var="title" />
	<display:column property="title" title="${title}" sortable="true" />

	<spring:message code="trip.description" var="description" />
	<display:column property="description" title="${description}" sortable="true" />
	
	<spring:message code="story.format.price" var="patternPrice"/>
	<spring:message code="trip.price" var="price" />
	<display:column property="price" title="${price}" sortable="true" format="${patternPrice}"/>
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column title="Create story" sortable="true">
		<spring:url value="story/explorer/create.do" var="editURL">
			<spring:param name="tripId" value="${row.id }" />
		</spring:url>
		<a href="${editURL}"><spring:message code="story.create" /></a>
		</display:column>
		
	</security:authorize>
	
	

</display:table>

