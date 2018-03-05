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


<table>

	<caption class="caption">

		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfRendezvouseCreatedPerUser" />


	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${findAvgStddevOfTheNumOfRendezvouseCreatedPerUser }">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findRatioUsersWithRendezvousesAndNotRendezvouses" />
	</caption>

	<tr>
		<td><jstl:out
				value="${ findRatioUsersWithRendezvousesAndNotRendezvouses}"></jstl:out></td>
	</tr>
</table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfAssistansPerRendezvouse" />
	</caption>

	<tr>
		<td><jstl:out
				value="${ findAvgStddevOfTheNumOfAssistansPerRendezvouse}"></jstl:out></td>
	</tr>
</table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfRendezvouseAssitedPerUser" />
	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${findAvgStddevOfTheNumOfRendezvouseAssitedPerUser }">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>

<caption class="caption">
	<spring:message
		code="dashboard.findTop10RendezvouseWithRSVPd" />
</caption>
<display:table name="findTop10RendezvouseWithRSVPd" id="row"
	class="displaytag">
	<spring:message code="rendezvouse.name" var="nameHeader" />
	<display:column title="${nameHeader}">
		<spring:url value="rendezvous/display.do" var="idURL">
			<spring:param name="rendezvousId" value="${row.id }" />
		</spring:url>
		<a href="${idURL}"><jstl:out value="${row.name}" /></a>
	</display:column>
</display:table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfAnnouncementsPerRendezvous" />
	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${findAvgStddevOfTheNumOfAnnouncementsPerRendezvous }">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>

<caption class="caption">
	<spring:message code="dashboard.findRendezvousesWithMore75PerCent" />
</caption>
<display:table name="findRendezvousesWithMore75PerCent" id="row"
	class="displaytag">
	<spring:message code="rendezvouse.name" var="nameHeader" />
	<display:column title="${nameHeader}">
		<spring:url value="rendezvous/display.do" var="idURL">
			<spring:param name="rendezvousId" value="${row.id }" />
		</spring:url>
		<a href="${idURL}"><jstl:out value="${row.name}" /></a>
	</display:column>
</display:table>

<caption class="caption">
	<spring:message code="dashboard.findRendezvousesWithAreLinked" />
</caption>
<display:table name="findRendezvousesWithAreLinked" id="row"
	class="displaytag">
	<spring:message code="rendezvouse.name" var="nameHeader" />
	<display:column title="${nameHeader}">
		<spring:url value="rendezvous/display.do" var="idURL">
			<spring:param name="rendezvousId" value="${row.id }" />
		</spring:url>
		<a href="${idURL}"><jstl:out value="${row.name}" /></a>
	</display:column>
</display:table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfQuestionsPerRendezvous" />
	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${findAvgStddevOfTheNumOfQuestionsPerRendezvous }">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous" />
	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous }">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>

<table>
	<caption class="caption">
		<spring:message
			code="dashboard.findAvgStddevOfTheNumOfRepliesPerComment" />
	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${findAvgStddevOfTheNumOfRepliesPerComment }">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>