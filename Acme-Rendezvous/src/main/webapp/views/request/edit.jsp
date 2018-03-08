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

<form:form action="request/user/edit.do" modelAttribute="request">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="user" />
	<form:hidden path="rendezvousid"/>
	
<!-- ATRIBUTOS -->

	
	
	<fieldset>
	
	<form:label path="creditCard.holderName">
		<spring:message code="request.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br/>
	
	<form:label path="creditCard.brandName">
		<spring:message code="request.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br/>
	
	<form:label path="creditCard.number">
		<spring:message code="request.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" placeholder="XXXXXXXXXXXXXXXX"/>
	<form:errors cssClass="error" path="creditCard.number" />
	<br/>
	
	<form:label path="creditCard.expirationMonth">
		<spring:message code="request.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" placeholder="XX"/>
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br/>
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="request.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" placeholder="XX"/>
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br/>
	
	<form:label path="creditCard.cvv">
		<spring:message code="request.creditCard.cvv" />:
	</form:label>
	<form:input path="creditCard.cvv"/>
	<form:errors cssClass="error" path="creditCard.cvv" />
	<br/>
	
	</fieldset>
	
	
	<acme:textbox code="request.comment" path="comment"/>
	<br />
	<acme:selectone items="${serviceOffered}" itemLabel="name" code="rendezvous.serviceOffered" path="serviceOffered"/>
	<br /> 
	
	
	<input type="submit" name="save" value="<spring:message code="request.save" />" />&nbsp; 
	
	<jstl:if test="${request.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="request.delete" />"
			onclick="javascript: return confirm('<spring:message code="request.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="request.cancel" />"
		onclick="javascript:  window.location.replace('rendezvous/user/list.do');" />
	<br />
</form:form>
