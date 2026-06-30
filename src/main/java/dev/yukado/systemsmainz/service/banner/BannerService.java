package dev.yukado.systemsmainz.service.banner;

import dev.yukado.systemsmainz.dto.BannerDto;
import dev.yukado.systemsmainz.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {

    Banner save(BannerDto dto, MultipartFile file);


    void deleteById(Long id);

    Banner findById(Long id);

    List<Banner> findAll();

    void delete(Long id);

    Banner update(BannerDto dto, MultipartFile file);
}
