package HPScan2Linux.HPScan2Linux.internal;

import HPScan2Linux.HPScan2Linux.ISettings;

public final class Settings implements ISettings
{
    private final String path2ScanSave;
    private final String printerAddress;
    private final int printerPort;
    private final String profilesPath;
    private final String proxyHost;
    private final int proxyPort;

    @Override
    public String getPathForScanSave()
    {
        return path2ScanSave;
    }

    @Override
    public String getPrinterAddr()
    {
        return printerAddress;
    }

    @Override
    public int getPrinterPort()
    {
        return printerPort;
    }

    @Override
    public String getProfilesPath()
    {
        return profilesPath;
    }

    @Override
    public String getProxyHost()
    {
        return proxyHost;
    }

    @Override
    public int getProxyPort()
    {
        return proxyPort;
    }

    public Settings(String path2ScanSave, String printerAddress, int printerPort, String profilesPath,
                    String proxyHost, int proxyPort)
    {
        this.path2ScanSave = path2ScanSave;
        this.printerAddress = printerAddress;
        this.printerPort = printerPort;
        this.profilesPath = profilesPath;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }
}
