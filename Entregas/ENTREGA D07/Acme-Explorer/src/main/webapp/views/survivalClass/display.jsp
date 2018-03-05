<%--
 * display.jsp
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

<!-- Display survival Class -->

<display:table name="survivalClass" class="displaytag"
	requestURI="${requestURI }" id="row">

	<!-- Attributes -->

<display:column>
	<spring:message code="survivalClass.title" />:
	<jstl:out value="${row.title }"></jstl:out>

	<p>
		<spring:message code="survivalClass.description" />:
		<jstl:out value="${row.description }"></jstl:out>
	</p>

	<p>
		<spring:message code="survivalClass.organisedMoment" />:
		<jstl:out value="${row.organisedMoment }"></jstl:out>
	</p>

	<p>
		<spring:message code="survivalClass.location" />:
		<jstl:out value="${row.location.name }"></jstl:out>
	</p>
	
	<p>
		<spring:message code="survivalClass.location.longitude" />:
		<jstl:out value="${row.location.longitude }"></jstl:out>
	</p>
	
	<p>
		<spring:message code="survivalClass.location.latitude" />:
		<jstl:out value="${row.location.latitude }"></jstl:out>
	</p>

	<spring:url value="trip/display.do" var="displayURL">
		<spring:param name="tripId" value="${row.trip.id}"/>
		</spring:url>
		<a href="${displayURL}"><spring:message code="survivalClass.trip.display"/></a>
		

	</display:column>


</display:table>
