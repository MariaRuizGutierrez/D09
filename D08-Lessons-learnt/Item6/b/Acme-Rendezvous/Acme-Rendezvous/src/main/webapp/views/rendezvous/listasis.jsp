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


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvous" requestURI="${requestURI }" id="row">

	<!-- ENLACE EDITAR Y DISPLAY-->
	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvous.edit" var="Edit" />

		<display:column title="${Edit}" sortable="true">
			<jstl:if test="${row.draftMode==true && row.deleted==false}">
				<spring:url value="rendezvous/user/edit.do" var="editURL">
					<spring:param name="rendezvouseId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="rendezvous.edit" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<spring:message code="rendezvous.display" var="Display" />
	<display:column title="${Display}" sortable="true">
		<spring:url value="rendezvous/display.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvous.display" /></a>

	</display:column>

	<!-- ATRIBUTOS -->
	<spring:message code="rendezvouse.name" var="titleHeader" />
	<display:column property="name" title="${titleHeader}" sortable="true" />

	<spring:message code="rendezvouse.description" var="titleHeader" />
	<display:column property="description" title="${titleHeader}"
		sortable="true" />

	<spring:message code="ren.format.date" var="pattern"></spring:message>
	<spring:message code="rendezvouse.organisedMoment" var="titleHeader" />
	<display:column property="organisedMoment" title="${titleHeader}"
		sortable="true" format="${pattern}" />

	<spring:message code="rendezvouse.picture" var="titleHeader" />
	<display:column title="${titleHeader}">
		<a href="${row.picture}"><spring:message
				code="rendezvouse.picture" /></a>
	</display:column>
	<!-- ENLACES -->
	<security:authorize access="isAnonymous()">
		<spring:message code="rendezvous.announcement" var="announcements" />
		<display:column title="${announcements}" sortable="true">
			<spring:url value="announcement/listb.do" var="announcementURL">
				<spring:param name="rendezvousId" value="${row.id }" />
			</spring:url>
			<a href="${announcementURL}"><spring:message
					code="rendezvous.announcement" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">
		<spring:message code="rendezvous.announcement" var="announcements" />
		<display:column title="${announcements}" sortable="true">
			<spring:url value="announcement/user/listb.do" var="announcementURL">
				<spring:param name="rendezvousId" value="${row.id }" />
			</spring:url>
			<a href="${announcementURL}"><spring:message
					code="rendezvous.announcement" /></a>
		</display:column>
	</security:authorize>


	<jstl:if test="${assist}">
		<spring:message code="rendezvouse.question" var="Question" />
		<display:column title="${Question}" sortable="true">
			<spring:url value="question/list.do" var="editURL">
				<spring:param name="rendezvouseId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message code="rendezvouse.question" /></a>
		</display:column>

	</jstl:if>
	<spring:message code="rendezvouse.assistans" var="ASS" />
	<display:column title="${ASS}" sortable="true">
		<spring:url value="rendezvous/listAssistants.do" var="editURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="rendezvouse.assistans" /></a>
	</display:column>

	<spring:message code="user.maker" var="Maker" />
	<display:column title="${Maker}" sortable="true">
		<spring:url value="rendezvous/listMaker.do" var="renURL">
			<spring:param name="rendezvousId" value="${row.id}" />
		</spring:url>
		<a href="${renURL}"><spring:message code="user.maker" /></a>
	</display:column>




	
	
			<jstl:if test="${assist}">

			<spring:message code="rendezvouse.comments" var="Comment" />
			<display:column title="${Comment}" sortable="true">
				<spring:url value="comment/user/list.do" var="listCommentURL">
					<spring:param name="rendezvouseId" value="${row.id}" />
				</spring:url>
				<a href="${listCommentURL}"><spring:message
						code="rendezvouse.comments" /></a>
			</display:column>
			<security:authorize access="hasRole('USER')">

				<spring:message code="rendezvouse.createComment"
					var="CreateComments" />
				<display:column title="${CreateComments}" sortable="true">
					<spring:url value="comment/user/create.do" var="createCommentURL">
						<spring:param name="rendezvouseId" value="${row.id}" />
					</spring:url>
					<a href="${createCommentURL}"><spring:message
							code="rendezvouse.comment.create" /></a>
				</display:column>
			</security:authorize>
			
			</jstl:if>
			
<jstl:if test="${assist}">
<display:column>
		

			<spring:url value="rendezvous/user/notassist.do" var="editURL">
				<spring:param name="rendezvousId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message
					code="rendezvouse.assistans.notgo" /></a>

			<!-- Boton para comentar en este rendezvouse si es que es asistente -->

		
	</display:column>	
	</jstl:if>
	
	<jstl:if test="${!assist && row.deleted==false}">
		<display:column>
		
			<spring:url value="rendezvous/user/assist.do" var="editURL">
				<spring:param name="rendezvousId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message
					code="rendezvouse.assistans.go" /></a>
		

	</display:column>
</jstl:if>
	<!-- Boton de delete para el administrador ya que puede borrar las Rendezvous que quiera pero no editarlas -->
	<security:authorize access="hasRole('ADMINISTRATOR')">
	
	
		<spring:message code="rendezvous.delete" var="delete" />

		<display:column title="${delete}" sortable="true">
			<spring:url value="rendezvous/administrator/delete.do" var="editURL">
				<spring:param name="rendezvousId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message code="rendezvous.delete" /></a>
		</display:column>
	</security:authorize>

<spring:message code="draftMode" var="draftMode" />
<display:column title="${draftMode}">
	<jstl:if test="${row.draftMode==true}">
	<div
  style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">
  			
		  <img src="images/no.png"width= "30" height="30">
		  </div>
		  </jstl:if>
	<jstl:if test="${row.draftMode==false}">
	<div
  style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">
  			
		  <img src="images/yes.png"width= "30" height="30">
		  </div>
		  </jstl:if>
		
</display:column>

<security:authorize access="hasRole('USER')">
		<spring:message code="delete" var="Delete" />
		<display:column title="${Delete}">
		<jstl:if test="${row.deleted==true}">
			<FONT COLOR="grey">
			<spring:message code="delete.delete"/>
			</FONT><br>
		</jstl:if>
		</display:column>
			
	</security:authorize>

	


	<spring:message code="forAdult" var="forAdult" />
	<display:column title="${forAdult}">
	<jstl:if test="${row.forAdult==true}">
	<div
  style="position: relative; width: 30px; height: 30px; margin-left: auto; margin-right: auto;">
  			
		  <img src="images/18.png"width= "30" height="30">
		  </div>
		  </jstl:if> 
</display:column>



</display:table>



