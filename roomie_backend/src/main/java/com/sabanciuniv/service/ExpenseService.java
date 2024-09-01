// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.service;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sabanciuniv.model.Expense;
import com.sabanciuniv.model.Group;
import com.sabanciuniv.repo.ExpenseRepository;
import com.sabanciuniv.repo.GroupRepository;
import com.sabanciuniv.repo.UserRepository;

import org.springframework.util.StringUtils;

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
    private GroupRepository groupRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<Expense> getAllProducts(String groupId) {
		int year = LocalDate.now().getDayOfYear();
		int month = LocalDate.now().getMonthValue();
		return expenseRepository.findByGroupIdAndYearMonth(groupId, year, month);
	}
	
	@Transactional
    public Expense addExpense(String groupId, Expense expense, String usernameBy) {
        // Check if the group exists and the user is part of the group
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        if (!group.getUsers().contains((Object)usernameBy)) {
            throw new SecurityException("User not authorized to add expenses to this group");
        }

        
        // Set the groupId and the user who added the expense
        
        expense.setUser(userRepository.findByUsername(usernameBy));
        expense.setGroup(groupRepository.findById(groupId));
        
        // Validate the expense object (if necessary)
        validateExpense(expense);

        // Save the expense to the database
        return expenseRepository.save(expense);
    }
	
	private void validateExpense(Expense expense) {
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }

        if (!StringUtils.hasText(expense.getDescription())) {
            throw new IllegalArgumentException("Expense description must not be empty");
        }

        if (expense.getDate() == null) {
            throw new IllegalArgumentException("Expense date must not be null");
        } else if (expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date must not be in the future");
        }

        if (expense.getCategory() == null) {
            throw new IllegalArgumentException("Expense category must not be empty");
        }
    }	
}
