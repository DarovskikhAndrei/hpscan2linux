package com.dara.hpscan.internal.events.scanstatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.SettingsProvider;
import com.dara.hpscan.StateService;

public class ScanJobsRequest implements IRequestBodyProvider
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ScanJobsRequest.class);

	public ScanJobsRequest()
	{
		m_profile = StateService.getInstance().getProfile();
	}

	@Override
	public String getBody()
	{
		String profiles = SettingsProvider.getSettings().getProfilesPath();
		File file = new File(profiles + m_profile + ".xml");
		try
		{
			FileInputStream buf = new FileInputStream(file);
			String result = "";
			int chr;
			while ((chr = buf.read()) != -1)
			{
				result += (char) chr;
			}

			buf.close();

			return result;
		}
		catch (FileNotFoundException e)
		{
			LOGGER.error("file {} not found");
		}
		catch (IOException e)
		{
			LOGGER.error("io exception", e);
		}

		return "";
	}

	@Override
	public String contentType()
	{
		return "text/xml";
	}

	private String m_profile;
}
