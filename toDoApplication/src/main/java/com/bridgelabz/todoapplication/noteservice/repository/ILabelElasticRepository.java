package com.bridgelabz.todoapplication.noteservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.todoapplication.noteservice.model.Label;

/**
 * @author Saurav
 *         <p>
 *         Elastic Repository for Label
 *         </p>
 *
 */
public interface ILabelElasticRepository extends ElasticsearchRepository<Label, String> {
	/**
	 * @param userId
	 * @return List of labels
	 *         <p>
	 *         This method is for finding the labels by user id
	 *         </p>
	 */
	public List<Label> findLabelsByUserId(String userId);

	/**
	 * <p>
	 * This method is for finding the Labels by their respective LabelName
	 * </p>
	 * 
	 * @param labelName
	 * @return
	 */
	public List<Label> findByLabelName(String labelName);
}
