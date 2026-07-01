package dev.yukado.systemsmainz.service.own;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.repository.OwnProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OwnProductService {

    private final OwnProductRepository repo;

    public OwnProductService(OwnProductRepository repo) {
        this.repo = repo;
    }

    public OwnProduct create(OwnProduct p) {
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());
        return repo.save(p);
    }

    public OwnProduct update(Long id, OwnProduct p) {
        p.setId(id);
        p.setUpdatedAt(LocalDateTime.now());
        return repo.save(p);
    }

    public List<OwnProduct> findAll() {
        return repo.findAll();
    }

    public OwnProduct findById(Long id) {
        return repo.findById(id).orElseThrow();
    }
}



