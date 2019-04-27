package com.codelabs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.codelabs.entity.RelatorioTabular;
@Repository
public interface RelatorioTabularRepository  extends  MongoRepository<RelatorioTabular, String> {
	
}

