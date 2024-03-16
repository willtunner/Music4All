package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.repositoriees.BandRepository;
import com.music4all.Music4All.repositoriees.DiscRepository;
import com.music4all.Music4All.services.DiscServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiscServiceImpl implements DiscServiceInterface {

    private final DiscRepository discRepository;
    private final BandRepository bandRepository;
    private final StorageService storageService;
    String bucketName = "disc-images-band";

    @Override
    public Disc createDisc(Disc disc, MultipartFile file) throws MessagingException {
        log.info("Saving new Disc '{}' to the database", disc.getName());

        Optional<Band> bandOptional = bandRepository.findById(disc.getBandId());

        if (bandOptional.isPresent()) {
            Band band = bandOptional.get();

            String urlImage = storageService.saveImageS3(bucketName, file);
            if (urlImage != null) disc.setDiscImageUrl(urlImage);

            Disc discSaved = discRepository.save(disc);
            band.addDisc(discSaved);
            log.info("Band {} saved success", band.getName());
            bandRepository.save(band);
            return discSaved;
        } else {
            log.info("Band not found");
            throw new RuntimeException("Band not found");
        }

    }

    @Override
    public Disc getDisc(String discName) {
        return discRepository.findByName(discName);
    }

    @Override
    public Boolean deleteDisc(Long idDisc) {
        log.info( "Delete Disc by id: {}", idDisc );
        discRepository.deleteById(idDisc);
        return true;
    }

    @Override
    public List<Disc> getDiscsByBand(Long id) {
        return discRepository.findByBandId(id);
    }

    @Override
    public List<Disc> getDisc() {
        log.info("List all discs ");
        return discRepository.findAll();
    }

    @Override
    public Optional<Disc> getDiscById(Long idDisc) {
        return discRepository.findById(idDisc);
    }

    @Override
    public Disc updateBand(Disc disc) {
        if ( disc.getId() != null) {
            log.info("User {} saved success", disc.getName());
            return discRepository.save(disc);
        }
        log.info("Disc DON'T update");
        return disc;
    }
}
