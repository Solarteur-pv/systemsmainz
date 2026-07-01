package dev.yukado.systemsmainz.service.own;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.entity.OwnProductImage;
import dev.yukado.systemsmainz.repository.OwnProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnProductImageService {

    private final OwnProductImageRepository repo;

    public OwnProductImageService(OwnProductImageRepository repo) {
        this.repo = repo;
    }

    public OwnProductImage save(OwnProductImage img) {
        return repo.save(img);
    }

    public List<OwnProductImage> findByProduct(OwnProduct product) {
        return repo.findAll().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .toList();
    }
}


