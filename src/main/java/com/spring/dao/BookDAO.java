package com.spring.dao;

import com.spring.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookDAO {

    public List<Book> getAllBooks();

    public Book getBookById(@Param("id") int id);

    public int add(Book book);

    public int delete(int d);

    public int update(Book entity);
}
