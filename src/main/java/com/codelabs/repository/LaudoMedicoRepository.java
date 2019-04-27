package com.codelabs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.codelabs.entity.LaudoMedico;
@Repository
public interface LaudoMedicoRepository extends  MongoRepository<LaudoMedico, String> {
	
}
