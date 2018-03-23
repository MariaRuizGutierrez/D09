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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<script type="text/javascript">
	function confirmDelete(announcementId) {
		confirm=confirm('<spring:message code="announcement.confirm.delete"/>');
		if (confirm)
		  window.location.href ="announcement/administrator/delete.do?announcementId=" + announcementId;
		  else
			  window.location.href ="announcement/administrator/list.do";
	}
</script>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="announcements" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<spring:message code="announcement.format.madeMoment" var="pattern"></spring:message>
	<spring:message code="announcement.madeMoment" var="momentHeader2" />
	<display:column property="madeMoment" title="${momentHeader2}" sortable="true" format="${pattern}" />
	
	<acme:column code="announcement.title" property="title"/>
	
	<acme:column code="announcement.description" property="description"/>
	
	
	<!-- Boton de delete para el administrador ya que puede borrar las Announcement que quiera pero no editarlas -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<spring:message code="announcement.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}" sortable="true">
			<input type="button" name="delete"
				value="<spring:message code="announcement.delete" />"
				onclick="confirmDelete(${row.id});" />
		</display:column>
	</security:authorize>
		
</display:table>






<security:authorize access="isAnonymous()">
<br />
<input type="button" name="back"
		value="<spring:message code="rendezvous.back" />"
		onclick="javascript: window.location.replace('rendezvous/list-unregister.do');" />
</security:authorize>
