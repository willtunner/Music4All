package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.BandRepository;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.BandServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BandServiceImpl implements BandServiceInterface {

    private final BandRepository bandRepository;
    private final UserRepository userRepository;

    @Value("${contato.disco.raiz}")
    private String raiz;

    @Value("${contato.disco.diretorio-fotos}")
    private String diretoriosFotos;


    @Override
    public Band saveBand(Band band, MultipartFile file) throws MessagingException, IOException {
//        Path diretorioPath = Paths.get(this.raiz, this.diretoriosFotos);
//        Path arquivoPath = diretorioPath.resolve(file.getOriginalFilename());

//        try {
//            Files.createDirectories(diretorioPath);
//            file.transferTo(arquivoPath.toFile());
//        } catch (IOException e) {
//            throw new RuntimeException("Problemas na tentativa de salvar arquivo");
//        }
        return bandRepository.save(band);

    }

    @Override
    public Band getBand(String bandName) {
        return null;
    }

    @Override
    public Band addMember(Long bandId, Long memberId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User member = userRepository.findById(memberId).orElse(null);
        band.addMembers(member);
        return bandRepository.save(band);
    }

    @Override
    public Band updateBand(Long id, List<User> musics) {
//        Band band = bandRepository.findById(id).orElse(null);

//        if (!musics.isEmpty()) {
//            if (band != null) {
//                band.setMusics(musics);
//            }
//        }

        return null;
    }

    @Override
    public List<Band> getBandByName(String name) {
        if (name != null) {
            System.out.println("Nome da banda: " + name);
            List<Band> band = bandRepository.findByName(name);
            log.info("Saving new music {} to the database");
            return band;
        }

        return null;
    }

    @Override
    public List<Band> getBandByState(String state) {
        if (state != null) {
            System.out.println("Estado da banda: " + state);
            List<Band> band = bandRepository.findBandByState(state);
            log.info("Listando Bandas por Estado");
            return band;
        }

        return null;
    }

    @Override
    public List<Band> getBandByStateByLimit(String state) {
        return bandRepository.findTop5ByStateOrderByLikeAsc(state);
    }

    @Override
    public Boolean deleteBand(Long idBand) {
        log.info( "Delete Band by id: ", idBand );
        bandRepository.deleteById(idBand);
        return true;
    }

    @Override
    public List<Band> getBands() {
        log.info("List all bands ");
        return bandRepository.findAll();
    }

    @Override
    public Optional<Band> getBandById(Long idBand) {
        return bandRepository.findById(idBand);
    }

    @Override
    public Band updateBand(Band band) {
        if ( band.getId() != null) {
            log.info("Band {} updated success", band.getName());
            return bandRepository.save(band);
        }
        log.info("Band DON'T update");
        return band;
    }
}
