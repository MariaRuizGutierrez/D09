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

<jstl:if test="${!existCurricula}">
	<spring:message code="curricula.notCurricula" var="notCurriculaHeader" />
	<h2><jstl:out value="${notCurriculaHeader}"></jstl:out></h2>
</jstl:if>


<!-- Si existe el curriculum  se muestra todos los atributos -->
<jstl:if test="${existCurricula}">

<display:table name="curricula" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5" keepStatus="true">

  	<!-- Attributes -->
  	<jstl:if test="${row.id != 0}">
	<spring:message code="curricula.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="false" />
	</jstl:if>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${row.id == 0}">
	<display:column>
	<spring:url value="curricula/ranger/create.do" var="linkcreate" />
	<a href="${linkcreate}"><spring:message
			code="curricula.create" /></a>
	</display:column>
	</jstl:if>
	</security:authorize>
	
</display:table>

<spring:message code="curricula.personalRecord" var="personalHeader" />
<h2><jstl:out value="${personalHeader}"></jstl:out></h2>
<display:table name="personalRecord" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<!-- Attributes -->
	<spring:message code="curricula.personalRecord.fullName" var="fullNameHeader" />
	<display:column property="fullName" title="${fullNameHeader}"
		sortable="false" />
		
	<spring:message code="curricula.personalRecord.photo" var="photoHeader" />
	<display:column property="photo" title="${photoHeader}"
		sortable="false" />
		
	<spring:message code="curricula.personalRecord.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="false" />
		
	<spring:message code="curricula.personalRecord.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"
		sortable="false" />
	
	<spring:message code="curricula.personalRecord.linkedProfile" var="linkedProfileHeader" />
	<display:column property="linkedProfile" title="${linkedProfileHeader}"
		sortable="false" />
	
	<jstl:if test="${showLink}">
	<security:authorize access="hasRole('RANGER')">
		<spring:message code="curricula.edit" var="editPersonal" />
		<display:column title="${editPersonal}">
			<spring:url value="personalRecord/ranger/edit.do" var="editlink">
				<spring:param name="personalRecordId" value="${row.id}" />
			</spring:url>
			<a href="${editlink}"><spring:message code="curricula.edit" /></a>
		</display:column>
	</security:authorize>
	</jstl:if>
	
</display:table>

<spring:message code="curricula.educationRecord" var="educationHeader" />
<h2><jstl:out value="${educationHeader}"></jstl:out></h2>
<display:table name="educationRecord" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<!-- Attributes -->
	<spring:message code="curricula.educationRecord.diplomaTitle" var="diplomaTitleHeader" />
	<display:column property="diplomaTitle" title="${diplomaTitleHeader}"
		sortable="false" />
		
	<spring:message code="curricula.educationRecord.studyingPeriod" var="studyingPeriodHeader" />
	<display:column property="studyingPeriod" title="${studyingPeriodHeader}"
		sortable="false" />
		
	<spring:message code="curricula.educationRecord.institution" var="institutionHeader" />
	<display:column property="institution" title="${institutionHeader}"
		sortable="false" />
		
	<spring:message code="curricula.educationRecord.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}"
		sortable="false" />
	
	<spring:message code="curricula.educationRecord.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}"
		sortable="false" />
	
	<jstl:if test="${showLink}">
	<security:authorize access="hasRole('RANGER')">
		<spring:message code="curricula.edit" var="editEducation" />
		<display:column title="${editEducation}">
			<spring:url value="educationRecord/ranger/edit.do" var="editlink">
				<spring:param name="educationRecordId" value="${row.id}" />
			</spring:url>
			<a href="${editlink}"><spring:message code="curricula.edit" /></a>
		</display:column>
	</security:authorize>
	</jstl:if>
	
</display:table>

<jstl:if test="${showLink}">
<security:authorize access="hasRole('RANGER')">
	<spring:url value="educationRecord/ranger/create.do" var="linkcreate" />
	<a href="${linkcreate}"><spring:message
			code="curricula.educationRecord.create" /></a>
</security:authorize>
</jstl:if>

<spring:message code="curricula.professionalRecord" var="professionalHeader" />
<h2><jstl:out value="${professionalHeader}"></jstl:out></h2>
<display:table name="professionalRecord" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<!-- Attributes -->
	<spring:message code="curricula.professionalRecord.companyName" var="companyNameHeader" />
	<display:column property="companyName" title="${companyNameHeader}"
		sortable="false" />
		
	<spring:message code="curricula.professionalRecord.workPeriod" var="workPeriodHeader" />
	<display:column property="workPeriod" title="${workPeriodHeader}"
		sortable="false" />
		
	<spring:message code="curricula.professionalRecord.role" var="roleHeader" />
	<display:column property="role" title="${roleHeader}"
		sortable="false" />
		
	<spring:message code="curricula.professionalRecord.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}"
		sortable="false" />
	
	<spring:message code="curricula.professionalRecord.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}"
		sortable="false" />
	
	<jstl:if test="${showLink}">
	<security:authorize access="hasRole('RANGER')">
		<spring:message code="curricula.edit" var="editProfessional" />
		<display:column title="${editProfessional}">
			<spring:url value="professionalRecord/ranger/edit.do" var="editlink">
				<spring:param name="professionalRecordId" value="${row.id}" />
			</spring:url>
			<a href="${editlink}"><spring:message code="curricula.edit" /></a>
		</display:column>
	</security:authorize>
	</jstl:if>
	
</display:table>

<jstl:if test="${showLink}">
<security:authorize access="hasRole('RANGER')">
	<spring:url value="professionalRecord/ranger/create.do" var="linkcreate" />
	<a href="${linkcreate}"><spring:message
			code="curricula.professionalRecord.create" /></a>
</security:authorize>
</jstl:if>

<spring:message code="curricula.endorserRecord" var="endorserHeader" />
<h2><jstl:out value="${endorserHeader}"></jstl:out></h2>
<display:table name="endorserRecord" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<!-- Attributes -->
	<spring:message code="curricula.endorserRecord.fullName" var="fullNameHeader" />
	<display:column property="fullName" title="${fullNameHeader}"
		sortable="false" />
		
	<spring:message code="curricula.endorserRecord.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="false" />
		
	<spring:message code="curricula.endorserRecord.linkedProfile" var="linkedProfileHeader" />
	<display:column property="linkedProfile" title="${linkedProfileHeader}"
		sortable="false" />
		
	<spring:message code="curricula.endorserRecord.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}"
		sortable="false" />
	
	<spring:message code="curricula.endorserRecord.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"
		sortable="false" />
	
	<jstl:if test="${showLink}">	
	<security:authorize access="hasRole('RANGER')">
		<spring:message code="curricula.edit" var="editEndorser" />
		<display:column title="${editEndorser}">
			<spring:url value="endorserRecord/ranger/edit.do" var="editlink">
				<spring:param name="endorserRecordId" value="${row.id}" />
			</spring:url>
			<a href="${editlink}"><spring:message code="curricula.edit" /></a>
		</display:column>
	</security:authorize>
	</jstl:if>
	
</display:table>

<jstl:if test="${showLink}">
<security:authorize access="hasRole('RANGER')">
	<spring:url value="endorserRecord/ranger/create.do" var="linkcreate" />
	<a href="${linkcreate}"><spring:message
			code="curricula.endorserRecord.create" /></a>
</security:authorize>
</jstl:if>


<spring:message code="curricula.miscellaneousRecord" var="miscellaneousHeader" />
<h2><jstl:out value="${miscellaneousHeader}"></jstl:out></h2>
<display:table name="miscellaneousRecord" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<!-- Attributes -->
	<spring:message code="curricula.miscellaneousRecord.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="false" />
	
	<spring:message code="curricula.miscellaneousRecord.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}"
		sortable="false" />
		
	<spring:message code="curricula.miscellaneousRecord.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}"
		sortable="false" />
		
	<jstl:if test="${showLink}">
	<security:authorize access="hasRole('RANGER')">
		<spring:message code="curricula.edit" var="editMiscellaneous" />
		<display:column title="${editMiscellaneous}">
			<spring:url value="miscellaneousRecord/ranger/edit.do" var="editlink">
				<spring:param name="miscellaneousRecordId" value="${row.id}" />
			</spring:url>
			<a href="${editlink}"><spring:message code="curricula.edit" /></a>
		</display:column>
	</security:authorize>
	</jstl:if>
	
</display:table>

<jstl:if test="${showLink}">
<security:authorize access="hasRole('RANGER')">
	<spring:url value="miscellaneousRecord/ranger/create.do" var="linkcreate" />
	<a href="${linkcreate}"><spring:message
			code="curricula.miscellaneousRecord.create" /></a>
</security:authorize>
</jstl:if>
</jstl:if>