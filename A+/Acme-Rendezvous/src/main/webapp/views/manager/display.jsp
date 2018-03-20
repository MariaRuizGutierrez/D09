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
  
  <display:table name="manager" class="displaytag"
	requestURI="${requestURI}" id="row">
	
	
	<display:column>
<p> <B><spring:message code="manager.vat" />: </B> <jstl:out value="${row.vat}"></jstl:out></p>
  <p> <B><spring:message code="manager.name" />: </B> <jstl:out value="${row.name}"></jstl:out></p>
  <p> <B><spring:message code="manager.surname" />: </B> <jstl:out value="${row.surname}"></jstl:out></p>
  <p> <B><spring:message code="manager.postalAddress" />: </B> <jstl:out value="${row.postalAddress}"></jstl:out></p>
  <p> <B><spring:message code="manager.phoneNumber" />: </B> <jstl:out value="${row.phoneNumber}"></jstl:out></p>
   <p> <B><spring:message code="manager.emailAddress" />: </B> <jstl:out value="${row.phoneNumber}"></jstl:out></p>
  
  <p> 
  </display:column>
  
    
</display:table>