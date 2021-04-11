package dev.mediaforum.network.dao;

import dev.mediaforum.network.dao.model.SavelistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SavelistItemRepository extends JpaRepository<SavelistItem, Integer> {

    SavelistItem findBySavelistIdAndPostId(int savelistId, int postId);

    @Transactional
    void deleteBySavelistIdAndPostId(int savelistId, int postId);

    // DANGEROUS
    @Transactional
    void deleteBySavelistId(int savelistId);

    int countByPostId(int postId);

    List<SavelistItem> findAllBySavelistIdOrderByAddedTimeDesc(int savelistId);

}
