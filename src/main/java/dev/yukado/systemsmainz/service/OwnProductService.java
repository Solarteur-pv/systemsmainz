package dev.yukado.systemsmainz.service;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.repository.OwnProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OwnProductService {

    @Autowired
    private OwnProductRepository repo;

    public OwnProduct create(OwnProduct p) {
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());
        return repo.save(p);
    }

    public OwnProduct update(OwnProduct p) {
        p.setUpdatedAt(LocalDateTime.now());
        return repo.save(p);
    }
}

