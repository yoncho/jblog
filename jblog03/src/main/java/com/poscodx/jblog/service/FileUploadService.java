package com.poscodx.jblog.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	public String restore(MultipartFile file) {
		String url =null;
		
		
		
		return url;
	}
	
	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename+=calendar.get(Calendar.YEAR);
		filename+=calendar.get(Calendar.MONTH);
		filename+=calendar.get(Calendar.DATE);
		filename+=calendar.get(Calendar.HOUR);
		filename+=calendar.get(Calendar.MINUTE);
		filename+=calendar.get(Calendar.SECOND);
		filename+=calendar.get(Calendar.MILLISECOND);
		filename+=("." + extName);
		
		return filename;
	}
}
