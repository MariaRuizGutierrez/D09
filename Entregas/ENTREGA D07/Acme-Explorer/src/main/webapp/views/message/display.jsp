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


<display:table name="messageDisplay" class="displaytag"
	requestURI="${requestURI }" id="row">

	<!-- Attributes -->
	<display:column>

		<spring:message code="message.moment" />
		<jstl:out value=":" />
		<jstl:out value="${row.moment}"></jstl:out>

		<p>
			<spring:message code="message.subject"></spring:message>
			<jstl:out value=":" />
			<jstl:out value="${row.subject}"></jstl:out>
		</p>

		<p>
			<spring:message code="message.body"></spring:message>
			<jstl:out value=":" />
			<jstl:out value="${row.body}"></jstl:out>
		</p>

		<p>
			<spring:message code="message.priority"></spring:message>
			<jstl:out value=":" />
			<jstl:out value="${row.priority}"></jstl:out>
		</p>


		<p>
			<spring:message code="message.messageFolder"></spring:message>
			<jstl:out value=":" />
			<jstl:out value="${row.messageFolder.name}"></jstl:out>
		</p>

		<p>
			<spring:message code="message.sender"></spring:message>
			<jstl:out value=":" />
			<jstl:out value="${row.sender.surname }, ${row.sender.name} "></jstl:out>
		</p>

		<p>
			<spring:message code="message.recipient"></spring:message>
			<jstl:out value=":" />
			<jstl:out value="${row.recipient.surname }, ${row.recipient.name}"></jstl:out>
		</p>


	</display:column>


</display:table>

<jstl:if test="${row.sender!=actorPrincipal }">
<security:authorize access="hasRole('ADMINISTRATOR')">



	<div>
		<spring:url value="message/administrator/reply.do"
			var="replyAdministratorURL">
			<spring:param name="messageId" value="${row.id}" />
		</spring:url>
		<spring:message code="message.reply.link" var="replyLink"></spring:message>
		<input type="button"
			onclick="location.href='${replyAdministratorURL}';"
			value="${replyLink }">
			
	</div>
	
</security:authorize>

<security:authorize access="hasRole('RANGER')">

	<div>
		<spring:url value="message/ranger/reply.do"
			var="replyRangerURL">
			<spring:param name="messageId" value="${row.id}" />
		</spring:url>
		<spring:message code="message.reply.link" var="replyLink"></spring:message>
		<input type="button"
			onclick="location.href='${replyRangerURL}';"
			value="${replyLink }">
			
	</div>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">

	<div>
		<spring:url value="message/explorer/reply.do"
			var="replyExplorerURL">
			<spring:param name="messageId" value="${row.id}" />
		</spring:url>
		<spring:message code="message.reply.link" var="replyLink"></spring:message>
		<input type="button"
			onclick="location.href='${replyExplorerURL}';"
			value="${replyLink }">
			
	</div>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">

	<div>
		<spring:url value="message/manager/reply.do"
			var="replyManagerURL">
			<spring:param name="messageId" value="${row.id}" />
		</spring:url>
		<spring:message code="message.reply.link" var="replyLink"></spring:message>
		<input type="button"
			onclick="location.href='${replyManagerURL}';"
			value="${replyLink }">
			
	</div>
</security:authorize>

<security:authorize access="hasRole('SPONSOR')">

	<div>
		<spring:url value="message/sponsor/reply.do"
			var="replySponsorURL">
			<spring:param name="messageId" value="${row.id}" />
		</spring:url>
		<spring:message code="message.reply.link" var="replyLink"></spring:message>
		<input type="button"
			onclick="location.href='${replySponsorURL}';"
			value="${replyLink }">
			
	</div>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">

	<div>
		<spring:url value="message/auditor/reply.do"
			var="replyAuditorURL">
			<spring:param name="messageId" value="${row.id}" />
		</spring:url>
		<spring:message code="message.reply.link" var="replyLink"></spring:message>
		<input type="button"
			onclick="location.href='${replyAuditorURL}';"
			value="${replyLink }">
			
	</div>
</security:authorize>
</jstl:if>