package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.Banner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BannerRepository extends CrudRepository<Banner, Long> {

    List<Banner> findAll();
    Banner findBannerById(Long id);
    Banner findBannerByFilename(String filename);

}
