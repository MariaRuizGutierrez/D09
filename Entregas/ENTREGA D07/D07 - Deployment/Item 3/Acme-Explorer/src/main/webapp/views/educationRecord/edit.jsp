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

<form:form action="${RequestURI }" modelAttribute="educationRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="diplomaTitle">
		<spring:message code="educationRecord.diplomaTitle" />:
	</form:label>
	<form:input path="diplomaTitle"/>
	<form:errors cssClass="error" path="diplomaTitle" />
	<br />
	
	<form:label path="studyingPeriod">
		<spring:message code="educationRecord.studyingPeriod" />:
	</form:label>
	<form:input path="studyingPeriod" placeholder=" yyyy/yyyy or yyyy/-"/>
	<form:errors cssClass="error" path="studyingPeriod" />
	<br />
	
	<form:label path="institution">
		<spring:message code="educationRecord.institution" />:
	</form:label>
	<form:input path="institution"/>
	<form:errors cssClass="error" path="institution" />
	<br />
	
	<form:label path="link">
		<spring:message code="educationRecord.link" />:
	</form:label>
	<form:input path="link" placeholder="http://"/>
	<form:errors cssClass="error" path="link" />
	<br />
	
	<form:label path="comments">
		<spring:message code="educationRecord.comments" />:
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments" />
	<br />
	

	<input type="submit" name="save"
		value="<spring:message code="educationRecord.save" />" />&nbsp; 
	<jstl:if test="${educationRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="educationRecord.delete" />"
			onclick="javascript: return confirm('<spring:message code="educationRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript:  window.location.replace('curricula/ranger/display.do');" />
	<br />
</form:form>