package com.dara.hpscan.internal.settings;

import com.dara.hpscan.ISettings;

public interface ISettingsFactory
{
    /**
     * Создает объект с настройками приложения
     */
    ISettings getSettings();
}
