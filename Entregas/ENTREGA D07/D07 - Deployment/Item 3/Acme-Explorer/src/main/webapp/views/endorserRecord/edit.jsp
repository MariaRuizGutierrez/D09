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

<form:form action="${RequestURI }" modelAttribute="endorserRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="fullName">
		<spring:message code="endorserRecord.fullName" />:
	</form:label>
	<form:input path="fullName"/>
	<form:errors cssClass="error" path="fullName" />
	<br />
	
	<form:label path="email">
		<spring:message code="endorserRecord.email" />:
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="linkedProfile">
		<spring:message code="endorserRecord.linkedProfile" />:
	</form:label>
	<form:input path="linkedProfile" placeholder="http://"/>
	<form:errors cssClass="error" path="linkedProfile" />
	<br />
	
	<form:label path="comments">
		<spring:message code="endorserRecord.comments" />:
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<form:label path="phone">
		<spring:message code="endorserRecord.phone" />:
	</form:label>
	<form:input path="phone"/>
	<form:errors cssClass="error" path="phone" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="endorserRecord.save" />" />&nbsp; 
	<jstl:if test="${endorserRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="endorserRecord.delete" />"
			onclick="javascript: return confirm('<spring:message code="endorserRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="endorserRecord.cancel" />"
		onclick="javascript:  window.location.replace('curricula/ranger/display.do');" />
	<br />
</form:form>