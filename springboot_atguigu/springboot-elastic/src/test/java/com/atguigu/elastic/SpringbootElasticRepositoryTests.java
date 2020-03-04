package com.atguigu.elastic;

import com.atguigu.elastic.bean.Article;
import com.atguigu.elastic.bean.Book;
import com.atguigu.elastic.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * elasticsearch 查询所有数据:http://localhost:9200/atguigu/book/_search
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void index() {
        Book book = new Book();
        bookRepository.index(book);
    }

    @Test
    public void search() {

    }
}
