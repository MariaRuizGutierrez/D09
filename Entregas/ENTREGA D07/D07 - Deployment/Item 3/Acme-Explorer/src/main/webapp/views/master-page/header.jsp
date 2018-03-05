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
	<img src="${bannerURL}" alt="Acme-Explorer Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsor/edit.do"><spring:message code="master.page.sponsor.edit" /></a></li>
				</ul></li>
				<li><a class="fNiv"><spring:message
						code="master.page.mensage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageFolder/sponsor/list.do"><spring:message code="master.page.messageFolder.list" /></a></li>
					<li><a href="message/sponsor/send.do"><spring:message code="master.page.message.send" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.sponsorship" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsorship.sponsor.list" /></a></li>
					<li><a href="trip/sponsor/list.do"><spring:message code="master.page.sponsor.list.trips" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadostrips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadoscategory" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="auditor/edit.do"><spring:message code="master.page.auditor.edit" /></a></li>
				</ul></li>
		
				<li><a class="fNiv"><spring:message
						code="master.page.mensage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageFolder/auditor/list.do"><spring:message code="master.page.messageFolder.list" /></a></li>
					<li><a href="message/auditor/send.do"><spring:message code="master.page.message.send" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.note" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="note/auditor/list.do"><spring:message code="master.page.note.auditor.list" /></a></li>
					<li><a href="trip/auditor/list.do"><spring:message code="master.page.auditorNote.list.trips" /></a>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.auditRecordListandCreate" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="auditRecord/auditor/list.do"><spring:message code="master.page.auditRecord.auditor.list" /></a></li>
					<li><a href="trip/auditor/list.do"><spring:message code="master.page.auditor.list.trips" /></a>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.noautenticadostrips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadoscategory" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="explorer/edit.do"><spring:message code="master.page.explorer.edit" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.mensage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageFolder/explorer/list.do"><spring:message code="master.page.messageFolder.list" /></a></li>
					<li><a href="message/explorer/send.do"><spring:message code="master.page.message.send" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.applicationFor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="applicationFor/explorer/list.do"><spring:message code="master.page.explorer.list.applicationFor" /></a></li>
					<li><a href="trip/explorer/list-not-apply.do"><spring:message code="master.page.explorer.list.not.apply.trips" /></a></li>
					<li><a href="finder/explorer/list.do"><spring:message code="master.page.message.finder" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.stories" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="story/explorer/list.do"><spring:message code="master.page.explorer.stories" /></a></li>
					<li><a href="trip/explorer/listTrip.do"><spring:message code="master.page.explorer.trips.stories" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.emergency" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="contactsEmergency/explorer/list.do"><spring:message code="master.page.explorer.contactEmergency" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.noautenticadostrips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadoscategory" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.statistics" /></a>
					
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.mensage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageFolder/administrator/list.do"><spring:message code="master.page.messageFolder.list" /></a></li>
					<li><a href="message/administrator/send.do"><spring:message code="master.page.message.send" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.category" /></a>
				<ul>
					<li class="arrow"></li>
					 <li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
					<li><a href="category/administrator/create.do"><spring:message code="master.page.administrator.category.create" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.accounts" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rangerProfile/administrator/create.do"><spring:message code="master.page.rangerProfile.administrator.create" /></a></li>
					<li><a href="auditorProfile/administrator/create.do"><spring:message code="master.page.auditorProfile.administrator.create" /></a></li>
					<li><a href="managerProfile/administrator/create.do"><spring:message code="master.page.managerProfile.administrator.create" /></a></li>
					<li><a href="sponsorProfile/administrator/create.do"><spring:message code="master.page.sponsorProfile.administrator.create" /></a></li>
					<li><a href="administrator/create.do"><spring:message code="master.page.administratorProfile.administrator.create" /></a></li>
					<li><a href="explorerProfile/create.do"><spring:message code="master.page.explorerProfile.create" /></a></li>
					
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.legalText" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="legalText/administrator/list.do"><spring:message code="master.page.legaltext.list" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.configurarionSystem" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configurationSystem/administrator/edit.do"><spring:message code="master.page.configurationSystem.edit" /></a>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.suspicious" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="suspicious/administrator/list_ban.do"><spring:message code="master.page.suspicious"/></a>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.tags" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tag/administrator/list.do"><spring:message code="master.page.tag"/></a>
				</ul></li>
				
				
			<li><a class="fNiv"><spring:message
						code="master.page.noautenticadostrips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('RANGER')">
			<li><a class="fNiv"><spring:message code="master.page.ranger" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rangerProfile/ranger/edit.do"><spring:message code="master.page.rangerProfile.ranger.edit" /></a></li>
			
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.mensage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageFolder/ranger/list.do"><spring:message code="master.page.messageFolder.list" /></a></li>
					<li><a href="message/ranger/send.do"><spring:message code="master.page.message.send" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.curricula" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curricula/ranger/display.do"><spring:message code="master.page.curricula.ranger.display" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.noautenticadostrips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadoscategory" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv "><spring:message
						code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="manager_/edit.do"><spring:message code="master.page.sponsor.edit" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.mensage" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageFolder/manager/list.do"><spring:message code="master.page.messageFolder.list" /></a></li>
					<li><a href="message/manager/send.do"><spring:message code="master.page.message.send" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.applicationFor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="applicationFor/manager/list.do"><spring:message code="master.page.manager.list.applicationFor" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.note" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="note/manager/list.do"><spring:message code="master.page.note.manager.list" /></a></li>
				</ul></li>
				
			<li><a class="fNiv"><spring:message
						code="master.page.survivalClass" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="survivalClass/manager/list.do"><spring:message code="master.page.survivalClass.manager" /></a></li>
					<li><a href="trip/manager_/list.do"><spring:message code="master.page.manager.createClass.trips" /></a></li>
				</ul></li> 
				
				<li><a class="fNiv"><spring:message
						code="master.page.funcionality" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/manager_/list.do"><spring:message code="master.page.manager.list.trips" /></a></li>
				</ul></li> 
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadostrips" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.noautenticadoscategory" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
				</ul></li>
		</security:authorize>




		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
			<li><a href="category/list.do"><spring:message code="master.page.administrator.categories" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li><a href="rangerProfile/create.do"><spring:message code="master.page.rangerProfile.create" /></a></li>
					<li><a href="explorerProfile/create.do"><spring:message code="master.page.explorerProfile.create" /></a></li>
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

