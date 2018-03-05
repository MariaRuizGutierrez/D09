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

<form:form action="${RequestURI }" modelAttribute="miscellaneousRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="title">
		<spring:message code="miscellaneousRecord.title" />:
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="link">
		<spring:message code="miscellaneousRecord.link" />:
	</form:label>
	<form:input path="link" placeholder="http://"/>
	<form:errors cssClass="error" path="link" />
	<br />
	
	<form:label path="comments">
		<spring:message code="miscellaneousRecord.comments" />:
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="miscellaneousRecord.save" />" />&nbsp; 
	<jstl:if test="${miscellaneousRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="miscellaneousRecord.delete" />"
			onclick="javascript: return confirm('<spring:message code="miscellaneousRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="miscellaneousRecord.cancel" />"
		onclick="javascript:  window.location.replace('curricula/ranger/display.do');" />
	<br />
</form:form>