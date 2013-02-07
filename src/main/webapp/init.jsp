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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>


<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>

<liferay-theme:defineObjects />
<portlet:defineObjects />


<%
String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}


String fileName = GetterUtil.getString(portletPreferences.getValue("fileName", null));
int lines = GetterUtil.getInteger(portletPreferences.getValue("lines", "100"));
String height = GetterUtil.getString(portletPreferences.getValue("height", null));
%>