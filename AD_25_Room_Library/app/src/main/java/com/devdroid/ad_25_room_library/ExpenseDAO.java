package com.devdroid.ad_25_room_library;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDAO {

    @Query("SELECT * FROM expenses")
    List<Expense> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE id = :id")
    Expense getExpenseById(int id);

    @Query("SELECT * FROM expenses WHERE title = :title")
    Expense getExpenseByTitle(String title);

    @Query("SELECT * FROM expenses WHERE amount = :amount")
    Expense getExpenseByAmount(String amount);

    @Insert
    void addTx(Expense expense);

    @Delete
    void deleteTx(Expense expense);

}
