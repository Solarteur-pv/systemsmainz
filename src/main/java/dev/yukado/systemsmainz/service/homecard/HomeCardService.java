package dev.yukado.systemsmainz.service.homecard;

import dev.yukado.systemsmainz.dto.HomeCardDto;
import dev.yukado.systemsmainz.entity.HomeCard;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeCardService {
    HomeCard save(HomeCardDto dto, MultipartFile file);


    void deleteById(Long id);

    HomeCard findById(Long id);

    List<HomeCard> findAll();

    HomeCard update(HomeCardDto dto, MultipartFile file);
}
