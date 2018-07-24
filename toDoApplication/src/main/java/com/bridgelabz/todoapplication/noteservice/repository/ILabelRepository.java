package com.bridgelabz.todoapplication.noteservice.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoapplication.noteservice.model.Label;

@Repository
public interface ILabelRepository extends MongoRepository<Label, String>{
	public List<Label> findLabelsByUserId(String userId);
}
