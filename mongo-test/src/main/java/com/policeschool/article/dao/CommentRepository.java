package com.policeschool.article.dao;

import com.policeschool.article.po.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Optional<Comment> findAllByUserid(String id);
}
