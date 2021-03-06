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



<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sponsor"/> 
	<form:hidden path="trip"/>
	
	
	
	
	<form:label path="bannerURL">
		<spring:message code="sponsorship.bannerURL" />:
	</form:label>
	<form:input path="bannerURL" placeholder="http://"/>
	<form:errors cssClass="error" path="bannerURL" />
	<br />
	
	<br/> 
	
	<form:label path="link">
		<spring:message code="sponsorship.link" />:
	</form:label>
	<form:input path="link" placeholder="http://"/>
	<form:errors cssClass="error" path="link" />
	<br />
	<br/> 
	<form:label path="creditCard">
		<spring:message code="sponsorship.creditCard" />:
	</form:label>
	<br />
	
	<br/> 
	<fieldset>
	
	<form:label path="creditCard.holderName">
		<spring:message code="sponsorship.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br/>
	
	<form:label path="creditCard.brandName">
		<spring:message code="sponsorship.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br/>
	
	<form:label path="creditCard.number">
		<spring:message code="sponsorship.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" placeholder="XXXXXXXXXXXXXXXX"/>
	<form:errors cssClass="error" path="creditCard.number" />
	<br/>
	
	<form:label path="creditCard.expirationMonth">
		<spring:message code="sponsorship.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" placeholder="XX"/>
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br/>
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="sponsorship.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" placeholder="XX"/>
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br/>
	
	<form:label path="creditCard.cvv">
		<spring:message code="sponsorship.creditCard.cvv" />:
	</form:label>
	<form:input path="creditCard.cvv"/>
	<form:errors cssClass="error" path="creditCard.cvv" />
	<br/>
	
	</fieldset>
	
	<br/>
	<!-- botones --> 
	
	<input type="submit" name="save"
		value="<spring:message code="sponsorship.save"/>" />&nbsp;
		
	<jstl:if test="${sponsorship.id !=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="sponsorship.delete"/>"
			onclick="javascript: return confirm('<spring:message code="sponsorship.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="sponsorship.cancel"/>"
		onclick="javascript: window.location.replace('trip/sponsor/list.do')" />
	<br />
	
</form:form>