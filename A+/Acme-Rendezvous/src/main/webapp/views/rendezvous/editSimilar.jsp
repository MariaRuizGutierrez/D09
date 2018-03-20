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


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="rendezvous/user/editSimilar.do"
	modelAttribute="rendezvouse">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="assistants" />
	<form:hidden path="announcements" />
	<form:hidden path="name" />
	<form:hidden path="description" />
	<form:hidden path="picture" />
	<form:hidden path="organisedMoment" />
	<form:hidden path="draftMode" />
	<form:hidden path="forAdult" />
	<form:hidden path="gps.longitude" />
	<form:hidden path="gps.latitude" />



	<acme:select items="${notSimilarRendezvouses}" itemLabel="name"
		code="rendezvous.similarRendezvouses" path="similarRendezvouses" />

	<br />
	<br />

	<!-- botones -->

	<input type="submit" name="link"
		value="<spring:message code="rendezvous.link"/>" />&nbsp;
		
		<acme:cancel
		url="rendezvous/user/list.do"
		code="rendezvous.cancel" />
	<br />

</form:form>


