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


<!-- Show url enrol-unEnrol -->
<security:authorize access="hasRole('EXPLORER')">
	<jstl:choose>
		<jstl:when test="${!enrol}">
			<spring:url value="survivalClass/explorer/list-enrol.do" var="enrolURL">
				<spring:param name="tripId" value="${tripId}" />
			</spring:url>
			<a href="${enrolURL}"><spring:message code="survivalClass.showEnrol" /></a>			
		</jstl:when>
		<jstl:otherwise>
			<spring:url value="survivalClass/explorer/list-not-enrol.do" var="enrolURL">
				<spring:param name="tripId" value="${tripId}" />
			</spring:url>
			<a href="${enrolURL}"><spring:message code="survivalClass.showNotEnrol" /></a><br />
			<br />			
		</jstl:otherwise>
	</jstl:choose>		
</security:authorize>


<!-- Listing survival Class -->
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="survivalClasses" requestURI="${requestURI}" id="row">

	<!-- Action links -->


	<!-- En caso de que se acceda al listado de survivalClass con manager se mostrara un boton de editar ademas del display -->
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="survivalClass.edit" var="edit"></spring:message>
		<display:column title="${edit}" sortable="true">
			<spring:url value="survivalClass/manager/edit.do" var="editURL">
				<spring:param name="survivalClassId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message code="survivalClass.edit" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<jstl:choose>
				<jstl:when test="${!enrol}">
					<a href="survivalClass/explorer/enrol.do?survivalClassId=${row.id}" 
					   onclick="javascript: return confirm('<spring:message code="survivalClass.confirm.enrol" />')">
						<spring:message code="survivalClass.enrol" />
					</a>					
				</jstl:when>
				<jstl:otherwise>
					<a href="survivalClass/explorer/notEnrol.do?survivalClassId=${row.id}">
						<spring:message code="survivalClass.notEnrol" />
					</a>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>



	<!-- El boton de display estara siempre presente en todos los usuarios que puedan acceder al listado de survival Class -->
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="survivalClass.display" var="display"></spring:message>
	<display:column title="${display}" sortable="true">
		<spring:url value="survivalClass/manager/display.do" var="displayURL">
			<spring:param name="survivalClassId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message
				code="survivalClass.display" /></a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
	<spring:message code="survivalClass.display" var="display"></spring:message>
	<display:column title="${display}" sortable="true">
		<spring:url value="survivalClass/explorer/display.do" var="displayURL">
			<spring:param name="survivalClassId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message
				code="survivalClass.display" /></a>
	</display:column>
	</security:authorize>


	<!-- Attributes -->
	<spring:message code="survivalClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
</display:table>

