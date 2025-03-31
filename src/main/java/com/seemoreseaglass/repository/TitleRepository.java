package com.seemoreseaglass.repository;

import com.seemoreseaglass.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Long> {
    List<Title> findByPrimaryTitleContainingIgnoreCase(String primaryTitle);
}
