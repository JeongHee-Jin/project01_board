package org.project01.domain;

public class BoardAttachVO {
	
	private String fileId;		//파일번호
	private String uploadPath;	//파일날짜
	private String fileName;	//파일이름
	
	private int nav;		//게시판키
	private int postId;			//게시물번호
	
	public BoardAttachVO() {}

	public BoardAttachVO(String fileId, String uploadPath, String fileName, int nav, int postId) {
		super();
		this.fileId = fileId;
		this.uploadPath = uploadPath;
		this.fileName = fileName;
		this.nav = nav;
		this.postId = postId;
	}



	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getNav() {
		return nav;
	}

	public void setNav(int nav) {
		this.nav = nav;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "BoardAttachVO [fileId=" + fileId + ", uploadPath=" + uploadPath + ", fileName=" + fileName
				+ ", nav=" + nav + ", postId=" + postId + "]";
	}

}
