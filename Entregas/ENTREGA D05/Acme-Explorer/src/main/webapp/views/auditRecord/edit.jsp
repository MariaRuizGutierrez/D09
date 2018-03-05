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

// TODO: TO BE COMPLETED

<form:form action="auditRecord/auditor/edit.do" modelAttribute="auditRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="auditor"/> 
	
	<form:label path="title">
		<spring:message code="auditRecord.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="realisedMoment">
		<spring:message code="auditRecord.realisedMoment" />:
	</form:label>
	<form:input path="realisedMoment" readonly="true"/>
	<form:errors cssClass="error" path="realisedMoment" />
	<br />
	
	<form:label path="description">
		<spring:message code="auditRecord.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="attachments">
		<spring:message code="auditRecord.attachments" />:
	</form:label>
	<form:input path="attachments" />
	<form:errors cssClass="error" path="attachments" />
	<br />
	
	<form:label path="draftMode">
		<spring:message code="auditRecord.draftMode" />:
	</form:label>
	<form:select id="draftModes" path="draftMode">
		<form:option value="0" label="----" />		
		<form:option value="1" label="YES"/>
		<form:option value="2" label="NO"/>
	</form:select>
	<br />
	
	<!-- botones --> 
	
	<input type="submit" name="save"
		value="<spring:message code="auditRecord.save"/>" />&nbsp;
		
	<jstl:if test="${auditRecord.id !=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="auditRecord.delete"/>"
			onclick="javascript: return confirm('<spring:message code="auditRecord.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="auditRecord.cancel"/>"
		onclick="javascript: window.location.replace('auditRecord/auditor/list.do')" />
	<br />
	
</form:form>
