// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.repo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sabanciuniv.model.Expense;
import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    void deleteByGroupIdAndId(String groupId, String expenseId);  // Delete an expense within a specific group
    
    
    // Custom aggregation to fetch expenses by year and month
    @Aggregation(pipeline = {
        "{ $match: { 'groupId': ?0 } }",
        "{ $project: { year: { $year: '$date' }, month: { $month: '$date' }, data: '$$ROOT' } }",
        "{ $match: { 'year': ?1, 'month': ?2 } }",
        "{ $replaceRoot: { newRoot: '$data' } }"
    })
    List<Expense> findByGroupIdAndYearMonth(String groupId, int year, int month);
    
    
}