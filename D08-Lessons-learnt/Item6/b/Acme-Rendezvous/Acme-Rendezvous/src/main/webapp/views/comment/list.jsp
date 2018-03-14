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

<script type="text/javascript">
	function confirmDelete(commentId) {
		confirm=confirm('<spring:message code="comment.confirm.delete"/>');
		if (confirm)
		  window.location.href ="comment/administrator/delete.do?commentId=" + commentId;
		  else
			  window.location.href ="comment/administrator/list.do";
	}
</script>




<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comments" requestURI="${requestURI}" id="row">

<security:authorize access="hasRole('USER')">
	<spring:message code="comment.display" var="Display" />
	<display:column title="${Display}" sortable="true">
		<spring:url value="comment/user/display.do" var="displayURL">
			<spring:param name="commentId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message code="comment.display" /></a>

	</display:column>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<spring:message code="comment.display" var="Display" />
	<display:column title="${Display}" sortable="true">
		<spring:url value="comment/administrator/display.do" var="displayURL">
			<spring:param name="commentId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message code="comment.display" /></a>

	</display:column>
</security:authorize>
	<!-- ATRIBUTOS -->

	<spring:message code="comment.format.writtenMoment" var="pattern"></spring:message>
	<spring:message code="comment.writtenMoment" var="momentHeader" />
	<display:column property="writtenMoment" title="${momentHeader}"
		sortable="true" format="${pattern}" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />

	<!-- Boton para responder a un comentario -->
	<security:authorize access="hasRole('USER')">
		<spring:message code="createComments" var="CreateComments" />
		<display:column title="${CreateComments}" sortable="true">
			<spring:url value="comment/user/createReply.do"
				var="createReplyCommentURL">
				<spring:param name="commentId" value="${row.id}" />
			</spring:url>
			<a href="${createReplyCommentURL}"><spring:message
					code="comment.reply1" /></a>
		</display:column>


		<jstl:if test="${ not empty row.replys}">
			<spring:message code="rendezvouse.comments" var="Comment" />
			<display:column title="${Comment}" sortable="true">
				<spring:url value="comment/user/listReplys.do" var="listReplysURL">
					<spring:param name="commentId" value="${row.id}" />
				</spring:url>
				<a href="${listReplysURL}"><spring:message
						code="comment.comments" /></a>
			</display:column>


		</jstl:if>
	</security:authorize>


	<spring:message code="comment.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}">
		<div
			style="position: relative; width: 200px; height: 100px; margin-left: auto; margin-right: auto;">
			<img src="${row.picture}" width="200" height="100">
		</div>
	</display:column>

	<security:authorize access="hasRole('USER')">
		<spring:message code="comment.user" var="userHeader" />
		<display:column title="${userHeader}" sortable="true">
			<spring:url value="user/display.do" var="userURL">
				<spring:param name="userId" value="${row.user.id }" />
			</spring:url>
			<a href="${userURL}"><jstl:out value="${row.user.name }" /></a>
		</display:column>
	</security:authorize>

	<!-- Boton de eliminar el comentario para el administrador -->

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<spring:message code="comment.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}" sortable="true">
			<input type="button" name="delete"
				value="<spring:message code="comment.delete" />"
				onclick="confirmDelete(${row.id});" />
		</display:column>
	</security:authorize>

</display:table>



