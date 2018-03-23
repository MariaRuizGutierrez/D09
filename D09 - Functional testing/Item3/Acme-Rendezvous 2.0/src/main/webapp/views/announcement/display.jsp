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



<display:table name="XXX" class="displaytag"
	requestURI="xxx/manager/display.do" id="row">

	<!-- Attributes -->

	<%!String	estilo;%>
	<jstl:choose>

		<jstl:when test="${row.gauge=='1'}">
			<%=estilo = "grey"%>

		</jstl:when>

		<jstl:when test="${row.gauge=='2'}">

			<%=estilo = "yellow"%>
		</jstl:when>

		<jstl:when test="${row.gauge=='3'}">
			<%=estilo = "green"%>
		</jstl:when>

	</jstl:choose>
	<spring:message code="xxx.code" var="codeHeader" />
	<display:column property="code" title="${codeHeader}" sortable="true"
		class="<%= estilo %>" />

	<spring:message code="xxx.format.date2" var="pattern"></spring:message>
	<spring:message code="xxx.createMoment" var="createMomentHeader" />
	<display:column property="createMoment" title="${createMomentHeader}"
		sortable="true" format="${pattern}" class="<%= estilo %>">
	</display:column>

	<spring:message code="xxx.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true"
		class="<%= estilo %>" />

	<spring:message code="xxx.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true"
		class="<%= estilo %>" />

	<spring:message code="xxx.format.date" var="pattern"></spring:message>
	<spring:message code="xxx.displayedMoment" var="displayedMomentHeader" />
	<display:column property="displayedMoment"
		title="${displayedMomentHeader}" sortable="true" format="${pattern}"
		class="<%= estilo %>">
	</display:column>

	<!-- Display -->
	<jstl:if test="${!(row.trip == null)}">
		<spring:message code="xxx.trip.display" var="tripHeader" />
		<display:column title="${tripHeader}" class="<%= estilo %>">
			<spring:url value="trip/display.do" var="displayURL">
				<spring:param name="tripId" value="${row.trip.id}" />
			</spring:url>
			<a href="${displayURL}"><spring:message code="xxx.trip.display" /></a>
		</display:column>
	</jstl:if>
</display:table>
