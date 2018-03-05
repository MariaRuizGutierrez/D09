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



<form:form action="trip/manager_/cancelTrip.do" modelAttribute="trip">

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
	

<form:label path="title">
		<spring:message code="trip.title" />:
	</form:label>
	<form:input path="title"  readonly="true" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	
	<form:label path="description">
		<spring:message code="trip.description" />:
	</form:label>
	<form:input path="description"  readonly="true" />
	<form:errors cssClass="error" path="description" />
	<br />
	<br />
	
	<form:label path="requirementsExplorers">
		<spring:message code="trip.requirementsExplorers" />:
	</form:label>
	<form:input path="requirementsExplorers"  readonly="true" />
	<form:errors cssClass="error" path="requirementsExplorers" />
	<br />
	<br />
	
	<form:label path="publicationDate">
		<spring:message code="trip.publicationDate" />:
	</form:label>
	<form:input path="publicationDate" placeholder=" yyyy/dd/MM HH:hh"  readonly="true" />
	<form:errors cssClass="error" path="publicationDate" />
	<br />
	<br />
	
	<form:label path="startDate">
		<spring:message code="trip.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder=" yyyy/dd/MM HH:hh"  readonly="true" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	<br />
	
	<form:label path="finishDate">
		<spring:message code="trip.finishDate" />:
	</form:label>
	<form:input path="finishDate" placeholder=" yyyy/dd/MM HH:hh"  readonly="true" />
	<form:errors cssClass="error" path="finishDate" />
	<br />
	<br />
	
	<form:label path="reasonWhy">
		<spring:message code="trip.reasonWhy" />:
	</form:label>
	<form:input path="reasonWhy" />
	<form:errors cssClass="error" path="reasonWhy" />
	<br />
	<br />
	
	
	<input type="submit" name="save" value="<spring:message code="trip.save" />"
			onclick="javascript: return confirm('<spring:message code="trip.confirm.cancel" />')" />&nbsp;
	<br />
</form:form>