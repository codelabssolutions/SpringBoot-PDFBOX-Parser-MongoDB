package com.codelabs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.codelabs.entity.DadosDoExame;
import com.codelabs.entity.FirstPdfData;
@Repository
public interface DaDosdoExameRepository extends  MongoRepository<FirstPdfData, String> {
	DadosDoExame getDadosDoExameById(String id);
}