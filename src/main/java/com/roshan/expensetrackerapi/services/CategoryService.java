package com.roshan.expensetrackerapi.services;

import com.roshan.expensetrackerapi.domain.Category;
import com.roshan.expensetrackerapi.exceptions.EtBadRequestException;
import com.roshan.expensetrackerapi.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryService {

	List<Category> fetchAllCategories(Integer userId);

	Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;

	Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;

	void updateCategory(Integer userId, Integer categoryId,
	                    Category category) throws EtBadRequestException;

	void removeCategoryWillAllTransactions(Integer userId,
	                                       Integer categoryId) throws EtResourceNotFoundException;
}
