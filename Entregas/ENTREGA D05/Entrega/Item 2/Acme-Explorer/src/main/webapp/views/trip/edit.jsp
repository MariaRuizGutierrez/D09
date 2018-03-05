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

<form:form action="trip/manager/edit.do" modelAttribute="trip">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="manager"/>
	<input id="cancelled" name="cancelled" type="hidden" value="0"/>

	<form:label path="title">
		<spring:message code="trip.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="description">
		<spring:message code="trip.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="requirementsExplorers">
		<spring:message code="trip.requirementsExplorers" />:
	</form:label>
	<form:input path="requirementsExplorers" />
	<form:errors cssClass="error" path="requirementsExplorers" />
	<br />
	
	<form:label path="publicationDate">
		<spring:message code="trip.publicationDate" />:
	</form:label>
	<form:input path="publicationDate" placeholder=" yyyy/dd/MM HH:hh" />
	<form:errors cssClass="error" path="publicationDate" />
	<br />
	
	<form:label path="startDate">
		<spring:message code="trip.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder=" yyyy/dd/MM HH:hh" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<form:label path="finishDate">
		<spring:message code="trip.finishDate" />:
	</form:label>
	<form:input path="finishDate" placeholder=" yyyy/dd/MM HH:hh" />
	<form:errors cssClass="error" path="finishDate" />
	<br />
	
	<form:label path="ranger">
		<spring:message code="trip.ranger" />:
	</form:label>
	<form:select id="rangers" path="ranger" >
		<form:option value="0" label="----" />		
		<form:options items="${rangers}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="ranger" />
	<br />
	
	<form:label path="legalText">
		<spring:message code="trip.legalText" />:
	</form:label>
	<form:select id="legalTexts" path="legalText" >
		<form:option value="0" label="----" />		
		<form:options items="${legalTexts}" itemValue="id"
			itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="legalText" />
	<br />
	
	<form:label path="tag">
		<spring:message code="trip.tag" />:
	</form:label>
	<form:select id="tags" path="tag" >
		<form:option value="0" label="----" />		
		<form:options items="${tags}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="tag" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="trip.save" />" />&nbsp; 
	<jstl:if test="${trip.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="trip.delete" />"
			onclick="javascript: return confirm('<spring:message code="trip.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="trip.cancel" />"
		onclick="javascript:  window.location.replace('trip/manager/list.do');" />
	<br />
</form:form>
