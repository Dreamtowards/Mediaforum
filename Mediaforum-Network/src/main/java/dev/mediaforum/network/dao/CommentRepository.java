package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByPostIdAndParentCommentId(int postId, int parentCommentId);

    int countByPostId(int postId);

}
