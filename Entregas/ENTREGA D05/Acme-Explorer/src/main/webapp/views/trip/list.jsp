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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing trips -->

<form:form action="trip/list.do" modelAttribute="trip">

	<form:label path="search">
		<spring:message code="trip.search" />:
	</form:label>
	<form:input path="search" />

	<security:authorize access="hasRole('MANAGER')">
		<form:label path="lowprice">
			<spring:message code="trip.lowprice" />:
	</form:label>
		<form:input path="lowprice" />

		<form:label path="highprice">
			<spring:message code="trip.highprice" />:
	</form:label>
		<form:input path="highprice" />

		<form:label path="trip.startDate">
			<spring:message code="trip.startDate" />:
	</form:label>
		<form:input path="startDate" />

		<form:label path="trip.finishDate">
			<spring:message code="trip.finishDate" />:
	</form:label>
		<form:input path="finishDate" />

	</security:authorize>

	<spring:url value="applicationFor/manager/edit.do" var="editlink">
		<spring:param name="search" value="search" />
		<spring:param name="lowprice" value="lowprice" />
		<spring:param name="highprice" value="highprice" />
		<spring:param name="startDate" value="startDate" />
		<spring:param name="finishDate" value="finishDate" />
	</spring:url>
	<input type="submit" name="search"
		value="<spring:message code="trip.search"/>"
		onClick=" window.location.href='${editlink}' ">/>&nbsp;
		
</form:form>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="trips" requestURI="${requestURI}" id="row">

	<display:column>
		<spring:url value="trip/display.do" var="displaydURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message code="trip.display" /></a>
	</display:column>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${publicated==false}">
			<display:column>
				<spring:url value="trip/manager/edit.do" var="editURL">
					<spring:param name="tripId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="trip.edit" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER)">
		<jstl:if test="${demand==false }">
			<display:column>
				<spring:url value="trip/explorer/apply.do" var="applyURL">
					<spring:param name="tripId" value="${row.id }" />
				</spring:url>
				<a href="${applyURL}"><spring:message code="trip.apply" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>



	<!-- Attributes -->
	<spring:message code="trip.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="trip.format.price" var="patternPrice "/>
	<spring:message code="trip.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" format="${patternPrice }"/>

	<spring:message code="trip.format.date" var="pattern"></spring:message>
	<spring:message code="trip.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}"
		sortable="true" format="${pattern}" />

	<spring:message code="trip.format.date" var="pattern"></spring:message>
	<spring:message code="trip.finishDate" var="finishDateHeader" />
	<display:column property="finishDate" title="${finishDateHeader}"
		sortable="true" format="${pattern}" />


</display:table>

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="trip/manager/create.do"> <spring:message
				code="trip.create" />
		</a>
	</div>
</security:authorize>