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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="auditRecord" class="displaytag"
  requestURI="auditRecord/auditor/display.do" id="row">
  	
	<!-- Attributes -->

	<display:column>
	<spring:message code="auditRecord.title" />:
	<jstl:out value="${row.title }"></jstl:out>
	

	<p>
		<spring:message code="auditRecord.description" />:
		<jstl:out value="${row.description}"></jstl:out>
	</p>

	<p>
		<spring:message code="auditRecord.format.date" var="pattern"></spring:message>
	 	<spring:message code="auditRecord.realisedMoment" />:
		<jstl:out value="${row.realisedMoment}"></jstl:out>
	
	
 
 </p>

	<p>
		<spring:message code="auditRecord.attachments" />:
		<jstl:out value="${row.attachments}"></jstl:out>
	</p>
	<p>
		<spring:message code="auditRecord.draftMode" />:
		<jstl:out value="${row.draftMode}"></jstl:out>
	</p>
	

</display:column>
 </display:table>
