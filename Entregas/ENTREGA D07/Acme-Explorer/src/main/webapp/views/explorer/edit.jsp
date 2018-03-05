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

<form:form action="${RequestURI}" modelAttribute="explorer">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="socialIdentities"/>
	<form:hidden path="messagesFolders"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="userAccount.authorities"/>
	<form:hidden path="userAccount.activated"/>
	<form:hidden path="stories"/>
	<form:hidden path="applicationsFor"/>
	<form:hidden path="contactsEmergency"/>
	<form:hidden path="suspicious"/>
	
	<jstl:if test="${explorer.id == 0}">		
		<form:label path="userAccount.username">
			<spring:message code="explorer.username" />:
		</form:label>
		<form:input path="userAccount.username" />
		<form:errors cssClass="error" path="userAccount.username" />
		<br /><br />
	
		<form:label path="userAccount.password">
			<spring:message code="explorer.password" />:
		</form:label>
		<form:password path="userAccount.password" />
		<form:errors cssClass="error" path="userAccount.password" />
		<br /><br />
	</jstl:if>
	
	<form:label path="name">
		<spring:message code="explorer.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<br />
	
	<form:label path="surname">
		<spring:message code="explorer.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	<br />
	
	<form:label path="email">
		<spring:message code="explorer.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	<br />
	
	<form:label path="address">
		<spring:message code="explorer.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<br />
	
	<form:label path="phone">
		<spring:message code="explorer.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	<br />
	
		<script type="text/javascript">
		function valida(phone) {
			var m = document.getElementById("phone").value;
			var expreg = /^(\+\d{1,3})?\s?(\(\d{3}\))?\s?\d{4,100}$/;
		
			if (!m == ''){
				if(!expreg.test(m)) {
					var confirmar = confirm("Are you sure you want to save this phone?");
					if (confirmar ) {
						return true;
					} else {
						return false;
					}
				}
			}
				

		}
	</script>
	<br />
	
	
	<input type="submit" name="save"
		value="<spring:message code="explorer.save" />" onclick="return valida();"/>&nbsp;
	
	<input type="button" name="cancel"
		value="<spring:message code="explorer.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do');" />
	<br />
	
</form:form>