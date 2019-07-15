package com.dara.hpscan.internal.events.scanstatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dara.hpscan.IRequestBodyProvider;
import com.dara.hpscan.SettingsProvider;
import com.dara.hpscan.StateService;

public class ScanJobsRequest implements IRequestBodyProvider
{
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
			System.err.println("file " + m_profile + " not found");
			// e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
