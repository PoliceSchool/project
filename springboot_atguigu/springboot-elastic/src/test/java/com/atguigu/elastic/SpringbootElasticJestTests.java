package com.atguigu.elastic;

import com.atguigu.elastic.bean.Article;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.codec.BinaryDecoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticJestTests {

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息");
        article.setAuthor("zhangsan");
        article.setContent("Hello World");

        // 构建一个索引功能
        Index index = new Index.Builder(article).index("atguigu").type("news").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search() {
        String json = "{" +
                "\"query\":{" +
                "\"match\":{" +
                "\"content\":\"hello\"" +
                "}" +
                "}" +
                "}";
        // 构建搜索功能
        Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();

        //执行
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println("this is result:");
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
