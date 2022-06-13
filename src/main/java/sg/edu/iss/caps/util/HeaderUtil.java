package sg.edu.iss.caps.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import lombok.Data;

@Data
public class HeaderUtil {
	
	private List<Header> headerList;
	private String name;
	private static Map<String, String> headermap;
	static {
		headermap = new HashMap<String, String>();
		headermap.put("Home", "/home");
		headermap.put("Course", "/course");
		headermap.put("Student", "/student");
	}
	
	
	public HeaderUtil() {
		headerList = new ArrayList<>();
		name = null;
	}
	
	public void addToHeaderList(Header header) {
		headerList.add(header);
	}
	
	public static void setHeader(Model model, String[] header, String name) {
		//This method builds the headerbar to be used in the view
		
		HeaderUtil headerbar = new HeaderUtil();
		
		for(int i = 0; i<header.length; i++) {
			String path = headermap.get(header[i]);
			if(path != null) {
				headerbar.addToHeaderList(new Header(path, header[i]));
			}
			else {
				//Temp for troubleshooting if link does not exist
				System.out.println("WARNING: No link found for ---> " + header[i]);
				StackTraceElement[] trace= Thread.currentThread().getStackTrace();
				System.out.println("\t Class: " + trace[1].getClassName() + ",  Method: " + trace[1].getMethodName());
				System.out.println("\t Class: " + trace[2].getClassName() + ",  Method: " + trace[2].getMethodName());
			}
		}
		headerbar.setName(name);
		
		//Storing as headerbar
		model.addAttribute("headerbar",headerbar);
	}
	
	
}
