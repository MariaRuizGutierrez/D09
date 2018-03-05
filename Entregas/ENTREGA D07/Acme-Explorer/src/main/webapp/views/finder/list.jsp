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

<!-- Listing trips -->

<form:form action="${requestURISearch}" modelAttribute="finder" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trips" />

	<form:label path="keyWord">
		<spring:message code="trip.search.keyword" />:
	</form:label>
	<form:input path="keyWord" />
	<form:errors cssClass="error" path="keyWord" />
	<br />
	<br />
	
	<form:label path="lowPrice">
		<spring:message code="trip.search.lowPrice" />:
	</form:label>
	<form:input path="lowPrice" />
	<form:errors cssClass="error" path="lowPrice" />
	<br />
	<br />
	
	<form:label path="highPrice">
		<spring:message code="trip.search.highPrice" />:
	</form:label>
	<form:input path="highPrice" />
	<form:errors cssClass="error" path="highPrice" />
	<br />
	<br />
	
	<form:label path="initialDate">
		<spring:message code="trip.search.initialDate" />:
	</form:label>
	<form:input path="initialDate" placeholder=" yyyy/dd/MM" />
	<form:errors cssClass="error" path="initialDate" />
	<br />
	<br />
	
	<form:label path="finalDate">
		<spring:message code="trip.search.finalDate" />:
	</form:label>
	<form:input path="finalDate" placeholder=" yyyy/dd/MM" />
	<form:errors cssClass="error" path="finalDate" />
	<br />
	<br />
	
	<input type="submit" name="search" value="<spring:message code="trip.search" />" onclick="valida();"/> &nbsp; 	 
</form:form> 

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="trips" requestURI="${requestURI}" id="row">

<!-- Display -->
	<spring:message code="trip.display" var="Display" />
	<display:column title="${Display}" sortable="true">
		<spring:url value="trip/display.do" var="displayURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message code="trip.display" /></a>
	</display:column>


<!-- Edit Para un Manager-->
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="trip.publicationDate" var="publicationDate" />	
	<spring:message code="trip.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
			<jstl:if test="${row.manager==manager && util.publicationDate(row.publicationDate)==true && (row.reasonWhy==null or row.reasonWhy=='')}">
			
				<spring:url value="trip/manager_/edit.do" var="editURL">
					<spring:param name="tripId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="trip.edit" /></a>
			
		</jstl:if>
		</display:column>		
	</security:authorize>
	
	
<!-- Apply Para un Explorer-->
	<security:authorize access="hasRole('EXPLORER')">			
		<spring:message code="trip.apply1" var="apply1" />
		<display:column title="${apply1}" sortable="true">
			<spring:url value="applicationFor/explorer/edit.do" var="applyURL">
				<spring:param name="tripId" value="${row.id }" />
			</spring:url>
				<a href="${applyURL}"><spring:message code="trip.apply" /></a>
		</display:column>		
	</security:authorize>
	
<!-- survivalClass  Para un Explorer-->
	<security:authorize access="hasRole('EXPLORER')">
	<spring:message code="trip.classes" var="classes" />
	<display:column title="${classes}" sortable="true">
		<spring:url value="survivalClass/explorer/list-enrol.do" var="displayURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${displayURL}"><spring:message code="trip.survivalClass" /></a>
	</display:column>
	</security:authorize>
<!-- Cancel  Para un Explorer-->



	<!-- Attributes -->
	<spring:message code="trip.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="format.price" var="patternPrice"/>
	<spring:message code="trip.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" format="${patternPrice}"/>


	<spring:message code="trip.format.date2" var="pattern"></spring:message>
	<spring:message code="trip.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}" sortable="true" format="${pattern}"/>
		
	
	<spring:message code="trip.format.date2" var="pattern"></spring:message>
	<spring:message code="trip.finishDate" var="finishDateHeader" />
	<display:column property="finishDate" title="${finishDateHeader}" sortable="true" format="${pattern}"/>
	
	<spring:message code="trip.ranger" var="rangerHeader" />
	<display:column title="${rangerHeader}" sortable="true">
		<spring:url value="curricula/display.do" var="stageURL">
			<spring:param name="rangerId" value="${row.ranger.id }" />
		</spring:url>
			<a href="${stageURL}"><spring:message code="trip.ranger" /></a>
	</display:column>
	
	<spring:message code="trip.auditRecord" var="auditRecordHeader" />
	<display:column title="${auditRecordHeader}" sortable="true">
		<spring:url value="auditRecord/displayaudit.do" var="auditRecordURL">
			<spring:param name="tripId" value="${row.id }" />
		</spring:url>
			<a href="${auditRecordURL}"><spring:message code="trip.auditRecord" /></a>
	</display:column>
	
	<security:authorize access= "hasRole('MANAGER')">
	<spring:message code="trip.stage" var="Stages" />
	
	<display:column title="${Stages}" sortable="true">
	<jstl:if test="${row.manager==manager && row.cancelled==false && util.finishDateFuture(row.finishDate)==true}">
		<spring:url value="stage/manager/list.do" var="stageURL">
			<spring:param name="tripId" value="${row.id }" />
		</spring:url>
			<a href="${stageURL}"><spring:message code="trip.stage" /></a>
			</jstl:if>
	</display:column>
	
	</security:authorize>
	
		<security:authorize access="hasRole('MANAGER')">
	<spring:message code="trip.publicationDate" var="publicationDate" />	
	<spring:message code="trip.createStage" var="CreateStages" />
		<display:column title="${CreateStages}" sortable="true">
			<jstl:if test="${row.manager==manager && row.cancelled==false && util.finishDateFuture(row.finishDate)==true && util.publicationDate(row.publicationDate)==true}">
				<spring:url value="stage/manager/create.do" var="editURL">
					<spring:param name="tripId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="trip.stage.create" /></a>
			</jstl:if>
		</display:column>		
	</security:authorize>

<security:authorize access="hasRole('SPONSOR')">
	
	<spring:message code="sponsorship.create" var="Create" />
	<display:column title="${Create}" sortable="true">
	<jstl:if test="${row.cancelled==false && util.finishDateFuture(row.finishDate)==true}">
		<spring:url value="sponsorship/sponsor/create.do" var="createURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${createURL}"><spring:message code="sponsorship.create" /></a>
	</jstl:if>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<spring:message code="trip.auditRecord.create" var="Create" />
	<display:column title="${Create}" sortable="true">
	<jstl:if test="${row.cancelled==false && util.finishDateFuture(row.finishDate)==true}">
		<spring:url value="auditRecord/auditor/create.do" var="createURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${createURL}"><spring:message code="auditRecord.create" /></a>
	</jstl:if>
	</display:column>
</security:authorize>


<security:authorize access="hasRole('AUDITOR')">
<spring:message code="note.create" var="Create" />
	<display:column title="${Create}" sortable="true">
	<jstl:if test="${row.cancelled==false && util.finishDateFuture(row.finishDate)==true}">
		<spring:url value="note/auditor/create.do" var="createURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${createURL}"><spring:message code="note.create" /></a>
	</jstl:if>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<spring:message code="trip.survivalClass" var="SurvivalClasses" />
	<display:column title="${SurvivalClasses}" sortable="true">
	<jstl:if test="${row.manager==manager && row.cancelled==false && util.finishDateFuture(row.finishDate)==true && util.publicationDate(row.publicationDate)==true}">
		<spring:url value="survivalClass/manager/create.do" var="createURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${createURL}"><spring:message code="survivalClass.create" /></a>
		</jstl:if>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<spring:message code="trip.cancel" var="Cancel" />
	<display:column title="${Cancel}" sortable="true">
	<jstl:if test="${row.manager==manager && row.startDate>date && row.cancelled==false}">
		<spring:url value="trip/manager_/cancelTrip.do" var="cancelURL">
			<spring:param name="tripId" value="${row.id}" />
		</spring:url>
		<a href="${cancelURL}"><spring:message code="trip.cancel" /></a>
		</jstl:if>
	</display:column>
</security:authorize>

<jstl:if test="${row.cancelled== true}">
<spring:message code="trip.reasonWhy" />

	<display:column property="reasonWhy" sortable="true" />
	</jstl:if>
	
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="trip.publicationDate" var="publicationDate" />	
	<spring:message code="trip.tags.name" var="Tag" />
		<display:column title="${Tag}" sortable="true">
			<jstl:if test="${row.manager==manager and util.publicationDate(row.publicationDate)==true and (row.reasonWhy==null or row.reasonWhy=='')}">
				<spring:url value="tag/manager_/list.do" var="tagURL">
					<spring:param name="tripId" value="${row.id}" />
				</spring:url>
				<a href="${tagURL}"><spring:message code="trip.tags.name" /></a>
			
			</jstl:if>
		</display:column>		
	</security:authorize>
</display:table>