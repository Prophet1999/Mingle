package com.mingle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dislikes", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "dislikerId", "dislikeeId" })
})
public class Dislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long dislikerId;

    @Column(nullable = false)
    private Long dislikeeId;
}
