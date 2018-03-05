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


<display:table name="legalText" class="displaytag"
  requestURI="legalText/administrator/display.do" id="row">
  	
	<!-- Attributes -->

	<display:column>
	<spring:message code="legalText.title" />:
	<jstl:out value="${row.title }"></jstl:out>
	

	<p>
		<spring:message code="legalText.body" />:
		<jstl:out value="${row.body}"></jstl:out>
	</p>
	
	<p>
		<spring:message code="legalText.lawsNumber" />:
		<jstl:out value="${row.lawsNumber}"></jstl:out>
	</p>

	<p>
		<spring:message code="legalText.format.date" var="pattern"></spring:message>
	 	<spring:message code="legalText.moment" />:
		<jstl:out value="${row.moment}"></jstl:out>
 
	 </p>
	
	<p>
		<spring:message code="legalText.trip.title"></spring:message>:
		<jstl:forEach var="trip" items="${row.trips}">
			<jstl:out value="${trip.title}"></jstl:out><br/>
		
		</jstl:forEach>	
	</p>

	<p>
		<spring:message code="legalText.draftMode" />:
		<jstl:out value="${row.draftMode}"></jstl:out>
	</p>
	

</display:column>
 </display:table>