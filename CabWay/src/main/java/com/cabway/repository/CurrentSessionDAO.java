package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabway.model.CurrentSession;

public interface CurrentSessionDAO extends JpaRepository<CurrentSession, Integer>{

	public  CurrentSession  findByUuid(String uuid);
}
