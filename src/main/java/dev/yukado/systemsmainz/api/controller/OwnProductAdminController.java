package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.service.OwnProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/own-products")
public class OwnProductAdminController {

    @Autowired
    private OwnProductService service;

    @PostMapping
    public OwnProduct create(@RequestBody OwnProduct product) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    public OwnProduct update(@PathVariable Long id, @RequestBody OwnProduct product) {
        product.setId(id);
        return service.update(product);
    }
}

