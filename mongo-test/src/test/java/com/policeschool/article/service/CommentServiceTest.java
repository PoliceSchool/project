package com.policeschool.article.service;

import com.policeschool.article.ArticleApplication;
import com.policeschool.article.po.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Test
    public void testFindCommentList() {
        List<Comment> list = commentService.findCommentList();
        System.out.println(list);
    }

    @Test
    public void testFindComment() {
        Comment comment = commentService.findCommentById("1");
        System.out.println(comment);
    }

    @Test
    public void testSaveComment() {
        Comment comment = new Comment();
        comment.setArticleid("100000");
        comment.setContent("测试添加的数据");
        comment.setCreatedatetime(LocalDateTime.now());
        comment.setUserid("1003");
        comment.setNickname("凯撒大帝");
        comment.setState("1");
        comment.setLikenum(0);
        comment.setReplynum(0);
        commentService.saveComment(comment);
    }

    @Test
    public void updateCommentLiknum() {
        commentService.updateCommentLiknum("5e61104341987e23078fda4a");
    }
}
