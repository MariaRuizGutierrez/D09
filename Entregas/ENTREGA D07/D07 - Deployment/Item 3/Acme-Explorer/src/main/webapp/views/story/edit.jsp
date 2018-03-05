<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('EXPLORER')">
<form:form action="story/explorer/edit.do" modelAttribute="story">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trip"/>
	<form:hidden path="explorer"/>

	 <form:label path="title">
		<spring:message code="stories.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="text">
		<spring:message code="stories.text" />:
	</form:label>
	<form:input path="text" />
	<form:errors cssClass="error" path="text" />
	<br />
	
	<form:label path="attachments">
		<spring:message code="stories.attachments" />:
	</form:label>
	<form:input path="attachments" placeholder="http://"/>
	<form:errors cssClass="error" path="attachments" />
	<br />
	
	<br/>
<!-- 	botones -->

	
	<input type="submit" name="save"
		value="<spring:message code="stories.save" />" />&nbsp;
		
	<jstl:if test="${story.id !=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="story.delete"/>"
			onclick="javascript: return confirm('<spring:message code="story.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	
	
	<input type="button" name="cancel"
		value="<spring:message code="story.cancel" />"
		onclick="javascript: window.location.replace('story/explorer/list.do');" />
	
	<br /> 
	
	
	
</form:form>
</security:authorize>