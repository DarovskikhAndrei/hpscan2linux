package com.dara.hpscan;

import java.io.*;
import java.util.logging.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dara.hpscan.internal.HPScan2Client;

public final class HPScan2Linux
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HPScan2Linux.class);

    public static void main(String[] args)
    {
        registerShutdownHook();

        boolean dem = true;

        try
        {
            if (dem)
            {
                demonize();
            }
        }
        catch (IOException e)
        {
            LOGGER.error("Demonizing error");
            return;
        }

        ISettings settings = SettingsProvider.getSettings();

        if (settings.getLoggingCfgFileName() != null)
        {
            File cfgFile = new File(settings.getLoggingCfgFileName());
            if (cfgFile.exists())
            {
                try (InputStream is = new FileInputStream(cfgFile))
                {
                    LogManager.getLogManager().readConfiguration(is);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        try (HPScan2Client client = new HPScan2Client())
        {
            client.init(settings.getPrinterAddr(), settings.getPrinterPort());

            while (false == m_shutdownFlag)
            {
                client.process();
            }
        }
        catch (IOException e)
        {
            LOGGER.error("Error processing", e);
        }
    }

    static public void setShutdownFlag()
    {
        LOGGER.info("Installed close event");
        m_shutdownFlag = true;
    }

    private static void registerShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(
                new Thread()
                {
                    public void run()
                    {
                        HPScan2Linux.setShutdownFlag();
                    }
                }
        );
    }

    static private void demonize() throws IOException
    {
        System.out.close();
        System.in.close();
    }

    volatile static private boolean m_shutdownFlag = false;
}
