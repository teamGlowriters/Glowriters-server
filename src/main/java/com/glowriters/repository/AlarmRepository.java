package com.glowriters.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glowriters.domain.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long>{

}
