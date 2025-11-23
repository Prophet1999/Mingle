package com.mingle.repository;

import com.mingle.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLikerIdAndLikeeId(Long likerId, Long likeeId);

    boolean existsByLikerIdAndLikeeId(Long likerId, Long likeeId);
}
