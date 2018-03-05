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


<display:table name="note" class="displaytag"
  requestURI="${requestURI}" id="row">
  	
	<!-- Attributes -->

	<display:column>
	<spring:message code="note.createdMoment" />:
	<jstl:out value="${row.createdMoment }"></jstl:out>

	<p>
		<spring:message code="note.body" />:
		<jstl:out value="${row.body}"></jstl:out>
	</p>

<p>
		<spring:message code="note.remark" />:
		<jstl:out value="${row.remark}"></jstl:out>
	</p>
	
	<p>
		<spring:message code="note.reply" />:
		<jstl:out value="${row.reply}"></jstl:out>
	</p>
	
	<p>
		<spring:message code="note.replyMoment" />:
		<jstl:out value="${row.replyMoment}"></jstl:out>
	</p>

</display:column>
 </display:table>
