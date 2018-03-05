package com.kiranreddy.budgettracker.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransationRepository extends JpaRepository<Transaction, Long>{

}
