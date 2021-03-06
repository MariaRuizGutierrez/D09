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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
	function confirmDelete(serviceOfferedId) {
		confirm=confirm('<spring:message code="serviceOffered.confirm.cancel"/>');
		if (confirm)
		  window.location.href ="serviceoffered/administrator/cancel.do?serviceOfferedId=" + serviceOfferedId;
		  else
			  window.location.href ="serviceoffered/administrator/list.do";
	}
</script>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="serviceoffered" requestURI="${requestURI }" id="row">
	
	<%!String estilo;%>
	<jstl:choose>
			<jstl:when test="${row.cancelled==false}">
				<%=estilo = "p1"%>

			</jstl:when>

			<jstl:when test="${row.cancelled==true}">

				<%=estilo = "p2"%>
			</jstl:when>
		</jstl:choose>
	
		
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="serviceOffered.edit" var="Edit" />
		
		<display:column title="${Edit}" sortable="true" class="<%= estilo %>">
		
		
		<jstl:if test="${fn:contains(managerPrincipal.servicesOffered, row)}">
		<jstl:if test="${row.rendezvouses.size()==0}">
		<jstl:if test="${row.cancelled==false}">
			<spring:url value="serviceoffered/manager/edit.do" var="editURL">
				<spring:param name="serviceOfferedId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message code="serviceOffered.edit" /></a>
		
		</jstl:if>
		</jstl:if>
		</jstl:if>
		</display:column>
	</security:authorize>
	
		
	<spring:message code="serviceOffered.display" var="Display" />
	<display:column title="${Display}" sortable="true" class="<%= estilo %>">
		<spring:url value="serviceOffered/display.do" var="displayURL">
			<spring:param name="serviceOfferedId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message code="serviceOffered.display" /></a>

	</display:column>
		
	<spring:message code="serviceoffered.name" var="titleHeader1" />
	<display:column property="name" title="${titleHeader1}" sortable="true" class="<%= estilo %>"/>
	
	<spring:message code="serviceoffered.description" var="titleHeader2" />
	<display:column property="description" title="${titleHeader2}" sortable="true" class="<%= estilo %>"/>
	
	<spring:message code="serviceoffered.picture" var="titleHeader" />
	<display:column title="${titleHeader}" class="<%= estilo %>">
	<jstl:if test="${!(row.picture=='')}">
		<a href="${row.picture}"><spring:message
				code="serviceoffered.picture" /></a>
	</jstl:if>
	</display:column>

	
	<spring:message code="serviceoffered.category" var="titleHeader3" />
	<display:column property="category.name" title="${titleHeader3}" sortable="true" class="<%= estilo %>"/>
	
	
	


<%-- <acme:column code="serviceoffered.name" property="name" />
<acme:column code="serviceoffered.description" property="description"/>
<acme:column code="serviceoffered.picture" property="picture"/> --%>

<security:authorize access="hasRole('ADMINISTRATOR')">
<spring:message code="serviceOffered.cancelled" var="draftMode" />

<!-- Boton de delete para el administrador ya que puede borrar los anuncios que quiera pero no editarlos -->
	
	<spring:message code="serviceOffered.delete" var="deleteHeader" />
	<jstl:if test="${row.cancelled==false}">
		<display:column title="${deleteHeader}" sortable="true" class="<%= estilo %>">
		
		<div 
		style="margin-top:8px; ">
			<input type="button" name="delete"
				value="<spring:message code="serviceOffered.delete" />"
				onclick="confirmDelete(${row.id});" />
				</div>
		
		</display:column>
		</jstl:if>
		
	</security:authorize>
	
	<spring:message code="serviceoffered.cancelled" var="titleHeader3" />
	<spring:message code="serviceoffered.cancelled.message" var="messageCancelled"></spring:message>
	<jstl:if test="${row.cancelled ==true}"> 
	<display:column value="${messageCancelled}" title="${titleHeader3}" sortable="true" class="<%= estilo %>"/>
	</jstl:if>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="serviceoffered/manager/create.do"> <spring:message
				code="serviceOffered.create" />
		</a>
	</div>
</security:authorize>


