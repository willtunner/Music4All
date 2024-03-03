package com.music4all.Music4All.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.music4all.Music4All.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) {
        // Verifica se o tipo de conteúdo do arquivo é de áudio
        if (!file.getContentType().startsWith("audio/")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "O arquivo fornecido não é um tipo de áudio suportado.");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File fileObject = convertMultiPartFileToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName,fileObject ));
        fileObject.delete();
        return "File uploaded : " + fileName;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }

    public void returnFileNames() {
        ListObjectsV2Result result = s3Client.listObjectsV2("musicarchives");
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        objects.stream().forEach(s3ObjectSummary -> {
            System.out.println(s3ObjectSummary.toString());
        });
    }



    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }


    public User saveImageUser(MultipartFile file, User user, String bucketName) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File fileObject = convertMultiPartFileToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
        String urlImage = getFileUrl(fileName, bucketName);
        user.setUrlImageProfile(urlImage);
        System.out.println("URL: " + urlImage);
        fileObject.delete();
        return user;
    }

    private String getFileUrl(String fileName, String bucketName) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }


    public List<String> getSongFileNames() {
        ListObjectsV2Result result = s3Client.listObjectsV2("musicarchives");
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        return objects.stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    public void uploadSon(MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        s3Client.putObject(new PutObjectRequest("musicarchives", file.getOriginalFilename(),
                file.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public List<String> testeFindByBandUserBucketS3(MultipartFile file, String bucketName) {
        // Verifica se o tipo de conteúdo do arquivo é de áudio
        if (!file.getContentType().startsWith("audio/")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "O arquivo fornecido não é um tipo de áudio suportado.");
        }

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName);

        List<String> urls = new ArrayList<>();

        ListObjectsV2Result result;
        do {
            result = s3Client.listObjectsV2(request);
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String key = objectSummary.getKey();
                if (key.contains("Metallica")) {
                    String url = s3Client.getUrl(bucketName, key).toString();
                    urls.add(url);
                }
            }
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        //return urls;
//        File fileObject = convertMultiPartFileToFile(file);
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName,fileObject ));
//        fileObject.delete();
        return urls;
    }
}
