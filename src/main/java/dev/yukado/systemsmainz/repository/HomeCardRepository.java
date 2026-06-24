package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.HomeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeCardRepository extends CrudRepository<HomeCard, Long> {

    List<HomeCard> findAll();
    HomeCard findHomeCardById(Long id);
    HomeCard findHomeCardByFilename(String filename);

}
