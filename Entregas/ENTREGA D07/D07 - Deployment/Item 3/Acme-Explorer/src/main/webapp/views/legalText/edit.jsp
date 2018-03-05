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


<form:form action="legalText/administrator/edit.do" modelAttribute="legalText">

	<form:hidden path="id" />
	<form:hidden path="version" /> 
	<form:hidden path="trips"/>

	<jstl:if test="${legalText.draftMode == true or legalText.id==0}">
	<form:label path="title">
		<spring:message code="legalText.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="body">
		<spring:message code="legalText.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="lawsNumber">
		<spring:message code="legalText.lawsNumber" />:
	</form:label>
	<form:input path="lawsNumber" />
	<form:errors cssClass="error" path="lawsNumber" />
	<br />
	
	<form:label path="moment" placeholder=" yyyy/dd/MM HH:hh">
		<spring:message code="legalText.moment" />:
	</form:label>
	<form:input path="moment" readonly="true"/>
	<form:errors cssClass="error" path="moment" />
	<br />
	
	<form:label path="draftMode">
		<spring:message code="auditRecord.draftMode" />:
	</form:label>
	<form:select id="draftModes" path="draftMode" multiple="true">	
		<form:option value="1" label="YES"/>
		<form:option value="0" label="NO"/> 
	</form:select>  
	<br /> 
	</jstl:if>
	
	
	<!-- botones --> 
	
	<input type="submit" name="save"
		value="<spring:message code="legalText.save"/>" />&nbsp;
		
	<jstl:if test="${legalText.id !=0 and legalText.draftMode == true}">
		<input type="submit" name="delete"
			value="<spring:message code="legalText.delete"/>"
			onclick="javascript: return confirm('<spring:message code="legalText.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="legalText.cancel"/>"
		onclick="javascript: window.location.replace('legalText/administrator/list.do')" />
	<br />
	
</form:form>