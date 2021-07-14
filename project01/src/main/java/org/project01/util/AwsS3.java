package org.project01.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.project01.domain.DataVO;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


@Service
public class AwsS3 {
	
	//private final AmazonS3Client amazonS3Client;
	private AmazonS3 s3Client;
    private static String accessKey = "";
    private static String secretKey = "";
    private Regions clientRegion = Regions.AP_NORTHEAST_2;
    final private String bucket = "myboardbox";
   
    private AwsS3() {
    	createS3Client();
    }
    static private AwsS3 instance = null;
    
    public static AwsS3 getInstance() {
        if (instance == null) {
            return new AwsS3();
        } else {
            return instance;
        }
    }
    
    //aws S3 client 생성
    private void createS3Client() {
        //AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new ClasspathPropertiesFileCredentialsProvider())
                //.withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(clientRegion)
                .build();
    }
    //s3에 파일 저장
    public void uploadMeta(MultipartFile mfile,String uploadFolderPath,String uploadFileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(mfile.getContentType());
        objectMetadata.setContentLength(mfile.getSize());  
        try (InputStream inputStream = mfile.getInputStream()) {
        	s3Client.putObject(new PutObjectRequest(this.bucket+uploadFolderPath, uploadFileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다"));
        }
    }
    //파일 URL
    public String getFileURL(String path, String fileName) {
	    System.out.println("넘어오는 파일명 : "+fileName);

	    // set expiration
	    Date expiration = new Date();
	    long expTimeMillis = expiration.getTime();
	    expTimeMillis += 1000 * 60 * 60; // 1 hour
	    expiration.setTime(expTimeMillis);

	    // Generate the presigned URL.
	    GeneratePresignedUrlRequest generatePresignedUrlRequest =
	    	new GeneratePresignedUrlRequest(this.bucket+path, (fileName).replace(File.separatorChar, '/'))
	        .withMethod(HttpMethod.GET)
	        .withExpiration(expiration);
	    System.out.println(generatePresignedUrlRequest.toString());
	    return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
    //파일삭제
    public void delete(String path,String key) {
        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket+path, key);
            //Delete
            this.s3Client.deleteObject(deleteObjectRequest);
            System.out.println(String.format("[%s] deletion complete", key));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
    //파일리스트
    public List<String> list(String path) {
    	List<String> awsList =new ArrayList<>();		
		ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(this.bucket).withMaxKeys(2);
        ListObjectsV2Result result = null;
    	do {
    		result = s3Client.listObjectsV2(req);
    		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) { 
    			String key=objectSummary.getKey();
    			key=key.substring(key.lastIndexOf("/")+1);
    			awsList.add(key);
    		}
    		String token = result.getNextContinuationToken();
    		req.setContinuationToken(token);
    	}while(result.isTruncated());
    	return awsList;
    }
}
