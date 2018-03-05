<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="contactsEmergency" id="row"
	requestURI="contactsEmergency/explorer/list.do" pagesize="5" keepStatus="true">
	
	<spring:message code="contactsEmergency.phone" var="phone" />
	<display:column property="phone" title="${phone}" sortable="true" />
	
	<spring:message code="contactsEmergency.email" var="email" />
	<display:column property="email" title="${email}" sortable="true" />

	<spring:message code="contactsEmergency.name" var="name" />
	<display:column property="name" title="${name}" sortable="true" />
	
	<spring:message code="contactsEmergency.edit" var="Edit" />
	<display:column title="${Edit}" sortable="true">
		<spring:url value="contactsEmergency/explorer/edit.do" var="editURL">
		<spring:param name="contactEmergencyId" value="${row.id}"/>
		</spring:url>
		<a href="${editURL}"><spring:message code="contactsEmergency.edit"/></a>
	</display:column>	
	
	
</display:table>

	<input type="button" name="create" value="<spring:message code="contactsEmergency.create"/>"
	onclick="javascript: relativeRedir('contactsEmergency/explorer/create.do');" />