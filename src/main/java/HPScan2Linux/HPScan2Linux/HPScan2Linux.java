package HPScan2Linux.HPScan2Linux;

import java.io.IOException;

public class HPScan2Linux 
{
    public static void main( String[] args )
    {
		registerShutdownHook();
		
		boolean dem = true;
		
		try
		{
			if (dem)
				demonize();
		}
		catch (IOException e) {
			// TODO: handle exception
			System.err.println("error demonize");
			return;
		}

		SettingsProvider settings = SettingsProvider.getSettings();
    	HPScan2Client client =  new HPScan2Client();
    	client.init(settings.getPrinterAddr(), settings.getPrinterPort());

		while (false == m_shutdownFlag)
		{
	    	try {
				client.process();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    static public void setShutdownFlag() {
    	System.err.println("close event");
    	m_shutdownFlag = true;
    }

    private static void registerShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(
            new Thread() {
                public void run() {
                	HPScan2Linux.setShutdownFlag();
                }
            }
        );
    }	
    
    static private void demonize() throws IOException {
    	System.out.close();
    	System.in.close();
    }

	volatile static private boolean m_shutdownFlag = false; 
}
