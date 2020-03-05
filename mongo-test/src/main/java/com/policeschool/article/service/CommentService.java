package com.policeschool.article.service;

import com.policeschool.article.dao.CommentRepository;
import com.policeschool.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void delteComment(String id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findCommentList() {
        return commentRepository.findAll();
    }

    public Comment findCommentById(String id) {
        return commentRepository.findById(id).get();
    }

    public void updateCommentLiknum(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likenum");
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
