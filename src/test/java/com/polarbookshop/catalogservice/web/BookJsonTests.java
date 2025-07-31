package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var now = Instant.now();
        Book book = new Book(394L, "1234567890", "Title", "Author", 9.9, now, now,
                0);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate")
                .isEqualTo(book.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate")
                .isEqualTo(book.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
                .isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        String jsonContent = """
                {
                    "isbn":"1234567890",
                    "title":"Title",
                    "author":"Author",
                    "price":9.9,
                    "createdDate":"2022-01-01T00:00:00Z",
                    "lastModifiedDate":"2022-01-01T00:00:00Z",
                    "version":21
                }
                """;
        Book book = json.parseObject(jsonContent);
        assertThat(book.isbn()).isEqualTo("1234567890");
        assertThat(book.title()).isEqualTo("Title");
        assertThat(book.author()).isEqualTo("Author");
        assertThat(book.price()).isEqualTo(9.9);
        assertThat(book.createdDate()).isEqualTo(Instant.parse("2022-01-01T00:00:00Z"));
        assertThat(book.lastModifiedDate()).isEqualTo(Instant.parse("2022-01-01T00:00:00Z"));
        assertThat(book.version()).isEqualTo(21);
    }
}
