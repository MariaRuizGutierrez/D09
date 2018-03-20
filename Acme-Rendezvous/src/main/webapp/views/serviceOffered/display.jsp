<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%-- <form:form action="${resquestURI}" modelAttribute="serviceOffered"> --%>
  
  <display:table name="serviceOffered" class="displaytag"
	requestURI="${requestURI}" id="row">
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<%!String estilo;%>
	<jstl:choose>
			<jstl:when test="${serviceOffered.cancelled==false}">
				<%=estilo = "p1"%>

			</jstl:when>

			<jstl:when test="${serviceOffered.cancelled==true}">

				<%=estilo = "p2"%>
			</jstl:when>
		</jstl:choose>
	</security:authorize>
	
  <jstl:if test="${serviceOffered.picture==''}">
	<spring:message code="nothing.found.to.display" />
	</jstl:if>
	
	<jstl:if test="${!(serviceOffered.picture=='') }">
	<spring:message code="serviceoffered.picture" var="titleHeader" />
	<display:column title="${titleHeader}" class="<%= estilo %>">

		<div
			style="position: relative; width: 500px; height: 300px; margin-left: auto; margin-right: auto;">

			<img src="${row.picture}" width="500" height="300">
		</div>
	</display:column>
	</jstl:if> 
	
	<display:column class="<%= estilo %>">
	
  <p> <B><spring:message code="serviceoffered.name" />: </B> <jstl:out value="${serviceOffered.name}"></jstl:out></p>
  <p> <B><spring:message code="serviceoffered.description" />: </B> <jstl:out value="${serviceOffered.description}"></jstl:out></p>
  <p> <B><spring:message code="serviceoffered.category" />: </B> <jstl:out value="${serviceOffered.category.concat}"></jstl:out></p>
  <p> <B><spring:message code="serviceOffered.cancelled" />: </B> <jstl:out value="${serviceOffered.cancelled}"></jstl:out></p>
  <p> 
  </display:column>
  
    
</display:table>