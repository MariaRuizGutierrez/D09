<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="repositories.ApplicationForRepository"%>
<%@page import="domain.ApplicationFor"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page
	import="org.aspectj.weaver.reflect.Java14GenericSignatureInformationProvider"%>
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

<display:table class="displaytag" name="applicationFor"
	requestURI="${requestURI }" id="row">

	<display:column>

		<p>
			<spring:message code="applicationfor.trip" />
			:
			<jstl:out value="${row.trip.ticker}"></jstl:out>
		</p>
		<br>
		<spring:message code="applicationfor.moment" />:
			<jstl:out value="${row.moment }"></jstl:out>
		<br>
		<spring:message code="applicationfor.status" />:
			<jstl:out value="${row.status }"></jstl:out>
		<br>
		<jstl:if test="${row.status=='REJECTED'}">
			<spring:message code="applicationfor.reason" />:
			<jstl:out value="${row.reasonWhy}"></jstl:out>
			<br>
		</jstl:if>
		<jstl:choose>
			<jstl:when test="${row.status=='PENDING'}">
		<jstl:if test="${morethirtyDays==false}">
		<body style="background-color:red;">
		
		</jstl:if>
			</jstl:when>

			<jstl:when test="${row.status=='REJECTED'}">
				<body style="background-color: grey;">
			</jstl:when>

			<jstl:when test="${row.status=='DUE'}">
				<body style="background-color: yellow;">
			</jstl:when>

			<jstl:when test="${row.status=='ACCEPTED'}">
				<body style="background-color: green;">
			</jstl:when>

			<jstl:when test="${row.status=='CANCELLED'}">
				<body style="background-color: cyan;">
			</jstl:when>

		</jstl:choose>
	</display:column>

</display:table>
