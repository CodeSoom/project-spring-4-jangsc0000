package com.pio.apiserver.repository;

import com.pio.apiserver.domain.Image;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
