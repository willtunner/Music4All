package com.music4all.Music4All.controllers;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.music4all.Music4All.services.implementations.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        // Define a data de expiração para o link prescrito
        Date expiration = new Date(System.currentTimeMillis() + 3600000); // Expira em 1 hora
        List<String> urlSongs = new ArrayList<>();
        List<String> fileNames = storageService.getSongFileNames();
        List<ObjectIndex> objectIndexList = new ArrayList<>();

        fileNames.forEach(s -> {
            // Cria uma solicitação para gerar o URL prescrito
          GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest("musicarchives", s)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration);
            // Gera o URL prescrito
            URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            System.out.println(presignedUrl);
            urlSongs.add(presignedUrl.toString());
        });

        if (fileNames.size() == urlSongs.size()) {
            for (int i =0; i < fileNames.size(); i++) {
                ObjectIndex objectIndex = new ObjectIndex();
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

    @PostMapping
    public String handleFileUpload(@RequestParam("file")MultipartFile file) {
        storageService.uploadFile(file);
        return "redirect:/home";
    }

}



class ObjectIndex {
    private String fileName;
    private String urlSong;

    // Getters e setters

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrlSong() {
        return urlSong;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }
}
