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


<form:form action="${RequestURI}" modelAttribute="value">

	<form:hidden path="id" />
	<form:hidden path="version" /> 


<p><spring:message code="value.trip" />: <jstl:out value="${tripTtitle}"></jstl:out></p>
<p><spring:message code="value.tag" />: <jstl:out value="${tagName}"></jstl:out></p>

	
	<br /> 
	
	<h2><spring:message code="valu.value.name" /></h2>
	<form:label path="value">
		<spring:message code="value.select" />:
	</form:label>
	<form:select id="value" path="value" >		
		    <option value="1">1</option>
 			<option value="2">2</option>
 			<option value="3">3</option>
  			<option value="4">4</option>
  			<option value="5">5</option>
  			<option value="6">6</option>
  			<option value="7">7</option>
  			<option value="8">8</option>
  			<option value="9">9</option>
  			<option value="10">10</option>
	</form:select>	
	<form:errors cssClass="error" path="value" />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	
	
 	<%-- <security:authorize access="hasRole('MANAGER')">
	<h2><spring:message code="tag.value" /></h2>
	<form:label path="values">
		<spring:message code="tag.value" />:
	</form:label>
	<form:select id="values" path="values" multiple="multiple" >		
		<form:options items="${values}" itemValue="id" itemLabel="value" />		
	</form:select>
	<form:errors cssClass="error" path="values" />
	<br /> 
	<br />
	</security:authorize>  --%>
		
	<!-- botones --> 
	
	<input type="submit" name="save"
		value="<spring:message code="value.save"/>" />&nbsp;
		
<%-- 	<jstl:if test="${tag.id !=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="tag.delete"/>"
			onclick="javascript: return confirm('<spring:message code="tag.confirm.delete"/>')" />&nbsp;
	</jstl:if>
		 --%>
	
	<input type="button" name="cancel"
		value="<spring:message code="value.cancel"/>"
		onclick="javascript: window.location.replace('trip/manager_/list.do')" />
	<br />
		
</form:form>

