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

package com.commsen.liferay.portlet.tailgate;

import static com.commsen.liferay.portlet.tailgate.Tailgate.LINES;
import static com.commsen.liferay.portlet.tailgate.Tailgate.SESSION_KEY_FILE_BUFFER;
import static com.commsen.liferay.portlet.tailgate.Tailgate.SESSION_KEY_LAST_LINES;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.commsen.file.monitor.FileBuffer;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author Milen Dyankov
 */
public class TailgatePortlet extends MVCPortlet {

	public void doView(final RenderRequest request, final RenderResponse response)
		throws IOException, PortletException {

		@SuppressWarnings("unchecked")
		final Queue<String> lastLines = (Queue) request.getPortletSession().getAttribute(SESSION_KEY_LAST_LINES);
		final StringBuilder result = new StringBuilder();
		if (lastLines != null) {
			for (String line : lastLines) {
				result.append("<li>").append(line).append("</li>");
			}
		}
		request.setAttribute(LINES, result.toString());

		super.doView(request, response);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * javax.portlet.GenericPortlet#serveResource(javax.portlet.ResourceRequest,
	 * javax.portlet.ResourceResponse)
	 */
	@Override
	public void serveResource(final ResourceRequest request, final ResourceResponse response)
		throws PortletException, IOException {

		FileBuffer fileBuffer = (FileBuffer) request.getPortletSession().getAttribute(SESSION_KEY_FILE_BUFFER);
		if (fileBuffer == null && Tailgate.initFileBuffer(request)) {
			fileBuffer = (FileBuffer) request.getPortletSession().getAttribute(SESSION_KEY_FILE_BUFFER);
		}
		@SuppressWarnings("unchecked")
		Queue<String> lastLines = (Queue) request.getPortletSession().getAttribute(SESSION_KEY_LAST_LINES);
		if (lastLines == null) {
			lastLines = new LinkedList<String>();
			request.getPortletSession().setAttribute(SESSION_KEY_LAST_LINES, lastLines);
		}

		if (fileBuffer != null) {
			if (!fileBuffer.isEnabled()) {
				fileBuffer.setEnabled(true);
			}
			String line;
			final PrintWriter writer = response.getWriter();
			while ((line = fileBuffer.readLine()) != null) {
				lastLines.add(line);
				if (lastLines.size() > fileBuffer.getMaxSize()) {
					lastLines.remove();
				}
				writer.println("<li>" + line + "</li>");
			}
		}
	}

}
