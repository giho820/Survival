package com.survivalsos.goldentime.model;

import java.io.Serializable;

/**
 * Created by kiho on 2016. 6. 21..
 */
//Todo Serializable을 implements 하는 이유는 뭐더라
public class Article implements Serializable {
    public Integer articleId;
    public String title;
    public Integer highRankCode;
    public Integer nextArticleId;
    public Integer relatedArticleId;
    public String isSoundFile;
    public String articleText;

    public Integer type;

    public String toString() {
        String result = "articleId :: " + articleId + "\n" +
                "title :: " + title + "\n" +
                "highRankCode :: " + highRankCode + "\n" +
                "nextArticleId :: " + nextArticleId + "\n" +
                "relatedArticleId :: " + relatedArticleId + "\n" +
                "isSoundFile :: " + isSoundFile + "\n" +
                "articleText :: " + articleText;

        return result;
    }
}
