package com.optimissa.BookShelfApi.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.optimissa.BookShelfApi.Utils.ParseDateFactory;
import com.optimissa.BookShelfApi.model.Author;
import com.optimissa.BookShelfApi.model.Book;
import com.optimissa.BookShelfApi.model.BookDetails;
import com.optimissa.BookShelfApi.repositories.AuthorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GoogleJsonParser {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private ParseDateFactory parseDateFactory;


    public List<Book> parseJsonAsBooks(JsonNode node) {
        return parseItems((ArrayNode) node.get("items"));
    }

    public List<Book> parseItems(ArrayNode arrayNode) {
        List<Book> books = new ArrayList<>();

        for (JsonNode node : arrayNode) {
            try {
                books.add(parseBook(node));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return books;
    }

    public Book parseBook(JsonNode node) throws NullPointerException {
        String description = getDescription(node);
        Date publishDate = getPublication(node);
        String family = getFamily(node);
        String language = getLanguage(node);
        String authorStr = getAuthor(node);
        Double price = getPrice(node);
        String title = getTitle(node);
        String isbn = Objects.requireNonNull(getISBN(node), "ISBN is null");

        Author author = Objects.requireNonNull(authorRepo.findAuthorByName(authorStr)
                , "Author " + authorStr + " not mapped on database");

        BookDetails details = BookDetails.builder()
                .description(description)
                .family(family)
                .publication(publishDate)
                .language(language)
                .price(price)
                .build();

        return Book.builder()
                .id(isbn)
                .author(author)
                .title(title)
                .bookDetails(details)
                .build();
    }

    private String getDescription(JsonNode node) {
        JsonNode volumeInfo = node.get("volumeInfo");
        JsonNode description = volumeInfo.get("description");

        return (description == null) ? "" : description.asText();
    }

    private String getAuthor(JsonNode node) {
        JsonNode volumeInfo = node.get("volumeInfo");
        ArrayNode authors = (ArrayNode) volumeInfo.get("authors");

        return authors.get(0).asText();
    }


    private Date getPublication(JsonNode node) {
        JsonNode volumeInfo = node.get("volumeInfo");
        String publishedDateTxt = volumeInfo.get("publishedDate").asText();

        return parseDateFactory.parse(publishedDateTxt);
    }

    public String getFamily(JsonNode node) {
        JsonNode volumeninfo = node.get("volumeInfo");
        ArrayNode categories = (ArrayNode) volumeninfo.get("categories");

        return categories == null
                ? "Narrativa"
                : categories.get(0).asText();
    }

    private String getLanguage(JsonNode node) {
        JsonNode volumeInfo = node.get("volumeInfo");
        String language = volumeInfo.get("language").asText();

        return language;
    }


    public Double getPrice(JsonNode node) {
        JsonNode salesInfo = node.get("saleInfo");
        JsonNode listPrice = salesInfo.get("listPrice");

        return listPrice == null
                ? -1
                : listPrice.get("amount").asDouble();
    }

    public String getTitle(JsonNode node) {
        JsonNode volumenInfo = node.get("volumeInfo");
        String title = volumenInfo.get("title").asText();

        return title;
    }

    private String getISBN(JsonNode node) {
        JsonNode volumeInfo = node.get("volumeInfo");
        ArrayNode industryIdentifiers = (ArrayNode) volumeInfo.get("industryIdentifiers");

        if (industryIdentifiers == null) return null;

        JsonNode ISBN = industryIdentifiers.get(0);
        return ISBN.get("identifier").asText();

    }

}
