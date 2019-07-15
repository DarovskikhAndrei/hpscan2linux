package com.dara.hpscan;

public interface ISettings
{
    String getPathForScanSave();

    String getPrinterAddr();

    int getPrinterPort();

    String getProfilesPath();

    String getProxyHost();

    int getProxyPort();

    String getLoggingCfgFileName();
}
