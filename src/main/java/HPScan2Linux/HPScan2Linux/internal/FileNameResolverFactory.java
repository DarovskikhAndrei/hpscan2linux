package HPScan2Linux.HPScan2Linux.internal;

public final class FileNameResolverFactory
{
    static public FileNameResolver createDefault()
    {
        return new FileNameByDateResolver();
    }
}
