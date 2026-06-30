package dev.yukado.systemsmainz.service.banner;

import dev.yukado.systemsmainz.dto.BannerDto;
import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepo;

    @Override
    public Banner save(BannerDto dto, MultipartFile file) {

        Banner banner = new Banner();
        banner.setFilename(dto.getFilename());
        banner.setBannerText(dto.getBannerText());
        banner.setActive(dto.getActive());
        banner.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        banner.setContentType(dto.getContentType());

        if (!file.isEmpty()) {
            try {
                banner.setData(file.getBytes());
                banner.setContentType(file.getContentType());
                banner.setFilename(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Lesen der Datei", e);
            }
        }

        return bannerRepo.save(banner);
    }

    @Override
    public Banner update(BannerDto dto, MultipartFile file) {

        Banner banner = bannerRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        banner.setFilename(dto.getFilename());
        banner.setBannerText(dto.getBannerText());
        banner.setActive(dto.getActive());
        banner.setContentType(dto.getContentType());

        if (!file.isEmpty()) {
            try {
                banner.setData(file.getBytes());
                banner.setContentType(file.getContentType());
                banner.setFilename(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Lesen der Datei", e);
            }
        }

        return bannerRepo.save(banner);
    }

    @Override
    public void deleteById(Long id) {
        bannerRepo.deleteById(id);
    }

    @Override
    public Banner findById(Long id) {
        return bannerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    @Override
    public List<Banner> findAll() {
        return bannerRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!bannerRepo.existsById(id)) {
            throw new IllegalArgumentException("Banner nicht gefunden");
        }

        bannerRepo.deleteById(id);
    }
}
