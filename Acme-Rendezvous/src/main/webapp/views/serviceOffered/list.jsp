<%--
 * list.jsp
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

<script type="text/javascript">
	function confirmDelete(serviceOfferedId) {
		confirm=confirm('<spring:message code="serviceOffered.confirm.delete"/>');
		if (confirm)
		  window.location.href ="serviceoffered/administrator/cancel.do?serviceOfferedId=" + serviceOfferedId;
		  else
			  window.location.href ="serviceoffered/administrator/list.do";
	}
</script>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="serviceoffered" requestURI="${requestURI }" id="row">

<acme:column code="serviceoffered.name" property="name"/>
<acme:column code="serviceoffered.description" property="description"/>
<acme:column code="serviceoffered.picture" property="picture"/>

<!-- Boton de delete para el administrador ya que puede borrar los anuncios que quiera pero no editarlos -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<spring:message code="serviceOffered.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}" sortable="true">
			<input type="button" name="delete"
				value="<spring:message code="serviceOffered.delete" />"
				onclick="confirmDelete(${row.id});" />
		</display:column>
	</security:authorize>
</display:table>


