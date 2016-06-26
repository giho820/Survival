package com.survivalsos.goldentime.model;

/**
 * Created by kiho on 2016. 6. 26..
 */
public class CheckList{
    int idx;
    String title;
    int link;
    int articleId;
    int categoryId;

    public String toString() {
        String result = "idx :: " + idx + "\n" +
                "title :: " + title + "\n" +
                "link :: " + link + "\n" +
                "articleId :: " + articleId + "\n" +
                "categoryId :: " + categoryId;
        return result;
    }

}