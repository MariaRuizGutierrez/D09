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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<display:table name="rendezvous" class="displaytag"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->




	<spring:message code="rendezvouse.picture" var="titleHeader" />
	<display:column title="${titleHeader}">

		<div
			style="position: relative; width: 500px; height: 300px; margin-left: auto; margin-right: auto;">

			<img src="${row.picture}" width="500" height="300">
		</div>
	</display:column>
	<display:column>
		<B><spring:message code="rendezvouse.name" />:</B>
		<jstl:out value="${row.name}"></jstl:out>
		<p>
			<B><spring:message code="rendezvouse.description" />:</B>
			<jstl:out value="${row.description}"></jstl:out>
		<p>
			
 			<spring:message code="rendezvous.format.birthDate" var="pattern"></spring:message>
			<fmt:formatDate value="${row.organisedMoment}" pattern="${pattern}" var="newdatevar" />
			<B><spring:message code="rendezvouse.organisedMoment"></spring:message>:</B>
			<c:out value="${newdatevar}" />
			 
	
		<p>
			<B><spring:message code="rendezvouse.location.longitude" />:</B>
			<jstl:out value="${row.gps.longitude}"></jstl:out>
		<p>

			<B><spring:message code="rendezvouse.location.latitude" />:</B>
			<jstl:out value="${row.gps.latitude}"></jstl:out>
		<p>

			<B><spring:message code="rendezvouse.draftMode" />:</B>
			<jstl:out value="${row.draftMode}"></jstl:out>
		<p>

			<B><spring:message code="rendezvouse.deleted" />:</B>
			<jstl:out value="${row.deleted}"></jstl:out>
		<p>

			<B><spring:message code="rendezvouse.forAdult" />:</B>
			<jstl:out value="${row.forAdult}"></jstl:out>
		<p>
		
			<jstl:if test="${row.similarRendezvouses.size()>0 }">
			<B><spring:message code="rendezvouse.similarRendezvouses" />:</B></jstl:if>
			<jstl:forEach items="${row.similarRendezvouses}" var="item">
				<spring:url value="rendezvous/display.do"
					var="displayRendezvousSimilarURL">
					<spring:param name="rendezvousId" value="${item.id}" />

				</spring:url>
				<a href="${displayRendezvousSimilarURL}"><jstl:out
						value="${item.name }" /></a>
				<br>
			</jstl:forEach>
	</display:column>
	<p>
</display:table>
