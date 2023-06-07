package com.colin.api.services;

import com.colin.api.controller.BookController;
import com.colin.api.controller.PersonController;
import com.colin.api.data.vo.v1.BooksVO;
import com.colin.api.data.vo.v1.PersonVO;
import com.colin.api.data.vo.v2.PersonVOV2;
import com.colin.api.entities.Book;
import com.colin.api.entities.Person;
import com.colin.api.exceptions.RequiredResourceIsNullException;
import com.colin.api.exceptions.ResourceNotFoundException;
import com.colin.api.mapper.DozerMapper;
import com.colin.api.mapper.custom.PersonMapper;
import com.colin.api.repositories.BooksRepository;
import com.colin.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

	private final Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired
	BooksRepository repository;


	public List<BooksVO> findAll(){
		logger.info("Finding All Books");
		var books = DozerMapper.parseListObjects(repository.findAll(), BooksVO.class);
		books.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		return books;
	}

	public BooksVO findById(Long id){
		logger.info("Finding one book by ID");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = DozerMapper.parseObject(entity, BooksVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}

	public BooksVO createBook(BooksVO book){
		if(book == null) throw new RequiredResourceIsNullException();
		logger.info("Creating one Book");
		var entity = DozerMapper.parseObject(book, Book.class);

		var vo = DozerMapper.parseObject(repository.save(entity), BooksVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public BooksVO updateBook(BooksVO book){
		if(book == null )throw new RequiredResourceIsNullException();
		logger.info("Updating one Book");

		var entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setAuthor(book.getAuthor());
		entity.setPrice(book.getPrice());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setTitle(book.getTitle());

		var vo = DozerMapper.parseObject(repository.save(entity), BooksVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}


	public void delete(Long id) {
		logger.info("Deleting one book");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
}
