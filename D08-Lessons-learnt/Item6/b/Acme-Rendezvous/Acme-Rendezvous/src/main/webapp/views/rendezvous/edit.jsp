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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="rendezvous/user/edit.do"
	modelAttribute="rendezvouse">
	
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="rendezvouse.name" path="name"/>
	<br />
	<acme:textbox code="rendezvouse.description" path="description"/>
	<br />
	<acme:textbox code="rendezvouse.picture" path="picture" placeHolder="http:\\\\"/>
	<br />
	<acme:textbox code="rendezvouse.organisedMoment" path="organisedMoment" placeHolder="yyyy/MM/dd"/>
	<br />

	<acme:textbox code="rendezvouse.location.longitude" path="gps.longitude"/>
	<br />
	<acme:textbox code="rendezvouse.location.latitude" path="gps.latitude"/>
	<br />

	<acme:booleanselect code="rendezvouse.draftMode" path="draftMode"/>
	
	<br/>
	<acme:booleanselect code="rendezvouse.forAdult" path="forAdult"/>
	<br/>
	<acme:select items="${similarRendezvouses}" itemLabel="name" code="rendezvous.similarRendezvouses" path="similarRendezvouses"/>
	<br />
	

	

	<!-- botones -->

	<acme:submit name="save" code="rendezvous.save"/>
		
	<jstl:if test="${rendezvouse.id !=0 }">
			<acme:submit name="deletevirtual" code="rendezvous.delete"/>
	</jstl:if>

	<acme:cancel url="rendezvous/user/list.do" code="rendezvous.cancel"/>
	<br />


	
</form:form>


