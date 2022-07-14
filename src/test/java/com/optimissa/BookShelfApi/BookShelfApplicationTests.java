package com.optimissa.BookShelfApi;

import com.optimissa.BookShelfApi.Controller.AuthorController;
import com.optimissa.BookShelfApi.model.Author;
import com.optimissa.BookShelfApi.repositories.AuthorRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class BookShelfApplicationTests {


	@InjectMocks
	private AuthorController authorController;

	@Mock
	private AuthorRepo authorRepo;




	@Test
	void contextLoads() {
	}


	@Test
	public void testGetAuthorById() {
		Author a = new Author();
		a.setId(1l);
		when(authorRepo.findAuthorById(1l)).thenReturn(a);

		Author author = authorController.showById(1l);
		verify(authorRepo).findAuthorById(1l);
		assertEquals(1l, author.getId());
	}

	@Test
	public void testGetAuthorByName() {
		Author a = new Author();
		a.setName("Pedro Salinas");
		when(authorRepo.findAuthorsByName("Pedro Salinas")).thenReturn(a);

		Author author = authorController.showByName("Pedro Salinas");
		verify(authorRepo).findAuthorsByName("Pedro Salinas");
		assertEquals("Pedro Salinas", author.getName());
	}


}
