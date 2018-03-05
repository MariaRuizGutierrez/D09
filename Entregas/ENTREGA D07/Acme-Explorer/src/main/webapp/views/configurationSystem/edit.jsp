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

<form:form action="${RequestURI }" modelAttribute="configurationSystem">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="defaultCategories" />
	<form:hidden path="defaultTags" />
	
	
	<form:label path="maxNumberFinder">
		<spring:message code="configurationSystem.maxNumberFinder" />:
	</form:label>
	<form:input path="maxNumberFinder"/>
	<form:errors cssClass="error" path="maxNumberFinder" />
	<br />
	
	<form:label path="cacheMaxTime">
		<spring:message code="configurationSystem.cacheMaxTime" />:
	</form:label>
	<form:input path="cacheMaxTime"/>
	<form:errors cssClass="error" path="cacheMaxTime" />
	<br />
	
	<form:label path="banner">
		<spring:message code="configurationSystem.banner" />:
	</form:label>
	<form:input path="banner" placeholder="http://" />
	<form:errors cssClass="error" path="banner" />
	<br />

	<form:label path="spamWords">
		<spring:message code="configurationSystem.spamWords" />:
	</form:label>
	<form:input path="spamWords"/>
	<form:errors cssClass="error" path="spamWords" />
	<br />
	
	<form:label path="VAT">
		<spring:message code="configurationSystem.VAT" />:
	</form:label>
	<form:input path="VAT"/>
	<form:errors cssClass="error" path="VAT" />
	<br />
	
	<form:label path="defaultPhone">
		<spring:message code="configurationSystem.defaultPhone" />:
	</form:label>
	<form:input path="defaultPhone"/>
	<form:errors cssClass="error" path="defaultPhone" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="configurationSystem.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="configurationSystem.cancel" />"
		onclick="javascript:  window.location.replace('welcome/index.do');" />
	<br />
</form:form>