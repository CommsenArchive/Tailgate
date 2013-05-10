
package com.commsen.liferay.portlet.tailgate;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;

public class ConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String fileName = getParameter(actionRequest, Tailgate.FILE_NAME);
		if (fileName != null && fileName.length() != 0 && fileName.trim().length() != 0) {
			File file = new File(fileName);
			if (!file.isFile()) {
				SessionErrors.add(actionRequest, "NOT_VALID_FILE_NAME");
			}
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}
