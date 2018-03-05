
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


<form:form action="${requestURI }" modelAttribute="messageFolder">


	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="messages"/>
	<form:hidden path="modifiable"/>

	<form:label path="name">
		<spring:message code="messageFolder.name" />:
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name" />
	<br />
	
	

	<!-- Boton save y deletes -->
	<input type="submit" name="save"
		value="<spring:message code="messageFolder.save" />" />&nbsp; 
	<jstl:if test="${messageFolder.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="messageFolder.delete" />"
			onclick="javascript: return confirm('<spring:message code="messageFolder.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<!--  Cancel para administrator -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<input type="button" name="cancel"
		value="<spring:message code="messageFolder.cancel" />"
		onclick="javascript:  window.location.replace('messageFolder/administrator/list.do');" />
	<br />
	</security:authorize>
	
	<!-- Cancel para ranger -->
	<security:authorize access="hasRole('RANGER')">
	<input type="button" name="cancel"
		value="<spring:message code="messageFolder.cancel" />"
		onclick="javascript:  window.location.replace('messageFolder/ranger/list.do');" />
	<br />
	</security:authorize>
	
	<!-- Cancel para sponsor -->
	<security:authorize access="hasRole('SPONSOR')">
	<input type="button" name="cancel"
		value="<spring:message code="messageFolder.cancel" />"
		onclick="javascript:  window.location.replace('messageFolder/sponsor/list.do');" />
	<br />
	</security:authorize>
	
	<!--  Cancel para  explorer-->
	<security:authorize access="hasRole('EXPLORER')">
	<input type="button" name="cancel"
		value="<spring:message code="messageFolder.cancel" />"
		onclick="javascript:  window.location.replace('messageFolder/explorer/list.do');" />
	<br />
	</security:authorize>
	
	<!-- Cancel para auditor -->
	<security:authorize access="hasRole('AUDITOR')">
	<input type="button" name="cancel"
		value="<spring:message code="messageFolder.cancel" />"
		onclick="javascript:  window.location.replace('messageFolder/auditor/list.do');" />
	<br />
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
	<input type="button" name="cancel"
		value="<spring:message code="messageFolder.cancel" />"
		onclick="javascript:  window.location.replace('messageFolder/manager/list.do');" />
	<br />
	</security:authorize>
	
</form:form>