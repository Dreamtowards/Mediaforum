package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByOrderByPostTimeDesc();

}
