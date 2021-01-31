package com.example.androidfundamentalsproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id") //acces la parametru cu doua puncte
    User getById(int id);

    @Query("SELECT * FROM user WHERE username = :name") //acces la parametru cu doua puncte
    List<User> getAllByName(String name);

    @Query("SELECT * FROM user WHERE username=:name AND password = :pass") //acces la parametru cu doua puncte
    User getByUsernamePass(String name,String pass);
}
