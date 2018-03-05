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

<form:form action="${RequestURI}" modelAttribute="auditor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="socialIdentities"/>
	<form:hidden path="messagesFolders"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="userAccount.authorities"/>
	<form:hidden path="userAccount.activated"/>
	<form:hidden path="notes"/>
	<form:hidden path="suspicious"/>
	<form:hidden path="auditRecords"/>
	
	<jstl:if test="${auditor.id == 0}">		
		<form:label path="userAccount.username">
			<spring:message code="auditor.username" />:
		</form:label>
		<form:input path="userAccount.username" />
		<form:errors cssClass="error" path="userAccount.username" />
		<br /><br />
	
		<form:label path="userAccount.password">
			<spring:message code="auditor.password" />:
		</form:label>
		<form:password path="userAccount.password" />
		<form:errors cssClass="error" path="userAccount.password" />
		<br /><br />
	</jstl:if>
	
	
	<form:label path="name">
		<spring:message code="auditor.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="surname">
		<spring:message code="auditor.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="email">
		<spring:message code="auditor.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="address">
		<spring:message code="auditor.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="phone">
		<spring:message code="auditor.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
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
	<input type="submit" name="save"
		value="<spring:message code="auditor.save" /> " onclick="return valida();"/>&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="auditor.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do');" />
	<br />
	
</form:form>