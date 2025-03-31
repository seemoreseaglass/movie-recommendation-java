package com.seemoreseaglass.repository;

import com.seemoreseaglass.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, String> {
}
