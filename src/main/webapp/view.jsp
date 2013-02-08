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

<div>
<button id="<portlet:namespace />_start">Start</button>
<button id="<portlet:namespace />_stop">Stop</button>
listing file <strong><%=fileName%></strong>. <small>(Showing only last <%=lines%> lines)</small>
</div>

	<ul id="<portlet:namespace />_list" class="tailgate" style="height: <%=height%>"> 
		<%=request.getAttribute("lines") %>
	</ul> 
	 

	<aui:script use="aui-io-request">
	
	var handle;
	clearInterval(handle);
	A.one('#<portlet:namespace />_stop').hide();
	
	A.one('#<portlet:namespace />_start').on('click', function() {
		handle = setInterval(
			function() {
				A.io.request('<liferay-portlet:resourceURL />', {
					on: {
						success: function() {
		        			var data = this.get('responseData');
		        			A.one('#<portlet:namespace />_list').append(data);
		        			
		        			// delete first lines if too long 				
							var lines = A.all('#<portlet:namespace />_list li').size();
							if (lines > <%=lines%>) {
								A.all('#<portlet:namespace />_list li').slice(0, lines - <%=lines%>).remove();
							}
		      			}
		    		}
				});
			},
			1000
		);
		A.one('#<portlet:namespace />_start').hide();
		A.one('#<portlet:namespace />_stop').show();	
		A.one('#<portlet:namespace />_list').addClass('tailgateRunnig');
	});
		
	A.one('#<portlet:namespace />_stop').on('click', function() {
		clearInterval(handle);
		A.one('#<portlet:namespace />_stop').hide();	
		A.one('#<portlet:namespace />_start').show();
		A.one('#<portlet:namespace />_list').removeClass('tailgateRunnig');
	});
	
	</aui:script>