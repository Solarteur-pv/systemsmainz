package dev.yukado.systemsmainz.service.homecard;

import dev.yukado.systemsmainz.dto.HomeCardDto;
import dev.yukado.systemsmainz.entity.HomeCard;
import dev.yukado.systemsmainz.repository.HomeCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeCardServiceImpl implements HomeCardService {

    @Autowired
    private HomeCardRepository homeCardRepo;

    @Override
    public HomeCard save(HomeCardDto dto, MultipartFile file) {

        HomeCard homeCard = new HomeCard();
        homeCard.setFilename(dto.getFilename());
        homeCard.setTitle(dto.getTitle());
        homeCard.setHomeCardText(dto.getHomeCardText());
        homeCard.setActive(dto.getActive());
        homeCard.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        homeCard.setContentType(dto.getContentType());

        if (!file.isEmpty()) {
            try {
                homeCard.setData(file.getBytes());
                homeCard.setContentType(file.getContentType());
                homeCard.setFilename(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Lesen der Datei", e);
            }
        }

        return homeCardRepo.save(homeCard);
    }

    @Override
    public HomeCard update(HomeCardDto dto, MultipartFile file) {

        HomeCard homeCard = homeCardRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        homeCard.setFilename(dto.getFilename());
        homeCard.setTitle(dto.getTitle());
        homeCard.setHomeCardText(dto.getHomeCardText());
        homeCard.setActive(dto.getActive());
        homeCard.setContentType(dto.getContentType());
        homeCard.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        if (!file.isEmpty()) {
            try {
                homeCard.setData(file.getBytes());
                homeCard.setContentType(file.getContentType());
                homeCard.setFilename(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Lesen der Datei", e);
            }
        }

        return homeCardRepo.save(homeCard);
    }

    @Override
    public void deleteById(Long id) {
        homeCardRepo.deleteById(id);
    }

    @Override
    public HomeCard findById(Long id) {
        return homeCardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    @Override
    public List<HomeCard> findAll() {
        return homeCardRepo.findAll();
    }

}


