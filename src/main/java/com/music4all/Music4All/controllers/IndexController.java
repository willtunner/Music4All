package com.music4all.Music4All.controllers;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.music4all.Music4All.services.implementations.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    private final StorageService storageService;
    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    public IndexController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("home")
    public String getHomepage(Model model) {
        int oneHourExpiration = 3600000;
        Date expiration = new Date(System.currentTimeMillis() + oneHourExpiration);
        List<String> urlSongs = new ArrayList<>();
        List<String> fileNames = storageService.getSongFileNames();
        List<ObjectIndex> objectIndexList = new ArrayList<>();

        fileNames.forEach(s -> {
          GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest("musicarchives", s)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration);
            URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            System.out.println(presignedUrl);
            urlSongs.add(presignedUrl.toString());
        });

        if (fileNames.size() == urlSongs.size()) {
            for (int i =0; i < fileNames.size(); i++) {
                ObjectIndex objectIndex = new ObjectIndex();
                String nameRefactor = fileNames.get(i).substring(fileNames.get(i).indexOf("_") + 1, fileNames.get(i).lastIndexOf("."));
                objectIndex.setFileNameRefactor(nameRefactor);
                objectIndex.setFileName(fileNames.get(i));
                objectIndex.setUrlSong(urlSongs.get(i));
                objectIndexList.add(objectIndex);
            }
        }
        model.addAttribute("objectIndexList", objectIndexList);
        return "home";
    }

    @GetMapping("login")
    public String loginPage() {

        return "login";
    }

    @PostMapping("home")
    public String handleFileUpload(@RequestParam("file")MultipartFile file) {
        storageService.uploadFile(file);
        return "redirect:/home";
    }

    @PostMapping("/home/delete")
    public String deleteFileHome(@RequestParam("delete") String fileName) {
        if (fileName != null && !fileName.isEmpty()) storageService.deleteFile(fileName);
        return "redirect:/home";
    }

}

class ObjectIndex {
    private String fileName;

    private String fileNameRefactor;
    private String urlSong;

    public void setFileNameRefactor(String fileNameRefactor) {
        this.fileNameRefactor = fileNameRefactor;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }
}
