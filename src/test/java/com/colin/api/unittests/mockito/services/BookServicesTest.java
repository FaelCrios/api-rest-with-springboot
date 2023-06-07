package com.colin.api.unittests.mockito.services;

import com.colin.api.data.vo.v1.BooksVO;
import com.colin.api.entities.Book;
import com.colin.api.exceptions.RequiredResourceIsNullException;
import com.colin.api.repositories.BooksRepository;
import com.colin.api.services.BookServices;
import com.colin.api.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {
	
	
	MockBook input;
	
	@InjectMocks
	private BookServices service;
	
	@Mock
	BooksRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	
	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();
		when(repository.findAll()).thenReturn(list);
		
		var result = service.findAll();
		assertNotNull(result);
		assertEquals(14, result.size());
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
		assertEquals("Some Title1", result.getTitle());
	}

	@Test
	void testCreateBook() {
		Book entity = input.mockEntity(1);
		Book persisted = entity;
		persisted.setId(1L);
		
		BooksVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.createBook(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
		assertEquals("Some Title1", result.getTitle());
	}
	

	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredResourceIsNullException.class, () -> {
			service.createBook(null);
		});
		
		String expectedMessage = "It is not allowed to persit a null object";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void testUpdateBook() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BooksVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.updateBook(vo);
		
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Some Author1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
		assertEquals("Some Title1", result.getTitle());
	}
	
	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredResourceIsNullException.class, () -> {
			service.updateBook(null);
		});
		
		String expectedMessage = "It is not allowed to persit a null object";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void testDeleteBook() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

}
