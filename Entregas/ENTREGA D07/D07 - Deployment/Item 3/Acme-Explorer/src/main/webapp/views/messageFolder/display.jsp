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



<display:table name="messageFolder" class="displaytag"
	requestURI="${requestURI }" id="row">

	<!-- Attributes -->

	<display:column>
		<spring:message code="messageFolder.name" />:
		<jstl:out value=":" />
		<jstl:out value="${row.name }"></jstl:out>

		<p>
			<spring:message code="messageFolder.modifiable" />
			<jstl:out value=":" />
			<jstl:out value="${row.modifiable}"></jstl:out>
		</p>

		<p>
			<spring:message code="messageFolder.messages"></spring:message>
			<jstl:out value=":" />

			<jstl:forEach var="messag" items="${row.messages }">

				<!-- Enlace para administrador -->
				<security:authorize access="hasRole('ADMINISTRATOR')">
					<spring:url value="message/administrator/display.do"
						var="displayMessageURL">

						<spring:param name="messageId" value="${messag.id}" />
					</spring:url>
				</security:authorize>

				<!-- Enlace para sponsor -->
				<security:authorize access="hasRole('SPONSOR')">
					<spring:url value="message/sponsor/display.do"
						var="displayMessageURL">

						<spring:param name="messageId" value="${messag.id}" />
					</spring:url>
				</security:authorize>
				<!-- Enlace para ranger -->
				<security:authorize access="hasRole('RANGER')">
					<spring:url value="message/ranger/display.do"
						var="displayMessageURL">

						<spring:param name="messageId" value="${messag.id}" />
					</spring:url>
				</security:authorize>
				<!-- Enlace para manager -->
				<security:authorize access="hasRole('MANAGER')">
					<spring:url value="message/manager/display.do"
						var="displayMessageURL">

						<spring:param name="messageId" value="${messag.id}" />
					</spring:url>
				</security:authorize>
				<!-- Enlace para auditor -->
				<security:authorize access="hasRole('AUDITOR')">
					<spring:url value="message/auditor/display.do"
						var="displayMessageURL">

						<spring:param name="messageId" value="${messag.id}" />
					</spring:url>
				</security:authorize>
				<!-- Enlace para explorer -->
				<security:authorize access="hasRole('EXPLORER')">
					<spring:url value="message/explorer/display.do"
						var="displayMessageURL">

						<spring:param name="messageId" value="${messag.id}" />
					</spring:url>
				</security:authorize>
				<a href="${displayMessageURL}"><jstl:out
						value="${messag.subject }" /></a>


			</jstl:forEach>


		</p>





	</display:column>
</display:table>
