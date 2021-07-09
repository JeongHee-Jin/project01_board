
package org.project01.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.project01.domain.BoardAttachVO;
import org.project01.persistence.UploadFileDAO;
import org.project01.util.AwsS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.IOUtils;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Inject
	private UploadFileDAO fileDAO;
	
	private AwsS3 awsS3=AwsS3.getInstance();
	
	//폴더경로생성
	private String getFolder() {
		//"yyyy-mm-dd" 년월일
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String str = "/"+sdf.format(date);
		return str.replace("-", File.separator);
	}	
	//"text/plain;charset=UTF-8"
	//파일등록 : ajax로 파일 경로생성 및 파일 저장 , DB접속 X
	@PostMapping(value = "/uploadAws",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> awsS3Upload(MultipartFile[] uploadFile) 
			throws IOException {
		List<BoardAttachVO> list = new ArrayList<BoardAttachVO>();
		
		//저장할 폴더 경로
		try {
			String uploadFolderPath = getFolder();
			for (MultipartFile multipartFile : uploadFile) {
				BoardAttachVO attachDTO = new BoardAttachVO();
				//폴더
				attachDTO.setUploadPath(uploadFolderPath);
				//파일이름
				String uploadFileName = multipartFile.getOriginalFilename();
				
				uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
				attachDTO.setFileName(uploadFileName);
				//uuid 넣기
				UUID uuid = UUID.randomUUID();
				attachDTO.setFileId(uuid.toString());	
				uploadFileName = uuid.toString() + "_" + uploadFileName;				
				awsS3.uploadMeta(multipartFile, uploadFolderPath,uploadFileName);
				list.add(attachDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	//첨부파일 삭제(글쓰기에서만)
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String targetFile,String path) 
			throws UnsupportedEncodingException {
		logger.info("파일삭제");
		awsS3.delete(path,targetFile);
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	//첨부파일 불러오기 : 게시물 번호를 받아 첨부파일 데이터를 json으로 반환하도록 처리
	@GetMapping("/getAttachList")
	public @ResponseBody ResponseEntity<List<BoardAttachVO>> loadFile(String nav,String postId) 
			throws Exception{
		logger.info("loadFile");
		Map<String,Object> map=new HashMap<String,Object>();
		List<BoardAttachVO> list = new ArrayList<>();
		try {
			map.put("nav",nav);
			map.put("postId",postId);		
			list=fileDAO.getAttachList(map);
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	//첨부파일 다운로드
	@GetMapping(value="/download" ,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody ResponseEntity<byte[]> getFile(@RequestHeader(value="User-Agent")String userAgent,
			String path,String fileName){
		logger.info("fileName: "+ fileName);
		ResponseEntity<byte[]> result = null;
		  try {
			  HttpHeaders header = new HttpHeaders();
			  URL url = new URL(awsS3.getFileURL(path,fileName));
			  HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			  InputStream fileIS = urlConn.getInputStream();
			  String rscOriginalName = fileName.substring(fileName.indexOf("_")+1);
			  String downloadName = null;
			  if(userAgent.contains("Trident")) {
					logger.info("IE browser");
					downloadName = URLEncoder.encode(rscOriginalName, "UTF-8").replaceAll("\\+", " ");
					logger.info("Edge name: " + downloadName);
				}else if(userAgent.contains("Edge")) {
					logger.info("Edge browser");
					downloadName = new String(rscOriginalName.getBytes("UTF-8"));
				}else {
					logger.info("Chrome browser");
					downloadName = new String(rscOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				}		  
			header.add("Content-Disposition", "attachment; filename=" + downloadName);
		    header.add("Content-Type", URLConnection.guessContentTypeFromStream(fileIS));
		    result = new ResponseEntity<>(IOUtils.toByteArray(fileIS), header, HttpStatus.OK);
		  
		  } catch(IOException e) {
			 logger.info("file path error");
		  }
		  return result;
	}
	
	//@ResponseBody 처리후 페이지 이동안함
	//json,xml 메세지 전송
	//produces="" 에 뭔가 할지가 써있음
	//MediaType 전송하는 데이터타입
	//APPLICATION_OCTET_STREAM_VALUE 파일내용 전송
	//내 컴퓨터 c드라이브에 저장
	//파일등록 : ajax로 파일 경로생성 및 파일 저장 , DB접속 X
//	@PostMapping(value = "/uploadAjaxAction",produces = "MediaType.APPLICATION_JSON_UTF8_VALUE")
//	@ResponseBody
//	public ResponseEntity<List<BoardAttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) throws IOException {
//		List<BoardAttachVO> list = new ArrayList<>();
//		BoardAttachVO attachDTO = new BoardAttachVO();
//		logger.info("파일 등록 ");
//		//저장할 폴더 경로
//		String uploadFolder = "C:\\Users\\jh\\Desktop\\project01\\uplode";
//		String uploadFolderPath = getFolder();
//		//폴더만들기
//		File uploadPath = new File(uploadFolder, uploadFolderPath);
//		if (uploadPath.exists() == false) {
//			uploadPath.mkdirs();
//		}
//		for (MultipartFile multipartFile : uploadFile) {
//			
//			String uploadFileName = multipartFile.getOriginalFilename();
//			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
//			attachDTO.setFileName(uploadFileName);
//			//uuid+파일이름
//			UUID uuid = UUID.randomUUID();
//			uploadFileName = uuid.toString() + "_" + uploadFileName;
//				
//			try {
//				//경로 및 파일이름
//				File saveFile = new File(uploadPath, uploadFileName);
//				multipartFile.transferTo(saveFile);
//					
//				//파일이름넣기
//				attachDTO.setFileId(uuid.toString());
//				attachDTO.setUploadPath(uploadFolderPath);
//
//				list.add(attachDTO);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
	//첨부파일 다운로드
//	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	@ResponseBody
//	public ResponseEntity<Resource> downloadFile(@RequestHeader(value="User-Agent") 
//		String userAgent, String fileName) throws UnsupportedEncodingException{
//		logger.info("download file : "+fileName);
//		//파일경로+이름
//		Resource rsc = new FileSystemResource("C:\\Users\\jh\\Desktop\\project01\\uplode\\" + fileName);
//		System.out.println("rsc : "+rsc);
//		//경로에 파일존재여부
//		if(rsc.exists() == false) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		String rscName = rsc.getFilename();	//파일이름
//		System.out.println("rscName : "+rscName);
//		//UUID 제거한 파일 순수 이름
//		String rscOriginalName = rscName.substring(rscName.indexOf("_")+1);
//		System.out.println("rscOriginalName : "+rscOriginalName);
//		HttpHeaders headers = new HttpHeaders();
//		try { //브라우저별 한글 인코딩
//			String downloadName = null;
//			if(userAgent.contains("Trident")) {
//				logger.info("IE browser");
//				downloadName = URLEncoder.encode(rscOriginalName, "UTF-8").replaceAll("\\+", " ");
//				logger.info("Edge name: " + downloadName);
//			}else if(userAgent.contains("Edge")) {
//				logger.info("Edge browser");
//				downloadName = new String(rscOriginalName.getBytes("UTF-8"));
//			}else {
//				logger.info("Chrome browser");
//				downloadName = new String(rscOriginalName.getBytes("UTF-8"), "ISO-8859-1");
//				System.out.println("downloadName : "+downloadName);
//			}
//			//다운로드시 저장되는 이름
//			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
//			System.out.println("headers : "+headers);
//		}catch(UnsupportedEncodingException e){
//			e.printStackTrace();
//		}		
//		System.out.println("headers : "+headers);
//		return new ResponseEntity<Resource>(rsc, headers, HttpStatus.OK);
//	}
//		
//	
//	//첨부파일 삭제(글쓰기에서만)
//	@PostMapping("/deleteFile")
//	@ResponseBody
//	public ResponseEntity<String> deleteFile(String targetFile) {
//		logger.info("파일삭제");
//		File file;
//		try {
//			file = new File("C:\\Users\\jh\\Desktop\\project01\\uplode\\" 
//					+ URLDecoder.decode(targetFile, "UTF-8"));
//			file.delete();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<String>("deleted", HttpStatus.OK);
//	}	
}
