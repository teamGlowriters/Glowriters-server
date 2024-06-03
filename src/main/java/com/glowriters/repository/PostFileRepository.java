package com.glowriters.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glowriters.domain.Postfile;

public interface PostFileRepository extends JpaRepository<Postfile, Long>{

}
