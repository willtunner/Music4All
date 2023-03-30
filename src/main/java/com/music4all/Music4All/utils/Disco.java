package com.music4all.Music4All.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Disco {

    @Value("${contato.disco.raiz}")
    private String raiz;

    @Value("${contato.disco.diretorio-fotos}")
    private String diretoriosFotos;

    public void saveImage(MultipartFile foto) {
        this.save(this.diretoriosFotos, foto);
    }

    public void save(String diretorio, MultipartFile file) {
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(file.getOriginalFilename());

        try {
            Files.createDirectories(diretorioPath);
            file.transferTo(arquivoPath.toFile());
        } catch (IOException e) {
                throw new RuntimeException("Problemas na tentativa de salvar arquivo");
        }
    }
}
