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
<jsp:useBean id="util" class="utilities.Methodutilities" scope="page" />


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvous" requestURI="${requestURI }" id="row">

<%!String estilo;%>
	<jstl:choose>
			<jstl:when test="${util.organisedMoment(row.organisedMoment)==false}">
				<%=estilo = "p2"%>

			</jstl:when>
			
			 <jstl:when test="${util.organisedMoment(row.organisedMoment)==true}">
				<%=estilo = "yellow"%>

			</jstl:when>
 
		</jstl:choose>
		
		
	<!-- ENLACE EDITAR Y DISPLAY-->
	<security:authorize access="hasRole('USER')">
	<spring:message code="rendezvous.display" var="Display" />
	<display:column title="${Display}" sortable="true" >
		<spring:url value="rendezvous/user/display.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvous.display" /></a>

	</display:column>
	</security:authorize>


	<!-- ATRIBUTOS -->
	<spring:message code="rendezvouse.name" var="titleHeader" />
	<display:column property="name" title="${titleHeader}" sortable="true" />

	<spring:message code="rendezvouse.description" var="titleHeader" />
	<display:column property="description" title="${titleHeader}"
		sortable="true" />

	<spring:message code="ren.format.date" var="pattern"></spring:message>
	<spring:message code="rendezvouse.organisedMoment" var="titleHeader"/>
	<display:column property="organisedMoment" title="${titleHeader}"
		sortable="true" format="${pattern}" class="<%= estilo %>"/>

	
	<spring:message code="rendezvouse.picture" var="titleHeader"/>
	<display:column title="${titleHeader}" >
	<jstl:if test="${!(row.picture=='')}">
		<a href="${row.picture}"><spring:message
				code="rendezvouse.picture" /></a>
	</jstl:if>
	</display:column>

	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvous.announcement" var="announcements" />
		<display:column title="${announcements}" sortable="true" >
			<spring:url value="announcement/user/list.do" var="announcementURL">
				<spring:param name="rendezvousId" value="${row.id }" />
			</spring:url>
			<a href="${announcementURL}"><spring:message
					code="rendezvous.announcement" /></a>
		</display:column>
	</security:authorize>




	<spring:message code="rendezvouse.question" var="Question" />
	<display:column title="${Question}" sortable="true" >
		<spring:url value="question/list.do" var="editURL">
			<spring:param name="rendezvouseId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvouse.question" /></a>
	</display:column>


	<spring:message code="rendezvouse.assistans" var="ASS" />
	<display:column title="${ASS}" sortable="true" >
		<spring:url value="rendezvous/listAssistants.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvouse.assistans" /></a>
	</display:column>


	<spring:message code="draftMode" var="draftMode" />
	<display:column title="${draftMode}">
		<jstl:if test="${row.draftMode==true}">
			<div
				style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

				<img src="images/no.png" width="30" height="30">
			</div>
		</jstl:if>
		<jstl:if test="${row.draftMode==false}">
			<div
				style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

				<img src="images/yes.png" width="30" height="30">
			</div>
		</jstl:if>
		
</display:column>
	

	<security:authorize access="hasRole('USER')">
		<spring:message code="forAdult" var="forAdult" />
		<display:column title="${forAdult}">
			<jstl:if test="${row.forAdult==true}">
				<div
					style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

					<img src="images/18.png" width="30" height="30">
				</div>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>


