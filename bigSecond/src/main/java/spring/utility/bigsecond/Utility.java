package spring.utility.bigsecond;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Utility {
	
	/** 
	   * 직업 코드를 받아서 해당하는 값(레이블)을 리턴 
	   * @param key 
	   * @return 
	   */ 
	  public static String getCodeValue(String key) { 
	    Hashtable codes = new Hashtable(); 

	    codes.put("A01", "회사원"); 
	    codes.put("A03", "전산관련직"); 
	    codes.put("A05", "연구전문직"); 
	    codes.put("A07", "각종학교학생"); 
	    codes.put("A09", "일반자영업"); 
	    codes.put("A11", "공무원"); 
	    codes.put("A13", "의료인"); 
	    codes.put("A15", "법조인"); 
	    codes.put("A17", "종교.언론/예술인"); 
	    codes.put("A19", "농업"); 
	    codes.put("A23", "축산업"); 
	    codes.put("A25", "수산업"); 
	    codes.put("A27", "광업"); 
	    codes.put("A30", "주부"); 
	    codes.put("A32", "무직"); 
	    codes.put("A99", "기타"); 
	     
	    Object value = codes.get(key); // A01 ~ A99가 추출 

	    return (String)(value); // 코드값에 해당하는 직업 리턴 
	  } 
	
	public static void deleteFile(String upDir, String oldfile){
		File f = new File(upDir, oldfile);
		
		if(f.exists()){
			
			f.delete();
		}
	}
	
	
	/**
     * 오늘,어제,그제 날짜 가져오기
     * @return List- 날짜들 저장
     */
    public static List<String> getDay(){
        List<String> list = new ArrayList<String>();
        
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        for(int j = 0; j < 3; j++){
            list.add(sd.format(cal.getTime()));
            cal.add(Calendar.DATE, -1);
        }
        
        return list;
    }
    /**
     * 등록날짜와 오늘,어제,그제날짜와 비교
     * @param wdate - 등록날짜
     * @return - true:오늘,어제,그제중 등록날짜와 같음
     *           false:오늘,어제,그제 날짜가 등록날짜와 다 다름
     */
    public static boolean compareDay(String wdate){
        boolean flag = false;
        List<String> list = getDay();
        if(wdate.equals(list.get(0)) 
           || wdate.equals(list.get(1))
           || wdate.equals(list.get(2))){
            flag = true;
        }
          
        return flag;
    }
	
	  public static String checkNull(String str){//null이오면 빈 문자로 변환
	    String _str;  // 지역 변수
	    
	    if (str == null){
	      _str = "";  // null이면 공백을 할당 
	    }else{
	      _str = str; // null이 아니면 원본 문자열 할당
	    }
	    
	    return _str;
	  }
	  /** 
	     * 파일업로드 처리(model1,mvc) 
	     * @param fileItem 
	     * @param upDir 
	     * @return 
	     */ 
	    public static String saveFileSpring30(MultipartFile multipartFile, String basePath) { 
	        // input form's parameter name 
	        String fileName = ""; 
	        // original file name 
	        String originalFileName = multipartFile.getOriginalFilename(); 
	        // file content type 
	        String contentType = multipartFile.getContentType(); 
	        // file size 
	        long fileSize = multipartFile.getSize(); 
	         
	        System.out.println("fileSize: " + fileSize); 
	        System.out.println("originalFileName: " + originalFileName); 
	         
	        InputStream inputStream = null; 
	        OutputStream outputStream = null; 
	 
	        try { 
	            if( fileSize > 0 ) { // 파일이 존재한다면 
	                // 인풋 스트림을 얻는다. 
	                inputStream = multipartFile.getInputStream(); 
	 
	                File oldfile = new File(basePath, originalFileName); 
	             
	                if ( oldfile.exists()){ 
	                    for(int k=0; true; k++){ 
	                        //파일명 중복을 피하기 위한 일련 번호를 생성하여 
	                        //파일명으로 조합 
	                        oldfile = new File(basePath,"("+k+")"+originalFileName); 
	                     
	                        //조합된 파일명이 존재하지 않는다면, 일련번호가 
	                        //붙은 파일명 다시 생성 
	                        if(!oldfile.exists()){ //존재하지 않는 경우 
	                            fileName = "("+k+")"+originalFileName; 
	                            break; 
	                        } 
	                    } 
	                }else{ 
	                    fileName = originalFileName; 
	                } 
	                //make server full path to save 
	                String serverFullPath = basePath + "\\" + fileName; 
	                 
	                System.out.println("fileName: " + fileName); 
	                System.out.println("serverFullPath: " + serverFullPath); 
	                 
	                outputStream = new FileOutputStream( serverFullPath ); 
	  
	                // 버퍼를 만든다. 
	                int readBytes = 0; 
	                byte[] buffer = new byte[8192]; 
	 
	                while((readBytes = inputStream.read(buffer, 0, 8192)) != -1 ) { 
	                    outputStream.write( buffer, 0, readBytes ); 
	                } 
	                outputStream.close(); 
	                inputStream.close(); 
	                         
	            } 
	 
	        } catch(Exception e) { 
	            e.printStackTrace();   
	        }finally{ 
	             
	        } 
	         
	        return fileName; 
	    } 
	}
