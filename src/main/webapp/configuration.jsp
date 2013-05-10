
<%
/**
 *	This file is part of Tailgate Liferay plug-in.
 *	
 * Tailgate Liferay plug-in is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Tailgate Liferay plug-in is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tailgate Liferay plug-in.  If not, see <http://www.gnu.org/licenses/lgpl.html>.
 */

%>

<%@include file="init.jsp" %>


<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	
	<liferay-ui:error key="NOT_VALID_FILE_NAME" message="please-enter-valid-file-name" />
	<aui:input  label="file.name" name="preferences--fileName--" type="text" value='<%=fileName%>' />
	<aui:input  label="lines.to.show" name="preferences--lines--" type="text" value='<%=lines%>' />
	<aui:input  label="window.height" name="preferences--height--" type="text" value='<%=height%>' />
	
	<aui:button label="save" type="submit" />
	
</aui:form>
