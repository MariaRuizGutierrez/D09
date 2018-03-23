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



<display:table name="comment" class="displaytag"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->




	<spring:message code="comment.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}">

		<div
			style="position: relative; width: 500px; height: 300px; margin-left: auto; margin-right: auto;">

			<img src="${row.picture}" width="500" height="300">
		</div>
	</display:column>


	<display:column>
	
	<p>
		<spring:message code="comment.format.birthDate" var="pattern"></spring:message>
		<fmt:formatDate value="${row.writtenMoment}" pattern="${pattern}" var="newdatevar" />
		<B><spring:message code="comment.writtenMoment"></spring:message>:</B>
		<c:out value="${newdatevar}" />
	</p>
		
	<p>
			<B><spring:message code="comment.text" />:</B>
			<jstl:out value="${row.text}"></jstl:out>
	</p>
		<p>
		<B><spring:message code="comment.user" />:</B>
			<spring:url value="user/display.do" var="userURL">
				<spring:param name="userId" value="${row.user.id }" />
			</spring:url>
			<a href="${userURL}"><jstl:out value="${row.user.name }" /></a>
	</display:column>
	
	
</display:table>
