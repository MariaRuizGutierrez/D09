
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI }" modelAttribute="msg">


	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender"/>
	<form:hidden path="recipient"/>


	<form:label path="moment">
		<spring:message code="message.moment" />:
	</form:label>
	<form:input path="moment" readonly="true" />
	<form:errors cssClass="error" path="moment" />
	<br />

	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" readonly="true"/>
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:input path="body" readonly="true" />
	<form:errors cssClass="error" path="body" />
	<br />

	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<form:input path="priority" readonly="true" />
	<form:errors cssClass="error" path="priority" />
	<br />


	<form:label path="messageFolder">
		<spring:message code="message.messageFolder" />:
	</form:label>
	<form:select id="folders" path="messageFolder">
		<form:options items="${folders}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="messageFolder" />
	<br />

	

	<!-- Boton save y deletes -->
	<input type="submit" name="save"
		value="<spring:message code="message.save" />" />&nbsp; 


	<!--  Cancel para administrator -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript:  window.location.replace('messageFolder/administrator/list.do');" />
		<br />
	</security:authorize>

	<!-- Cancel para ranger -->
	<security:authorize access="hasRole('RANGER')">
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript:  window.location.replace('messageFolder/administrator/list.do');" />
		<br />
	</security:authorize>

	<!-- Cancel para sponsor -->
	<security:authorize access="hasRole('SPONSOR')">
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript:  window.location.replace('messageFolder/sponsor/list.do');" />
		<br />
	</security:authorize>

	<!--  Cancel para  explorer-->
	<security:authorize access="hasRole('EXPLORER')">
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript:  window.location.replace('messageFolder/explorer/list.do');" />
		<br />
	</security:authorize>

	<!-- Cancel para auditor -->
	<security:authorize access="hasRole('AUDITOR')">
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript:  window.location.replace('messageFolder/auditor/list.do');" />
		<br />
	</security:authorize>

	<!-- Cancel para manager -->
	<security:authorize access="hasRole('MANAGER')">
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript:  window.location.replace('messageFolder/manager/list.do');" />
		<br />
	</security:authorize>

</form:form>