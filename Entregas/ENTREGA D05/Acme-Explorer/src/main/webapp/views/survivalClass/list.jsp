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

<!-- Listing survival Class -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="survivalClasses" requestURI="${requestURI}" id="row">

	<!-- Action links -->


	<!-- En caso de que se acceda al listado de survivalClass con manager se mostrara un boton de editar ademas del display -->
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<spring:url value="survivalClass/manager/edit.do" var="editURL">
				<spring:param name="survivalClassId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message code="survivalClass.edit" /></a>
		</display:column>
	</security:authorize>

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



	<!-- El boton de display estara siempre presente en todos los usuarios que puedan acceder al listado de survival Class -->
	<display:column>
		<spring:url value="survivalClass/explorer/display.do" var="displayURL">
			<spring:param name="survivalClassId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message
				code="survivalClass.display" /></a>
	</display:column>


	<!-- Attributes -->
	<spring:message code="survivalClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="survivalClass.tripTitle" var="tripTitleHeader" />
	<display:column property="trip.title" title="${tripTitleHeader }"
		href="trip/display.do?tripId=${row.trip.id}" />

</display:table>

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="survivalClass/manager/create.do"> <spring:message
				code="survivalClass.create" />
		</a>
	</div>
</security:authorize>