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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="categories" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<spring:message code="category.edit" var="edit"></spring:message>
		<display:column title="${edit}" sortable="true">
		<jstl:if test="${(!(row.name=='CATEGORY') and
			!(row.name=='Safari' and row.fatherCategory.name=='CATEGORY') and
			!(row.name=='Ground' and row.fatherCategory.name=='Safari') and
			!(row.name=='Savana' and row.fatherCategory.name=='Ground') and
			!(row.name=='Mountain' and row.fatherCategory.name=='Ground') and
			!(row.name=='Water' and row.fatherCategory.name=='Safari') and
			!(row.name=='Lake' and row.fatherCategory.name=='Water') and
			!(row.name=='River' and row.fatherCategory.name=='Water') and
			!(row.name=='Climbing' and row.fatherCategory.name=='CATEGORY') and
			!(row.name=='Mountain' and row.fatherCategory.name=='Climbing') and
			!(row.name=='River' and row.fatherCategory.name=='Climbing')
			)}">
			<spring:url value="category/administrator/edit.do" var="editURL">
			<spring:param name="categoryId" value="${row.id}"/>
			</spring:url>
			<a href="${editURL}"><spring:message code="category.edit"/></a>
		</jstl:if>
		</display:column>			
	</security:authorize>
	
	<!-- Attributes -->
	
	<spring:message code="category.father.name" var="fatherHeader" />
	<display:column property="fatherCategory.name" title="${fatherHeader}" sortable="true" />

	
	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	
	
	<spring:message code="category.subcategories.name" var="subCategories" />:
	<display:column title =" ${subCategories}" sortable="true">
		<jstl:forEach var="subCategories" items="${row.subCategories}">
			<jstl:out value="${subCategories.name}"></jstl:out><br/>
		</jstl:forEach>	
	</display:column>
	
	<spring:message code="category.trips" var="nameHeader" />
	
		<display:column  title="${nameHeader}" sortable="true" >
		<spring:url value="trip/category/list.do" var="tripListURL">
		 <spring:param name="categoryId" value="${row.id}"/>
		 </spring:url>
		 <a href="${tripListURL}"><spring:message code="category.trips"/></a>
		 </display:column>
		

	
	

	
</display:table>
	
<!-- Action links -->

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<a href="category/administrator/create.do"> <spring:message
				code="category.create" />
		</a>
	</div>
</security:authorize>