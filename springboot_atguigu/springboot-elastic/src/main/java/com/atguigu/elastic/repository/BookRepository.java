package com.atguigu.elastic.repository;

import com.atguigu.elastic.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

}
