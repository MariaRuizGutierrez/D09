<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<img src="${bannerURL}" alt="Acme-Explorer Co., Inc." />
</div>



<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/edit.do"><spring:message
								code="master.page.administrator.edit" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.statistics" /></a>
				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.accounts" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/create.do"><spring:message
								code="master.page.administratorProfile.administrator.create" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.services" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="serviceoffered/administrator/list.do"><spring:message
								code="master.page.servicceOffered.administrator.list" /></a></li>
				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.category" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/administrator/list.do?d-16544-p=1"><spring:message
								code="master.page.administrator.categories" /></a></li>
				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.announcements" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/administrator/list.do?d-16544-p=1"><spring:message
								code="master.page.announcement.administrator.list" /></a></li>
				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/administrator/list.do?d-16544-p=1"><spring:message
								code="master.page.administrator.rendezvous" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.comment" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="comment/administrator/list.do?d-16544-p=1"><spring:message
								code="master.page.administrator.comments" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.configurarionSystem" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configurationSystem/administrator/edit.do"><spring:message
								code="master.page.configurationSystem.edit" /></a>
				</ul></li>
				
			
		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/edit.do"><spring:message
								code="master.page.user.edit" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.question" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="question/user/list.do?d-16544-p=1"><spring:message
								code="master.page.question.list" /></a></li>

				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.serviceoffered" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="serviceoffered/user/list.do?d-16544-p=1"><spring:message
								code="master.page.serviceoffered.list" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.answer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="answer/user/list.do?d-16544-p=1"><spring:message
								code="master.page.answer.list" /></a></li>

				</ul></li>
			<li><a class="fNiv" href="user/list.do?d-16544-p=1"><spring:message
						code="master.page.user.list" /></a></li>

			<%-- <li><a class="fNiv"><spring:message code="master.page.ren" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/list-RSVP.do"><spring:message
								code="master.page.ren.list" /></a></li>

				</ul></li> --%>

			<li><a class="fNiv"><spring:message
						code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/list.do?d-16544-p=1"><spring:message
								code="master.page.mi.rendezvous" /></a></li>
					<li><a href="rendezvous/user/listasis.do?d-16544-p=1"><spring:message
								code="master.page.mi.rendezvous.asis" /></a></li>
					<li><a href="rendezvous/user/listnotasis.do?d-16544-p=1"><spring:message
								code="master.page.mi.rendezvous.notasis" /></a></li>
					<li><a href="rendezvous/user/list-deleted.do?d-16544-p=1"><spring:message
								code="deleted.ones" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.announcementsUser" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/user/listAll.do?d-16544-p=1"><spring:message
								code="master.page.mi.announcementUser" /></a></li>

				</ul></li>
		</security:authorize>


		<security:authorize access="hasRole('MANAGER')">
		
			<li><a class="fNiv"><spring:message 
						code="master.page.manager" /></a>
						
				<ul>
					<li class="arrow"></li>
					<li><a href="manager_/edit.do"><spring:message
						code="master.page.manager.edit" /></a></li>
				
				</ul>
			</li>

			<li><a class="fNiv"><spring:message
						code="master.page.serviceoffered" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="serviceoffered/manager/listb.do?d-16544-p=1"><spring:message
								code="master.page.serviceoffered.list" /></a></li>
					<li><a href="serviceoffered/manager/listAllb.do?d-16544-p=1"><spring:message
								code="master.page.serviceoffered.listAll" /></a></li>

				</ul></li>
		</security:authorize>



		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="user/list.do?d-16544-p=1"><spring:message
						code="master.page.user.list" /></a></li>
			
						<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/create.do"><spring:message
						code="master.page.user.register" /></a></li>
					<li><a href="manager_/create.do"><spring:message
						code="master.page.manager.register" /></a></li>

				</ul></li>
						
			
			
			<li><a class="fNiv"
				href="rendezvous/list-unregister.do?d-16544-p=1"><spring:message
						code="master.page.rendezvouss.list" /></a></li>
						
							<li><a class="fNiv"><spring:message
						code="category.list.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/listcat.do?d-16544-p=1"><spring:message
								code="category.list.rendezvous" /></a>
				</ul></li>
						
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>

				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
<br>
<font size="6" color = "green" face="Georgia"><B><jstl:out value="${Name}"></jstl:out></B></font>

<spring:message code="master.page.announcements" var="announcementLanguage" />
<jstl:choose>
	<jstl:when test="${announcementLanguage == 'Announcements'}">
		<br>
		<br>
		<i><jstl:out value="${EnglishWelcomeMessage}"></jstl:out></i>
	</jstl:when>
	
	<jstl:when test="${announcementLanguage == 'Anuncios'}">
		<br>
		<br>
		<i><jstl:out value="${SpanishWelcomeMessage}"></jstl:out></i>
	</jstl:when>
</jstl:choose>