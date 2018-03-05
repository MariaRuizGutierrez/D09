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



<display:table name="sponsorship" class="displaytag"
  requestURI="sponsorship/sponsor/display.do" id="row">
  
  <display:column>
	
	<a href="${row.link}"><spring:message code = "sponsorship.link"></spring:message></a>
	
	<p>		
		<a href="${row.bannerURL}"><spring:message code="sponsorship.bannerURL.banner"/></a>
	</p>
	
	<form:label path="sponsorship.creditCard">
		<spring:message code="sponsorship.creditCard" />:
	</form:label>
	<br />
	
	<br/> 
	
	<fieldset>
	<spring:message code="sponsorship.creditCard.holderName" />:
		<jstl:out value="${row.creditCard.holderName}"></jstl:out>
	<p>
	
	<spring:message code="sponsorship.creditCard.brandName" />:
		<jstl:out value="${row.creditCard.brandName}"></jstl:out>
	<p>
	
	<spring:message code="sponsorship.creditCard.number" />:
		<jstl:out value="${row.creditCard.number}"></jstl:out>
	<p>
	
	<spring:message code="sponsorship.creditCard.expirationMonth" />:
		<jstl:out value="${row.creditCard.expirationMonth}"></jstl:out>
	<p>
	
	<spring:message code="sponsorship.creditCard.expirationYear" />:
		<jstl:out value="${row.creditCard.expirationYear}"></jstl:out>
	<p>
	
	<spring:message code="sponsorship.creditCard.cvv" />:
		<jstl:out value="${row.creditCard.cvv}"></jstl:out>
	<p>
	
	
	</fieldset>

	<br />
	
	 
		<spring:url value="trip/display.do" var="displayURL">
		<spring:param name="tripId" value="${row.trip.id}"/>
		</spring:url>
		<a href="${displayURL}"><spring:message code="sponsorship.trip.display"/></a>
		

</display:column>
	

</display:table>
