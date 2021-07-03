package org.project01.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.project01.domain.BoardAttachVO;
import org.project01.domain.MemberVO;
import org.project01.persistence.AdminDAO;
import org.project01.persistence.UploadFileDAO;
import org.springframework.scheduling.annotation.Scheduled;

public class AutoDelete {

	@Inject
	private UploadFileDAO fileDAO;
	@Inject
	private AdminDAO adminDAO;
	
	private AwsS3 awsS3=AwsS3.getInstance();
	
	//탈퇴한 회원 7일후 자동 삭제, 자동 삭제 매일 0시10분
	@Scheduled(cron="0 10 00 * * ?")
	public void autoDeleteUser() throws Exception {	
		System.out.println("탈퇴 유저 자동 삭제>>>>>>>>>>>>>>>>>");
		Calendar cal=Calendar.getInstance();	
		cal.setTime(new Date()); //현재날짜저장
		
		List<MemberVO> memList=adminDAO.levMemList(); //탈퇴멤버
		
		Calendar levCal=Calendar.getInstance();			

		memList.forEach(mem->{
			try {
				levCal.setTime(mem.getLevDate());
				//현재시간 - 탈퇴시간
				long sec=cal.getTimeInMillis()-levCal.getTimeInMillis();
				int days=(int)sec/(24*60*60*1000);
				if(days>=0) {
					adminDAO.deleteMem(mem.getUserId());					
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		});
		System.out.println("탈퇴 유저 자동 삭제 완료");

	}
	//폴더경로생성
	private String getFolder() {
		//"yyyy-MM-dd" 년월일
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String str = "/"+sdf.format(date);
		return str.replace("-", File.separator);
	}
	// DB에 없는 첨부 파일 매일 23시 59분 자동 삭제(aws s3)
	@Scheduled(cron="0 59 23 * * ?")
	public void autoDeleteFiles() throws Exception {
		System.out.println("파일 삭제 시작>>>>>>>>>>>>>>>>>>");
		String path=getFolder();
		List<String> fileList=new ArrayList<>();
		List<String> awsList=new ArrayList<>();	
		boolean bln =false;	
		try {
			//DB 파일정보 가져오기
			List<BoardAttachVO> dbList= fileDAO.allFiles();
			//aws s3 버킷리스트 가져오기
			awsList=awsS3.list(path);			
			dbList.forEach(attach->{
				fileList.add(attach.getFileId()+ "_" +attach.getFileName());				
			});
			
			for(String s3Name:awsList) {
				for(String dbName : fileList) {
					if(dbName.equals(s3Name)){
						System.out.println(s3Name+":"+dbName);
						bln=false;
						break;
					}else {
						System.out.println(s3Name+":"+dbName);
						bln=true;
					}					
				}
				if(bln) {
					awsS3.delete(path, s3Name);
				}	
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("파일 삭제 끝>>>>>>>>>>>>>>>>>>");
	}
	
//	// DB에 없는 첨부 파일 매일 02시 자동 삭제
//	@Scheduled(cron="0 0 02 * * ?")
//	public void autoDeleteFiles() throws Exception {
//		logger.info("파일 삭제>>>>>>>>>>>>>>>>>>");
//		try {
//			//DB 파일정보 가져오기
//			List<BoardAttachVO> fileList= fileDAO.allFiles();
//			ArrayList<String> listY=new ArrayList<String>();
//			// 디렉토리 경로 설정(어제날짜)
//			File targetDir = Paths.get("C:\\Users\\jh\\Desktop\\project01\\uplode\\",
//					getFolderYesterDay()).toFile();			
//			fileList.forEach(attach->{			
//				listY.add(attach.getFileId()+ "_" +attach.getFileName());				
//			});
//			// 하위의 모든 파일 이름
//			for(File info : FileUtils.listFiles(
//					new File("C:\\Users\\jh\\Desktop\\project01\\uplode\\"),
//					TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {				
//				if(!listY.contains(info.getName())) {
//					File removeFiles= new File(targetDir+"\\"+info.getName());
//					removeFiles.delete();
//				}
//			}
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	//어제날짜
//	private String getFolderYesterDay() {
//		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -1);
//		String str = date.format(cal.getTime());
//		return str.replace("-", File.separator);
//	}
}
