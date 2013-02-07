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

import java.io.File;
import java.io.IOException;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import com.commsen.file.monitor.FileBuffer;
import com.commsen.file.monitor.FileMonitoringEngine;

public class Tailgate {
	
	static final int DEFAULT_NUMBER_OF_LINES = 100;
	static final String FILE_NAME = "fileName";
	static final String LINES = "lines";
	static final String SESSION_KEY_FILE_BUFFER = FileBuffer.class.getName();
	static final String SESSION_KEY_LAST_LINES = "com.commsen.liferay.portlet.tailgate.LINES";
	static final String WINDOW_HEIGHT = "height";


	public static boolean initFileBuffer(final PortletRequest request)
		throws IOException {

		final PortletPreferences prefs = request.getPreferences();
		final String fileName = prefs.getValue(FILE_NAME, null);

		if (fileName == null || fileName.length() == 0 || fileName.trim().length() == 0) {
			return false;
		}

		int lines;
		try {
			lines = Integer.parseInt(prefs.getValue(LINES, Integer.toString(DEFAULT_NUMBER_OF_LINES)));
		}
		catch (NumberFormatException e) {
			return false;
		}

		FileBuffer fileBuffer = (FileBuffer) request.getPortletSession().getAttribute(SESSION_KEY_FILE_BUFFER);
		if (fileBuffer != null && fileBuffer.getFileName().equals(new File(fileName).getCanonicalPath())) {
			return true;
		}

		fileBuffer = FileMonitoringEngine.newFileBuffer(fileName, lines);
		request.getPortletSession().setAttribute(SESSION_KEY_FILE_BUFFER, fileBuffer);
		return true;
	}

}
