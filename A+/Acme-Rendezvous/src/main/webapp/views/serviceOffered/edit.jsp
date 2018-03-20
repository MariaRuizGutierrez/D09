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

<form:form action="serviceoffered/manager/edit.do" modelAttribute="serviceOffered">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="rendezvouses"/>
	
	<acme:textbox code="serviceoffered.name" path="name"/>
	<br />
	
	<acme:textarea code="serviceoffered.description" path="description"/>
	<br />
	
	<acme:textbox code="serviceoffered.picture" path="picture" placeHolder="http:\\"/>
	<br />
	
	<acme:select items="${categories}" itemLabel="name" code="serviceoffered.category" path="category"/>
	<br />
	
	
		<!-- botones -->

	<acme:submit name="save" code="serviceoffered.save"/>
		
	
<jstl:if test="${serviceOffered.id !=0 }">
			<acme:submit_with_on_click name="delete" code="rendezvous.delete" code2="serviceOffered.confirm.delete"/>
</jstl:if>

	<acme:cancel url="serviceoffered/manager/listAll.do?d-16544-p=1" code="serviceoffered.cancel"/>
	<br />
	




</form:form>