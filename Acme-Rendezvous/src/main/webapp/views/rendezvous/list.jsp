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
<jsp:useBean id="util" class="utilities.Methodutilities" scope="page" />

<script type="text/javascript">
	function confirmDelete(rendezvousId) {
		confirm=confirm('<spring:message code="rendezvous.confirm.delete"/>');
		if (confirm)
		  window.location.href ="rendezvous/administrator/delete.do?rendezvousId=" + rendezvousId;
		  else
			  window.location.href ="rendezvous/administrator/list.do";
	}
</script>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvous" requestURI="${requestURI }" id="row">

<%!String estilo;%>
	<jstl:choose>
			<jstl:when test="${util.organisedMoment(row.organisedMoment)==false}">
				<%=estilo = "p2"%>

			</jstl:when>
			
			<jstl:when test="${util.organisedMoment(row.organisedMoment)==true}">
				<%=estilo = "white"%>

			</jstl:when>

		</jstl:choose>
		
		
	<!-- ENLACE EDITAR Y DISPLAY-->
	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvous.edit" var="Edit" />

		<display:column title="${Edit}" sortable="true" class="<%= estilo %>">
			<jstl:if test="${row.draftMode==true}">
				<spring:url value="rendezvous/user/edit.do" var="editURL">
					<spring:param name="rendezvouseId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="rendezvous.edit" /></a>
			</jstl:if>
		</display:column>
	

	<spring:message code="rendezvous.display" var="Display" />
	<display:column title="${Display}" sortable="true" class="<%= estilo %>">
		<spring:url value="rendezvous/user/display.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvous.display" /></a>

	</display:column>
	</security:authorize>
	
	<security:authorize access="isAnonymous()">
	<spring:message code="rendezvous.display" var="Display" />
	<display:column title="${Display}" sortable="true" class="<%= estilo %>">
		<spring:url value="rendezvous/display.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvous.display" /></a>

	</display:column>
	</security:authorize>

	<!-- ATRIBUTOS -->
	<spring:message code="rendezvouse.name" var="titleHeader" />
	<display:column property="name" title="${titleHeader}" sortable="true" class="<%= estilo %>"/>

	<spring:message code="rendezvouse.description" var="titleHeader" />
	<display:column property="description" title="${titleHeader}"
		sortable="true" class="<%= estilo %>"/>

	<spring:message code="ren.format.date" var="pattern"></spring:message>
	<spring:message code="rendezvouse.organisedMoment" var="titleHeader"/>
	<display:column property="organisedMoment" title="${titleHeader}"
		sortable="true" format="${pattern}" class="<%= estilo %>"/>

	
	<spring:message code="rendezvouse.picture" var="titleHeader"/>
	<display:column title="${titleHeader}" class="<%= estilo %>">
	<jstl:if test="${!(row.picture=='')}">
		<a href="${row.picture}"><spring:message
				code="rendezvouse.picture" /></a>
	</jstl:if>
	</display:column>
	<!-- ENLACES -->
	<security:authorize access="isAnonymous()">
		<spring:message code="rendezvous.announcement" var="announcements" />
		<display:column title="${announcements}" sortable="true" class="<%= estilo %>">
			<spring:url value="announcement/list.do" var="announcementURL">
				<spring:param name="rendezvousId" value="${row.id }" />
			</spring:url>
			<a href="${announcementURL}"><spring:message
					code="rendezvous.announcement" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<spring:message code="rendezvous.announcement" var="announcements" />
		<display:column title="${announcements}" sortable="true" class="<%= estilo %>">
			<spring:url value="announcement/list.do" var="announcementURL">
				<spring:param name="rendezvousId" value="${row.id }" />
			</spring:url>
			<a href="${announcementURL}"><spring:message
					code="rendezvous.announcement" /></a>
		</display:column>
	</security:authorize>



	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvous.announcement" var="announcements" />
		<display:column title="${announcements}" sortable="true" class="<%= estilo %>">
			<spring:url value="announcement/user/list.do" var="announcementURL">
				<spring:param name="rendezvousId" value="${row.id }" />
			</spring:url>
			<a href="${announcementURL}"><spring:message
					code="rendezvous.announcement" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvous.createAnnouncement"
			var="createAnnouncement" />
		<display:column title="${createAnnouncement}" sortable="true" class="<%= estilo %>">
		<jstl:if test="${util.organisedMoment(row.organisedMoment)==true}">
			<spring:url value="announcement/user/create.do" var="editURL">
				<spring:param name="rendezvousId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message
					code="rendezvous.createAnnouncement1" /></a>
		</jstl:if>
		</display:column>
	</security:authorize>


	<spring:message code="rendezvouse.question" var="Question" />
	<display:column title="${Question}" sortable="true" class="<%= estilo %>">
		<spring:url value="question/list.do" var="editURL">
			<spring:param name="rendezvouseId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvouse.question" /></a>
	</display:column>


	<spring:message code="rendezvouse.assistans" var="ASS" />
	<display:column title="${ASS}" sortable="true" class="<%= estilo %>">
		<spring:url value="rendezvous/listAssistants.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvouse.assistans" /></a>
	</display:column>

	<spring:message code="user.maker" var="Maker" />
	<display:column title="${Maker}" sortable="true" class="<%= estilo %>">
		<spring:url value="rendezvous/listMaker.do" var="renURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${renURL}"><spring:message code="user.maker" /></a>
	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<spring:message code="rendezvouse.comments" var="Comment" />
		<display:column title="${Comment}" sortable="true" class="<%= estilo %>">
			<spring:url value="comment/administrator/listByRendezvouse.do"
				var="listCommentURL">
				<spring:param name="rendezvouseId" value="${row.id}" />
			</spring:url>
			<a href="${listCommentURL}"><spring:message
					code="rendezvouse.comments" /></a>
		</display:column>

		<!-- Boton de delete para el administrador ya que puede borrar las Rendezvous que quiera pero no editarlas -->

		<spring:message code="rendezvous.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}" sortable="true" class="<%= estilo %>">
			<input type="button" name="delete"
				value="<spring:message code="rendezvous.delete" />"
				onclick="confirmDelete(${row.id});" />
		</display:column>
	</security:authorize>

	<security:authorize access="isAnonymous()">
		<!-- Similar rendezvoses -->
		<spring:message code="rendezvouse.similarRendezvouses"
			var="similarRendezvouses" />
		<display:column title="${similarRendezvouses}" sortable="true" class="<%= estilo %>">
			<spring:url value="rendezvous/listSimilar.do" var="listSimilarURL">
				<spring:param name="rendezvousId" value="${row.id}" />
			</spring:url>
			<a href="${listSimilarURL}"><spring:message
					code="rendezvouse.rendezvouses" /></a>
		</display:column>
	</security:authorize>

	<spring:message code="draftMode" var="draftMode" />
	<display:column title="${draftMode}" class="<%= estilo %>">
		<jstl:if test="${row.draftMode==true}">
			<div
				style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

				<img src="images/no.png" width="30" height="30">
			</div>
		</jstl:if>
		<jstl:if test="${row.draftMode==false}">
			<div
				style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

				<img src="images/yes.png" width="30" height="30">
			</div>
		</jstl:if>
		
</display:column>
	

	<security:authorize access="hasRole('USER')">
		<spring:message code="forAdult" var="forAdult" />
		<display:column title="${forAdult}" class="<%= estilo %>">
			<jstl:if test="${row.forAdult==true}">
				<div
					style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

					<img src="images/18.png" width="30" height="30">
				</div>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<spring:message code="forAdult" var="forAdult" />
		<display:column title="${forAdult}" class="<%= estilo %>">
			<jstl:if test="${row.forAdult==true}">
				<div
					style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">

					<img src="images/18.png" width="30" height="30">
				</div>
			</jstl:if>
		</display:column>
	</security:authorize>


	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvouse.linkSimilar" var="linkSimilar" />
		<display:column title="${linkSimilar}" sortable="true" class="<%= estilo %>">
		<jstl:if test="${util.organisedMoment(row.organisedMoment)==true}">
			<spring:url value="rendezvous/user/editNotSimilar.do"
				var="editSimilarURL">
				<spring:param name="rendezvouseId" value="${row.id}" />
			</spring:url>
			<a href="${editSimilarURL}"><spring:message
					code="rendezvouse.link" /></a>
		</jstl:if>
		</display:column>
		
		<spring:message code="rendezvouse.unlinkSimilar" var="unlinkSimilar" />
		<display:column title="${unlinkSimilar}" sortable="true" class="<%= estilo %>">
		<jstl:if test="${util.organisedMoment(row.organisedMoment)==true}">
			<spring:url value="rendezvous/user/editSimilar.do"
				var="editSimilarURL">
				<spring:param name="rendezvouseId" value="${row.id}" />
			</spring:url>
			<a href="${editSimilarURL}"><spring:message
					code="rendezvouse.unlink" /></a>
		</jstl:if>
		</display:column>
		
		
		
		<spring:message code="rendezvouse.service.request" var="service" />
		
		<display:column title="${service}" sortable="true" class="<%= estilo %>">
		<jstl:if test="${row.draftMode==true and util.organisedMoment(row.organisedMoment)==true}">
			<spring:url value="request/user/edit.do"
				var="request">
				<spring:param name="rendezvouseId" value="${row.id}" />
			</spring:url>
						<a href="${request}"><spring:message
					code="rendezvouse.request" /></a>
						</jstl:if>
		</display:column>
	
	</security:authorize>
	
	



</display:table>


<security:authorize access="hasRole('USER')">
	<div>
		<a href="rendezvous/user/create.do"> <spring:message
				code="rendezvous.create" />
		</a>
	</div>
</security:authorize>

