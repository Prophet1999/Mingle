package com.mingle.repository;

import com.mingle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.id != :userId AND (6371 * acos(cos(radians(:lat)) * cos(radians(u.latitude)) * cos(radians(u.longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(u.latitude)))) < :distance", nativeQuery = true)
    List<User> findUsersNear(@Param("userId") Long userId, @Param("lat") Double lat, @Param("lon") Double lon,
            @Param("distance") Double distance);
}
