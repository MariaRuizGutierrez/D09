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

<jstl:if test="${!existCurricula}">
<spring:message code="personalRecord.notCurricula.createPersonalRecord" var="notCurriculaHeader" />
<h2><jstl:out value="${notCurriculaHeader}"></jstl:out></h2>
</jstl:if>

<form:form action="${RequestURI }" modelAttribute="personalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="fullName">
		<spring:message code="personalRecord.fullName" />:
	</form:label>
	<form:input path="fullName"/>
	<form:errors cssClass="error" path="fullName" />
	<br />
	
	<form:label path="photo">
		<spring:message code="personalRecord.photo" />:
	</form:label>
	<form:input path="photo" placeholder="http://" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="email">
		<spring:message code="personalRecord.email" />:
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="personalRecord.phone" />:
	</form:label>
	<form:input path="phone"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="linkedProfile">
		<spring:message code="personalRecord.linkedProfile" />:
	</form:label>
	<form:input path="linkedProfile" placeholder="http://"/>
	<form:errors cssClass="error" path="linkedProfile" />
	<br />
	


	<script type="text/javascript">
		function valida(phone) {
			var m = document.getElementById("phone").value;
			var expreg = /^(\+\d{1,3})?\s?(\(\d{3}\))?\s?\d{4,100}$/;

			if (!expreg.test(m)) {
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
		value="<spring:message code="personalRecord.save" />" onclick="return valida();" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="personalRecord.cancel" />"
		onclick="javascript:  window.location.replace('welcome/index.do');" />
	<br />
</form:form>