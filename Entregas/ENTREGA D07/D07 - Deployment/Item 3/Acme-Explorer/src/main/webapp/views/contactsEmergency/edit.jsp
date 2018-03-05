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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<form:form
	action="contactsEmergency/explorer/edit.do?ContactEmergencyid"
	modelAttribute="contactEmergency">
	
	
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="name">
		<spring:message code="contactsEmergency.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="email">
		<spring:message code="contactsEmergency.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="contactsEmergency.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<br/>

	<div>

		<script type="text/javascript">
		function valida(phone) {
			var m = document.getElementById("phone").value;
			var email = document.getElementById("email").value;
			var aux = m.length;
			var expreg = /^(\+\d{1,3})?\s?(\(\d{3}\))?\s?\d{4,100}$/;
			if((email=="" && aux>0 && !expreg.test(m))|| (email!="" && aux<4 && aux>0 && !expreg.test(m)) ){
	
				var confirmar = confirm("Are you sure you want to save this phone?");
				if (confirmar) {
					return true;
				} else {
					return false;
				}
			}
		}
	</script>
		<input type="submit" name="save"
			value="<spring:message code="contactsEmergency.save" />" onclick="return valida();"/>&nbsp; 
		<jstl:if test="${contactEmergency.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="contactsEmergency.delete" />" />&nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="explorer.cancel" />"
			onclick="javascript: window.location.replace('contactsEmergency/explorer/list.do');" />

	</div>


</form:form>