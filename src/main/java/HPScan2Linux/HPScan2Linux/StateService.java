package HPScan2Linux.HPScan2Linux;

public final class StateService
{
    private static StateService m_instance;

    public static synchronized StateService getInstance()
    {
        if (m_instance == null)
        {
            m_instance = new StateService();
        }
        return m_instance;
    }

    public void setProfile(String profile)
    {
        m_profile = profile;
    }

    public String getProfile()
    {
        return m_profile;
    }

    public void setJobURL(String url)
    {
        m_jobURL = url;
    }

    public String getJobURL()
    {
        return m_jobURL;
    }

    public void setBinaryURL(String url)
    {
        m_binaryURL = url;
    }

    public String getBinaryURL()
    {
        return m_binaryURL;
    }

    private String m_profile;
    private String m_jobURL;
    private String m_binaryURL;
}
