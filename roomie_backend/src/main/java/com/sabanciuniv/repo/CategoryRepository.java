// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.sabanciuniv.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    // Additional methods can be added as required for category management
	Optional<Category> findById(String id);
}