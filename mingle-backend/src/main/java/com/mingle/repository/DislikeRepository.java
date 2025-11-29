package com.mingle.repository;

import com.mingle.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Long> {
    boolean existsByDislikerIdAndDislikeeId(Long dislikerId, Long dislikeeId);
}
