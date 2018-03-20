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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="question/user/edit.do" modelAttribute="question">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
<!-- ATRIBUTOS -->

	
	
	
	<acme:textbox code="question.name" path="name"/>
	<br />
	
<h2><spring:message code="question.rendezvouse" /></h2>
	<form:label path="rendezvouse">
		<spring:message code="question.rendezvouse" />:
	</form:label>
	<form:select id="rendezvouse" path="rendezvouse" >
		<form:option value="0" label="----" />			
		<form:options items="${rendezvouses}" itemValue="id" itemLabel="name" />		
	</form:select>
	<form:errors cssClass="error" path="rendezvouse" />
	<br />
	<br />
<!-- BOTONES -->

	<input type="submit" name="save" value="<spring:message code="question.save" />" />&nbsp; 
	
	<jstl:if test="${question.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="question.delete" />"
			onclick="javascript: return confirm('<spring:message code="question.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="question.cancel" />"
		onclick="javascript:  window.location.replace('question/user/list.do');" />
	<br />
</form:form>
