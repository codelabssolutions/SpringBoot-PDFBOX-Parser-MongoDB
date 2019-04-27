package com.codelabs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.codelabs.entity.ExemplosDeECG;
@Repository
public interface ExemplosDeECGRepository extends  MongoRepository<ExemplosDeECG, String> {
	
}