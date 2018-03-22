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


<jstl:if test="${row.picture==''}">
	<spring:message code="nothing.found.to.display" />
	</jstl:if> 
	
	<jstl:if test="${!(row.picture=='') }">
	<spring:message code="rendezvouse.picture" var="titleHeader" />
	<display:column title="${titleHeader}">

		<div
			style="position: relative; width: 500px; height: 300px; margin-left: auto; margin-right: auto;">

			<img src="${row.picture}" width="500" height="300">
		</div>
	</display:column>
	</jstl:if>
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
		
			<security:authorize access="isAnonymous()">
			<jstl:if test="${similarRendezvouses.size()>0 }">
			<B><spring:message code="rendezvouse.similarRendezvouses" />:</B></jstl:if>
			<jstl:forEach items="${similarRendezvouses}" var="item">
				<spring:url value="rendezvous/display.do"
					var="displayRendezvousSimilarURL">
					<spring:param name="rendezvousId" value="${item.id}" />

				</spring:url>
				<a href="${displayRendezvousSimilarURL}"><jstl:out
						value="${item.name }" /></a>
				<br>
			</jstl:forEach>
			</security:authorize>
			
			<security:authorize access="hasRole('USER')">
			<jstl:if test="${similarRendezvouses.size()>0 }">
			<B><spring:message code="rendezvouse.similarRendezvouses" />:</B></jstl:if>
			<jstl:forEach items="${similarRendezvouses}" var="item">
				<spring:url value="rendezvous/user/display.do"
					var="displayRendezvousSimilarURL">
					<spring:param name="rendezvousId" value="${item.id}" />

				</spring:url>
				<a href="${displayRendezvousSimilarURL}"><jstl:out
						value="${item.name }" /></a>
				<br>
			</jstl:forEach>
			</security:authorize>
			
			<security:authorize access="hasRole('MANAGER')">
			<jstl:if test="${similarRendezvouses.size()>0 }">
			<B><spring:message code="rendezvouse.similarRendezvouses" />:</B></jstl:if>
			<jstl:forEach items="${similarRendezvouses}" var="item">
				<spring:url value="rendezvous/display.do"
					var="displayRendezvousSimilarURL">
					<spring:param name="rendezvousId" value="${item.id}" />

				</spring:url>
				<a href="${displayRendezvousSimilarURL}"><jstl:out
						value="${item.name }" /></a>
				<br>
			</jstl:forEach>
			</security:authorize>
			
			<security:authorize access="hasRole('ADMINISTRATOR')">
			<jstl:if test="${similarRendezvouses.size()>0 }">
			<B><spring:message code="rendezvouse.similarRendezvouses" />:</B></jstl:if>
			<jstl:forEach items="${similarRendezvouses}" var="item">
				<spring:url value="rendezvous/administrator/display.do"
					var="displayRendezvousSimilarURL">
					<spring:param name="rendezvousId" value="${item.id}" />

				</spring:url>
				<a href="${displayRendezvousSimilarURL}"><jstl:out
						value="${item.name }" /></a>
				<br>
			</jstl:forEach>
			</security:authorize>
			
	</display:column>
	<p>
	
</display:table>

<h2><spring:message code="rendezvouse.service.request" /></h2>
<jstl:if test="${row.servicesOffered.size()==0}">
	<spring:message code="nothing.found.to.display" />
	</jstl:if>
<jstl:if test="${row.servicesOffered.size()>0}">
	<display:table name="services" id="row1" class="displaytag">
	 
	<%!String estilo;%>
	<jstl:choose>
			<jstl:when test="${row1.cancelled==false}">
				<%=estilo = "p1"%>

			</jstl:when>

			<jstl:when test="${row1.cancelled==true}">

				<%=estilo = "p2"%>
			</jstl:when>
		</jstl:choose>
		
		<spring:message code="rendezvous.service.name" var="nameHeader"/>
		
		<spring:message code="announcement.format.madeMoment" var="pattern" ></spring:message>
		<display:column property="name" title="${nameHeader}" sortable="false" format="${pattern}" class="<%= estilo %>">
			<jstl:out value="${row1.name}"></jstl:out>
		</display:column>
		<spring:message code="rendezvous.announcements.description" var="titleHeader2" />
		<display:column property="description" title="${titleHeader2}" sortable="false" class="<%= estilo %>">
			<jstl:out value="${row1.description}"></jstl:out>
		</display:column>
		
		<spring:message code="serviceoffered.picture" var="titleHeader" />
	<display:column title="${titleHeader}" class="<%= estilo %>">
		<a href="${row1.picture}"><spring:message
				code="serviceoffered.picture" /></a>
	</display:column>
	
	<spring:message code="serviceoffered.category" var="category" />
		<display:column property="category.name" title="${category}" sortable="false" class="<%= estilo %>">
			<jstl:out value="${row1.category.name}"></jstl:out>
		</display:column>
	
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="cancelled" var="Delete" />
		<display:column title="${Delete}" class="<%= estilo %>">
		<jstl:if test="${row1.cancelled==true}">
			<FONT COLOR="yellow">
			<spring:message code="cancelled.cancelled"/>
			</FONT><br>
		</jstl:if>
		</display:column>
		</security:authorize>
		</display:table>
	</jstl:if>
	
	<h2><spring:message code="rendezvous.questions" /></h2>	
	<display:table name="questions" id="row" class="displaytag">
		<spring:message code="question" var="questionHeader"/>
		<display:column property="name" title="${questionHeader}" sortable="false"  >
			<jstl:out value="${row.name}"></jstl:out>
		</display:column>
		<spring:message code="question.answer" var="Answer" />
	<display:column title="${Answer}" sortable="true">
		<spring:url value="answer/list.do" var="editURL">
			<spring:param name="questionId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="question.answer" /></a>
	</display:column> 
</display:table>


<h2><spring:message code="rendezvous.announcement" /></h2>	
	<display:table name="announcements" id="row" class="displaytag">
		<spring:message code="rendezvous.announcements.madeMoment" var="madeMomentHeader"/>
		<spring:message code="announcement.format.madeMoment" var="pattern"></spring:message>
		<display:column property="madeMoment" title="${madeMomentHeader}" sortable="false" format="${pattern}" >
			<jstl:out value="${row.madeMoment}"></jstl:out>
		</display:column>
		<spring:message code="rendezvous.announcements.description" var="titleHeader2" />
		<display:column property="description" title="${titleHeader2}" sortable="false" >
			<jstl:out value="${row.description}"></jstl:out>
		</display:column>
		
		
	</display:table>
	

	 
	

	

