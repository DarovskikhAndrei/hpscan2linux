package com.dara.hpscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.internal.settings.ISettingsFactory;
import com.dara.hpscan.internal.settings.LinuxSettingsFactory;
import com.dara.hpscan.internal.settings.WindowsSettingsFactory;

public final class SettingsProvider
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsProvider.class);

    private volatile static SettingsProvider settingsProvider;

    private volatile ISettings settings;

    static public ISettings getSettings()
    {
        if (settingsProvider != null)
            return settingsProvider.settings();
        synchronized (settingsProvider)
        {
            if (settingsProvider == null)
                settingsProvider = new SettingsProvider();

            return settingsProvider.settings();
        }
    }

    public ISettings settings()
    {
        if (settings != null)
            return settings;

        synchronized (this)
        {
            if (settings != null)
                return settings;

            settings = getSettingsFactory().getSettings();
            return settings;
        }
    }

    private ISettingsFactory getSettingsFactory()
    {
        return isWindows() ? new WindowsSettingsFactory() : new LinuxSettingsFactory();
    }

    public static boolean isWindows()
    {
        String os = System.getProperty("os.name").toLowerCase();
        // windows
        return (os.indexOf("win") >= 0);

    }
}