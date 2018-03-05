<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
 
 
<form:form action="${requestURI }" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path ="auditor"/>
	<form:hidden path="replyMoment"/>
	<form:hidden path ="reply"/>
	
	
	<form:label path="createdMoment" placeholder=" yyyy/dd/MM HH:hh" >
		<spring:message code="note.createdMoment" />:
	</form:label>
	<form:input path="createdMoment" readonly="true"/>
	<br />
	
	<form:label path="body">
		<spring:message code="note.body" />:
	</form:label>
	<form:input path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="remark">
		<spring:message code="note.remark" />
	</form:label>
	<form:input path="remark" />
	<form:errors cssClass="error" path="remark" />
	<br />
	

	<br/>
	
	<!-- botones -->
	<input type="submit" name="save"
		value="<spring:message code="note.save"/>" />&nbsp;
	
	
	
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel"/>"
		onclick="javascript: window.location.replace('note/auditor/list.do')" />
	<br />
</form:form>
 