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




<form:form action="administrator/administrator/edit.do" modelAttribute="administrator">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="socialIdentities"/>
	<form:hidden path="messagesFolders"/>
	<form:hidden path="userAccount"/>

	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	
	<form:label path="name">
		<spring:message code="administrator.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="surname">
		<spring:message code="administrator.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="email">
		<spring:message code="administrator.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="address">
		<spring:message code="administrator.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="phone">
		<spring:message code="administrator.phone" />:
	</form:label>

	
	<form:errors cssClass="error" path="phone" />
	<br />
	
	
	
	<script type="text/javascript">
		function valida(phone) {
			var m = document.getElementById("phone").value;
			var expreg = /^(\+\d{1,3})?\s?(\(\d{3}\))?\s?\d{4,100}$/;
			
			if(!expreg.test(m)){
				alert("Are you sure you want to save this phone?");
			}
				
		}

	</script>
	
	<input type="submit" name="save"
		value="<spring:message code="administrator.save" />" onclick="valida();"/>&nbsp; 
	<jstl:if test="${administrator.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="administrator.delete" />"
			onclick="javascript: return confirm('<spring:message code="administrator.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="administrator.cancel" />"
		onclick="javascript: window.location.replace('welcome/index.do');" />
	<br />
	
	</security:authorize>
	
</form:form>