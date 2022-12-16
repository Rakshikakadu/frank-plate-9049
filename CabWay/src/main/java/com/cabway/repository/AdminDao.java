package com.cabway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabway.model.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer>{

	public Admin findByUserName(String userName);
}
