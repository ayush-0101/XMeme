package com.crio.xmeme.repositories;

import com.crio.xmeme.entities.MemePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemePostRepository extends JpaRepository<MemePost, Long> {
}
