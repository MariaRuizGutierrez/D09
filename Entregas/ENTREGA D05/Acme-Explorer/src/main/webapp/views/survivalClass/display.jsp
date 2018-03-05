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


	<spring:message code="survivalClass.title" />
	<jstl:out value="${row.title }"></jstl:out>

	<p>
		<spring:message code="survivalClass.description" />
		<jstl:out value="${row.description }"></jstl:out>
	</p>

	<p>
		<spring:message code="survivalClass.organisedMoment" />
		<jstl:out value="${row.organisedMoment }"></jstl:out>
	</p>

	<p>
		<spring:message code="survivalClass.location" />
		<jstl:out value="${row.location }"></jstl:out>
	</p>

	<display:column>
		<spring:message code="survivalClass.managerName" />
		<spring:url value="manager/display.do" var="displayManagerURL">
			<spring:param name="managerId" value="${row.manager.id}" />
		</spring:url>
		<a href="${displayManagerURL}"><jstl:out
				value="${row.manager.name }"></jstl:out></a>
	</display:column>


	<display:column>
		<spring:message code="survivalClass.explorersName" />
		<spring:url value="explorer/display.do" var="displayExplorersURL">
			<spring:param name="explorerId" value="${row.explorer.id}" />
		</spring:url>
		<a href="${displayExplorersURL}"><jstl:out
				value="${row.explorers.name }"></jstl:out></a>
	</display:column>



	<display:column>
		<spring:message code="survivalClass.tripTitle" />
		<spring:url value="trip/display.do" var="displayTripURL">
			<spring:param name="tripId" value="${row.trip.id}" />
		</spring:url>
		<a href="${displayTripURL}"><jstl:out value="${row.trip.name }"></jstl:out></a>
	</display:column>



	<security:authorize access="hasRole('EXPLORER')">
		<jstl:if test="${registered==false}">
			<display:column>
				<spring:url value="survivalClass/explorer/registration.do"
					var="registeredURL">
					<spring:param name="survivalClassId" value="${row.id}" />
				</spring:url>
				<a href="${registeredURL}"><spring:message
						code="survivalClass.register" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>


</display:table>
