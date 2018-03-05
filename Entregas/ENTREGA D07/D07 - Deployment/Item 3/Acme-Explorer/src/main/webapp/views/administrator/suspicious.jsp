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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="suspicious" requestURI="${requestURI }" id="row">
	
	
	<!-- Attributes -->

	<spring:message code="actor.name" var="titleHeader" />:
	<display:column property="name" title="${titleHeader}" sortable="true" />
	
	<spring:message code="actor.ban" var="banHeader"></spring:message>
		<jstl:if test="${row.userAccount.activated==true}">
			
			<display:column title="${banHeader}" sortable="true">
			<spring:url value="suspicious/administrator/ban.do" var="banURL">
			<spring:param name="actorId" value="${row.id}"/>
			</spring:url>
			<a href="${banURL}"><spring:message code="actor.ban"/></a>
			</display:column> --%>
		</jstl:if>
	
	
		<jstl:if test="${row.userAccount.activated==false}">
			<display:column>
				<spring:url value="suspicious/administrator/unban.do" var="unbanHeader">
					<spring:param name="actorId" value="${row.id}" />
				</spring:url>
				<a href="${unbanHeader}"><spring:message code="actor.unban" /></a>
			</display:column>
		</jstl:if>

	
	
</display:table>