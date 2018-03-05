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

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>

	<img src="images/rendezvous2.jpg" alt="Acme-Rendezvous Co., Inc." />
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
						code="master.page.announcements" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/administrator/list.do"><spring:message
								code="master.page.announcement.administrator.list" /></a></li>
				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/administrator/list.do"><spring:message
								code="master.page.administrator.rendezvous" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.comment" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="comment/administrator/list.do"><spring:message
								code="master.page.administrator.comments" /></a></li>

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
					<li><a href="question/user/list.do"><spring:message
								code="master.page.question.list" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.answer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="answer/user/list.do"><spring:message
								code="master.page.answer.list" /></a></li>

				</ul></li>
			<li><a class="fNiv" href="user/list.do"><spring:message
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
					<li><a href="rendezvous/user/list.do"><spring:message
								code="master.page.mi.rendezvous" /></a></li>
					<li><a href="rendezvous/user/listasis.do"><spring:message
								code="master.page.mi.rendezvous.asis" /></a></li>
					<li><a href="rendezvous/user/listnotasis.do"><spring:message
								code="master.page.mi.rendezvous.notasis" /></a></li>
					<li><a href="rendezvous/user/list-deleted.do"><spring:message
								code="deleted.ones" /></a></li>

				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.announcementsUser" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/user/listAll.do"><spring:message
								code="master.page.mi.announcementUser" /></a></li>

				</ul></li>
		</security:authorize>


		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message
						code="master.page.user.list" /></a></li>
			<li><a class="fNiv" href="user/create.do"><spring:message
						code="master.page.user.register" /></a></li>
			<li><a class="fNiv" href="rendezvous/list-unregister.do"><spring:message
						code="master.page.rendezvouss.list" /></a></li>
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

