<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
 
 
<form:form action="note/manager/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="manager"/> 
	
	<form:label path="createdMoment" placeholder=" yyyy/dd/MM HH:hh" >
		<spring:message code="note.createdMoment" />:
	</form:label>
	<form:input path="createdMoment" readonly="true"/>
	<br />
	
	<form:label path="body">
		<spring:message code="note.body" />:
	</form:label>
	<form:input path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="remark">
		<spring:message code="note.remark" />
	</form:label>
	<form:input path="remark" />
	<form:errors cssClass="error" path="remark" />
	<br />
	
	<form:label path="reply" >
		<spring:message code="note.reply" />
	</form:label>
	<form:input path="reply" readonly="true"/>
	<br />
	
	<form:label path="replyMoment" >
		<spring:message code="note.replyMoment" />
	</form:label>
	<form:input path="replyMoment" readonly="true"/>
	<br />
	
	
	<!-- botones -->
	<input type="submit" name="save"
		value="<spring:message code="note.save"/>" />&nbsp;
	
	<jstl:if test="${event.id !=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="note.delete"/>"
			onclick="javascript: return confirm('<spring:message code="note.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel"/>"
		onclick="javascript: window.location.replace('note/manager/list.do')" />
	<br />
</form:form>
 