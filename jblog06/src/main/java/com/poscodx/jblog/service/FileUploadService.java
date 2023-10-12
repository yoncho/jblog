package com.poscodx.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private static String SAVE_PATH="/mysite-uploads";
	private static String URL_PATH="/assets/upload-images";
	
	public String restore(MultipartFile file) {
		String url = null;

		try {
			//1, file 체크
			if(file.isEmpty()) {
				return url;
			}
			
			//2, Local 저장소 (폴더) 생성 (이미 존재하는지 확인)
			File uploadDirectory = new File(SAVE_PATH); 
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdir();
			}
			
			//3, File 원본 이름, 확장자, 저장할 이름 
			String originFileName = file.getOriginalFilename();
			String extention = originFileName.substring(originFileName.lastIndexOf(".") + 1);
			String saveFileName = generateSaveFilename(extention);
			
			byte[] date = file.getBytes();
			
			//4, Local 저장소에 저장
			OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFileName);
			os.write(date);
			os.close();
			
			//5, url 생성
			url = URL_PATH +"/" +saveFileName;
			
		}catch(Exception e) {
			throw new RuntimeException("FileUploadService " + e.toString());
		}
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
