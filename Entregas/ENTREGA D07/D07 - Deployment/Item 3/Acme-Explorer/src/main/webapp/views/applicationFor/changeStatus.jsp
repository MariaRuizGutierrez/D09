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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="applicationFor/manager/change.do"
	modelAttribute="applicationFor" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="explorer" />
	<form:hidden path="manager" />
	<form:hidden path="moment" />
	<form:hidden path="trip" />
	<form:hidden path="comments"/>
	<form:hidden path="creditCard.holderName" />
	<form:hidden path="creditCard.brandName" />
	<form:hidden path="creditCard.number" />
	<form:hidden path="creditCard.expirationMonth" />
	<form:hidden path="creditCard.expirationYear" />
	<form:hidden path="creditCard.cvv" />


	<form:label path="status">
		<spring:message code="applicationfor.status" />:
	</form:label>
	<form:select path="status" id="test">
		<form:option label="PENDING" value="PENDING" />
		<form:option label="REJECTED" value="REJECTED" />
		<form:option label="DUE" value="DUE" />
	</form:select>
	<br />
	<br />
<script type="text/JavaScript">
	$('#test').change(function() {

	    if ($('#test').val() == "PENDING")
	    {
	        $(".ocul").css("visibility","hidden");
	    };
	    
	    if ($('#test').val() == "DUE")
	    {
	        $(".ocul").css("visibility","hidden");
	    };
	    if ($('#test').val() == "REJECTED")
	    {
	        $(".ocul").css("visibility","visible");
	    };
	    
	});
	</script>


	<form:label path="reasonWhy" class="ocul">
		<spring:message code="applicationfor.reason" />:
	</form:label>

	<form:input path="reasonWhy" class="ocul"/>
	<%-- 	<form:errors cssClass="error" path="reasonWhy" /> --%>
	<br />



	<input type="submit" name="save"
		value="<spring:message code="applicationfor.save" />" />&nbsp;

	<input type="button" name="cancel"
		value="<spring:message code="applicationfor.cancel" />"
		onclick="javascript:  window.location.replace('applicationFor/manager/list.do');" />
	<br />
</form:form>
