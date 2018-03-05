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

<!-- Listing messageFodler -->
<display:table name="messageFolders" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<!-- Botones para sponsor -->
	<security:authorize access="hasRole('SPONSOR')">
		<spring:message code="messageFolder.display" var="displayHeader" />
		<display:column title="${displayHeader}" sortable="true">

			<spring:url value="messageFolder/sponsor/display.do"
				var="displaySponsorURL">
				<spring:param name="messageFolderId" value="${row.id}" />
			</spring:url>

			<a href="${displaySponsorURL}"><spring:message
					code="messageFolder.display" /></a>
		</display:column>

		<spring:message code="messageFolder.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row.modifiable==true }">


				<spring:url value="messageFolder/sponsor/edit.do"
					var="editSponsorURL">
					<spring:param name="messageFolderId" value="${row.id }"></spring:param>
				</spring:url>
				<a href="${editSponsorURL }"><spring:message
						code="messageFolder.edit" /></a>
			</jstl:if>
		</display:column>


		<spring:message code="messageFolder.displayMessages" var="displayMessagesHeader" />
		<display:column title="${displayMessagesHeader}" sortable="true">
			<spring:url value="message/sponsor/list.do"
				var="listMessagesSponsorURL">
				<spring:param name="messageFolderId" value="${row.id }"></spring:param>
			</spring:url>
			<a href="${listMessagesSponsorURL }"><spring:message
					code="messageFolder.displayMessages" /></a>
		</display:column>

	</security:authorize>

	<!-- Botones para administrator -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<spring:message code="messageFolder.display" var="displayHeader" />
		<display:column title="${displayHeader}" sortable="true">

			<spring:url value="messageFolder/administrator/display.do"
				var="displayAdministratorURL">
				<spring:param name="messageFolderId" value="${row.id}" />
			</spring:url>

			<a href="${displayAdministratorURL}"><spring:message
					code="messageFolder.display" /></a>
		</display:column>


		<spring:message code="messageFolder.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row.modifiable==true }">


				<spring:url value="messageFolder/administrator/edit.do"
					var="editAdministratorURL">
					<spring:param name="messageFolderId" value="${row.id }"></spring:param>
				</spring:url>
				<a href="${editAdministratorURL }"><spring:message
						code="messageFolder.edit" /></a>
			</jstl:if>

		</display:column>



	<spring:message code="messageFolder.displayMessages" var="displayMessagesHeader" />
		<display:column title="${displayMessagesHeader}" sortable="true">
			<spring:url value="message/administrator/list.do"
				var="listMessagesAdministratorURL">
				<spring:param name="messageFolderId" value="${row.id }"></spring:param>
			</spring:url>
			<a href="${listMessagesAdministratorURL }"><spring:message
					code="messageFolder.displayMessages" /></a>
		</display:column>
	</security:authorize>


	<!-- Botones para ranger -->
	<security:authorize access="hasRole('RANGER')">
		<spring:message code="messageFolder.display" var="displayHeader" />
		<display:column title="${displayHeader}" sortable="true">
			<spring:url value="messageFolder/ranger/display.do"
				var="displayRangerURL">
				<spring:param name="messageFolderId" value="${row.id}" />
			</spring:url>

			<a href="${displayRangerURL}"><spring:message
					code="messageFolder.display" /></a>
		</display:column>


		<spring:message code="messageFolder.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row.modifiable==true }">


				<spring:url value="messageFolder/ranger/edit.do" var="editRangerURL">
					<spring:param name="messageFolderId" value="${row.id }"></spring:param>
				</spring:url>
				<a href="${editRangerURL }"><spring:message
						code="messageFolder.edit" /></a>
			</jstl:if>
		</display:column>


		<spring:message code="messageFolder.displayMessages" var="displayMessagesHeader" />
		<display:column title="${displayMessagesHeader}" sortable="true">
			<spring:url value="message/ranger/list.do"
				var="listMessagesRangerURL">
				<spring:param name="messageFolderId" value="${row.id }"></spring:param>
			</spring:url>
			<a href="${listMessagesRangerURL }"><spring:message
					code="messageFolder.displayMessages" /></a>
		</display:column>

	</security:authorize>


	<!-- Botones para explorer -->
	<security:authorize access="hasRole('EXPLORER')">
		<spring:message code="messageFolder.display" var="displayHeader" />
		<display:column title="${displayHeader}" sortable="true">
			<spring:url value="messageFolder/explorer/display.do"
				var="displayExplorerURL">
				<spring:param name="messageFolderId" value="${row.id}" />
			</spring:url>

			<a href="${displayExplorerURL}"><spring:message
					code="messageFolder.display" /></a>
		</display:column>

		<spring:message code="messageFolder.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row.modifiable==true }">


				<spring:url value="messageFolder/explorer/edit.do"
					var="editExplorerURL">
					<spring:param name="messageFolderId" value="${row.id }"></spring:param>
				</spring:url>
				<a href="${editExplorerURL }"><spring:message
						code="messageFolder.edit" /></a>
			</jstl:if>
		</display:column>


		<spring:message code="messageFolder.displayMessages" var="displayMessagesHeader" />
		<display:column title="${displayMessagesHeader}" sortable="true">
			<spring:url value="message/explorer/list.do"
				var="listMessagesExplorerURL">
				<spring:param name="messageFolderId" value="${row.id }"></spring:param>
			</spring:url>
			<a href="${listMessagesExplorerURL }"><spring:message
					code="messageFolder.displayMessages" /></a>
		</display:column>

	</security:authorize>

	<!-- Botones para manager -->
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="messageFolder.display" var="displayHeader" />
		<display:column title="${displayHeader}" sortable="true">
			<spring:url value="messageFolder/manager/display.do"
				var="displayManagerURL">
				<spring:param name="messageFolderId" value="${row.id}" />
			</spring:url>

			<a href="${displayManagerURL}"><spring:message
					code="messageFolder.display" /></a>
		</display:column>

		<spring:message code="messageFolder.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row.modifiable==true }">


				<spring:url value="messageFolder/manager/edit.do"
					var="editManagerURL">
					<spring:param name="messageFolderId" value="${row.id }"></spring:param>
				</spring:url>
				<a href="${editManagerURL }"><spring:message
						code="messageFolder.edit" /></a>
			</jstl:if>
		</display:column>


		<spring:message code="messageFolder.displayMessages" var="displayMessagesHeader" />
		<display:column title="${displayMessagesHeader}" sortable="true">
			<spring:url value="message/manager/list.do"
				var="listMessagesManagerURL">
				<spring:param name="messageFolderId" value="${row.id }"></spring:param>
			</spring:url>
			<a href="${listMessagesManagerURL }"><spring:message
					code="messageFolder.displayMessages" /></a>
		</display:column>

	</security:authorize>

	<!-- Botones para auditor -->
	<security:authorize access="hasRole('AUDITOR')">
		<spring:message code="messageFolder.display" var="displayHeader" />
		<display:column title="${displayHeader}" sortable="true">
			<spring:url value="messageFolder/auditor/display.do"
				var="displayAuditorURL">
				<spring:param name="messageFolderId" value="${row.id}" />
			</spring:url>

			<a href="${displayAuditorURL}"><spring:message
					code="messageFolder.display" /></a>
		</display:column>

		<spring:message code="messageFolder.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row.modifiable==true }">


				<spring:url value="messageFolder/auditor/edit.do"
					var="editAuditorURL">
					<spring:param name="messageFolderId" value="${row.id }"></spring:param>
				</spring:url>
				<a href="${editAuditorURL }"><spring:message
						code="messageFolder.edit" /></a>
			</jstl:if>
		</display:column>


		<spring:message code="messageFolder.displayMessages" var="displayMessagesHeader" />
		<display:column title="${displayMessagesHeader}" sortable="true">
			<spring:url value="message/auditor/list.do"
				var="listMessagesAuditorURL">
				<spring:param name="messageFolderId" value="${row.id }"></spring:param>
			</spring:url>
			<a href="${listMessagesAuditorURL }"><spring:message
					code="messageFolder.displayMessages" /></a>
		</display:column>

	</security:authorize>



	<!-- Attributes -->
	<spring:message code="messageFolder.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />


</display:table>

<security:authorize access="hasRole('SPONSOR')">
	<div>
		<a href="messageFolder/sponsor/create.do"> <spring:message
				code="messageFolder.create" />
		</a>
	</div>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<a href="messageFolder/administrator/create.do"> <spring:message
				code="messageFolder.create" />
		</a>
	</div>

</security:authorize>

<security:authorize access="hasRole('RANGER')">
	<div>
		<a href="messageFolder/ranger/create.do"> <spring:message
				code="messageFolder.create" />
		</a>

	</div>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<div>
		<a href="messageFolder/explorer/create.do"> <spring:message
				code="messageFolder.create" />
		</a>
	</div>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="messageFolder/manager/create.do"> <spring:message
				code="messageFolder.create" />
		</a>
	</div>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<div>
		<a href="messageFolder/auditor/create.do"> <spring:message
				code="messageFolder.create" />
		</a>
	</div>
</security:authorize>

