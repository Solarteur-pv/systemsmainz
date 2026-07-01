package dev.yukado.systemsmainz.service.own;

import dev.yukado.systemsmainz.entity.OwnCategory;
import dev.yukado.systemsmainz.repository.OwnCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnCategoryService {

    private final OwnCategoryRepository repo;

    public OwnCategoryService(OwnCategoryRepository repo) {
        this.repo = repo;
    }

    public OwnCategory create(OwnCategory c) {
        return repo.save(c);
    }

    public List<OwnCategory> findAll() {
        return repo.findAll();
    }
}

