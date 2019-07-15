package com.dara.hpscan.internal;

public final class FileNameResolverFactory
{
    static public FileNameResolver createDefault()
    {
        return new FileNameByDateResolver();
    }
}
