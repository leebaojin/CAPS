package sg.edu.iss.caps.util;

import lombok.Data;

@Data
public class Header {
	private String path;
	private String text;
	
	public Header(String path, String text) {
		this.path = path;
		this.text = text;
	}
}
