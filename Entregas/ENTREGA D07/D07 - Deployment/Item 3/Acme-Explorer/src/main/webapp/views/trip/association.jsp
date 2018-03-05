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


<form:form action="trip/manager_/association.do" modelAttribute="trip">

	<form:hidden path="id" />
	<form:hidden path="version" /> 
	<form:hidden path="ticker"/>
	<form:hidden path="price"/>
	<form:hidden path="cancelled"/>
	<form:hidden path="manager"/>
	<form:hidden path="stages"/>
	<form:hidden path="applicationsFor"/>
	<form:hidden path="notes"/>
	<form:hidden path="auditRecords"/>
	<form:hidden path="ranger"/>	
		
	<h2><spring:message code="trip.tags.name" /></h2>
	<form:label path="tags">
		<spring:message code="trip.tag" />:
	</form:label>
	<form:select id="tags" path="tags" multiple="multiple" >		
		<form:options items="${tags}" itemValue="id" itemLabel="name" />		
	</form:select>
	<form:errors cssClass="error" path="tags" />
	<br /> 
	<br /> 
	
	<form:label path="tag.value">
		<spring:message code="trip.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	<br />

		
</form:form>