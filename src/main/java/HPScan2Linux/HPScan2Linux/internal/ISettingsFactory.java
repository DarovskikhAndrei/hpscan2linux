package HPScan2Linux.HPScan2Linux.internal;

import HPScan2Linux.HPScan2Linux.ISettings;

public interface ISettingsFactory
{
    /**
     * Создает объект с настройками приложения
     */
    ISettings getSettings();
}
