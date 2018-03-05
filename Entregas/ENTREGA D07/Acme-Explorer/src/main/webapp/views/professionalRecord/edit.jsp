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

<form:form action="${RequestURI }" modelAttribute="professionalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName" />:
	</form:label>
	<form:input path="companyName"/>
	<form:errors cssClass="error" path="companyName" />
	<br />
	
	<form:label path="workPeriod">
		<spring:message code="professionalRecord.workPeriod" />:
	</form:label>
	<form:input path="workPeriod" placeholder=" yyyy/yyyy or yyyy/-"/>
	<form:errors cssClass="error" path="workPeriod" />
	<br />
	
	<form:label path="role">
		<spring:message code="professionalRecord.role" />:
	</form:label>
	<form:input path="role"/>
	<form:errors cssClass="error" path="role" />
	<br />
	
	<form:label path="link">
		<spring:message code="professionalRecord.link" />:
	</form:label>
	<form:input path="link" placeholder="http://"/>
	<form:errors cssClass="error" path="link" />
	<br />
	
	<form:label path="comments">
		<spring:message code="professionalRecord.comments" />:
	</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	

	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />&nbsp; 
	<jstl:if test="${professionalRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="professionalRecord.delete" />"
			onclick="javascript: return confirm('<spring:message code="professionalRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel" />"
		onclick="javascript:  window.location.replace('curricula/ranger/display.do');" />
	<br />
</form:form>