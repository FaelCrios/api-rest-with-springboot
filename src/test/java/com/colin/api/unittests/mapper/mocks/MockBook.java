package com.colin.api.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.colin.api.data.vo.v1.BooksVO;
import com.colin.api.entities.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BooksVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BooksVO> mockVOList() {
        List<BooksVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }

    public BooksVO mockVO(Integer number) {
        BooksVO book = new BooksVO();
        book.setKey(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }

}
