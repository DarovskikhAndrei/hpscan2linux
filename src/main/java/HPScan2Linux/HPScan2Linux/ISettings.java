package HPScan2Linux.HPScan2Linux;

public interface ISettings
{
    String getPathForScanSave();

    String getPrinterAddr();

    int getPrinterPort();

    String getProfilesPath();

    String getProxyHost();

    int getProxyPort();
}
