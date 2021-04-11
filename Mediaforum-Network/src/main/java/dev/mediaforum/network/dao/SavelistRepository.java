package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.Savelist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavelistRepository extends JpaRepository<Savelist, Integer> {

    List<Savelist> findAllByAuthorIdOrderByCreatedTimeDesc(int authorId);

    // but really should name needs been unique by/in a user .?
    Savelist findByAuthorIdAndName(int authorId, String name);

}
