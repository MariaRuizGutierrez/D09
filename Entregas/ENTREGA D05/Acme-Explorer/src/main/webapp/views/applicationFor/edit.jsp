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

<form:form action="${RequestURI }" modelAttribute="applicationFor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="explorer"/>
	<form:hidden path="manager"/>
	<form:hidden path="reasonWhy"/>

	<form:label path="moment">
		<spring:message code="applicationFor.moment" />:
	</form:label>
	<form:input path="moment" readonly="true"/>
	<form:errors cssClass="error" path="moment" />
	<br />
	
	<form:label path="status">
		<spring:message code="trip.status" />:
	</form:label>
	<form:select id="status" path="status">
		<form:option value="PENDING" label="PENDING" />
	</form:select>
	<br />
	
	<form:label path="trip">
		<spring:message code="applicationFor.trip" />:
	</form:label>
	<form:select id="trips" path="trip" >
		<form:option value="0" label="----" />		
		<form:options items="${trips}" itemValue="id"
			itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="trip" />
	<br />
	
	
	<fieldset>
	
	<form:label path="creditCard.holderName">
		<spring:message code="applicationfor.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="holderName" />
	<br/>
	
	<form:label path="creditCard.brandName">
		<spring:message code="applicationfor.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="brandName" />
	<br/>
	
	<form:label path="creditCard.number">
		<spring:message code="applicationfor.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="number" />
	<br/>
	
	<form:label path="creditCard.expirationMonth">
		<spring:message code="applicationfor.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="expirationMonth" />
	<br/>
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="applicationfor.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="expirationYear" />
	<br/>
	
	<form:label path="creditCard.cvv">
		<spring:message code="applicationfor.creditCard.cvv" />:
	</form:label>
	<form:input path="creditCard.cvv" />
	<form:errors cssClass="error" path="cvv" />
	<br/>
	
	</fieldset>	<br />

	<input type="submit" name="save"
		value="<spring:message code="applicationFor.save" />" />&nbsp; 
	<jstl:if test="${applicationFor.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="applicationFor.delete" />"
			onclick="javascript: return confirm('<spring:message code="applicationFor.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="applicationFor.cancel" />"
		onclick="javascript:  window.location.replace('applicationFor/explorer/list.do');" />
	<br />
</form:form>
