package HPScan2Linux.HPScan2Linux;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class GetScanResultResponse extends ResponseExecutor {
	@Override
	public void execute() {
	}
	
	@Override
	public void init(HttpResponse response) {
		InputStream inStream = getBodyStream(response);
		if (inStream == null)
			return;

		String path = SettingsProvider.getSettings().getPathForScanSave();
		String fileName = getFileName();
		String fullFileName = path + fileName;
		
		try {
			FileOutputStream fos = new FileOutputStream(fullFileName);
			
			int readed = 0;
			int pos = 0;
			byte bytes[] = new byte[1024];
			
			while ( (readed = inStream.read(bytes, pos, 1024)) > 0) {
				fos.write(bytes, pos, readed);
			}
			
			fos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		finally {
			StateService.getInstance().setBinaryURL(null);
			StateService.getInstance().setJobURL(null);
			StateService.getInstance().setProfile(null);
		}
	}
	
	private String getFileName() {
		String filename = "";
		StateService service = StateService.getInstance();
		String url = service.getBinaryURL();
		String ext = getProfileExt();
		url = url.replaceAll("/", "_");
		
		filename = url + "." + ext;
		
		return filename;
	}
	
	private String getProfileExt() {
		String profiles = SettingsProvider.getSettings().getProfilesPath();
		File file = new File(profiles + StateService.getInstance().getProfile() + ".xml");
		try {
			FileInputStream buf = new FileInputStream(file);
			Document doc = getXMLDocument(buf);
			String format = getXMLParam(doc, "Format", "http://www.hp.com/schemas/imaging/con/cnx/scan/2008/08/19");
			buf.close();
			if (format != null) {
				if (format.equalsIgnoreCase("Jpeg"))
					return "jpg";
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		}
		return "";
	}
}
