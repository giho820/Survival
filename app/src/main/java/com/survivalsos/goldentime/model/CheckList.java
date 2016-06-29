package com.survivalsos.goldentime.model;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.util.DebugUtil;

/**
 * Created by kiho on 2016. 6. 28..
 */
public class CheckList {
    public Integer no;
    public String title;
    public Integer link;
    public Integer articleId;
    public Integer categoryId;
    public Integer isInMyList;
    public Integer isChecked;

    public String categoryName;;
    public boolean isHeader;

    public String toString() {
        String result = "no :: " + no + "\n" +
                "title :: " + title + "\n" +
                "link :: " + link + "\n" +
                "articleId :: " + articleId + "\n" +
                "categoryId :: " + categoryId + "\n" +
                "isInMyList :: " + isInMyList +"\n" +
                "isChecked :: " + isChecked +"\n" +
                "카테고리 명 :: " + Definitions.CATEGORY_NAME[categoryId] +
                "isHeader :: " + isHeader;

        return result;
    }

    public void setCategoryName(Integer categoryId){
        if(categoryId != null && Definitions.CATEGORY_NAME.length > categoryId) {
            DebugUtil.showDebug("카테고리 네임 :: " + Definitions.CATEGORY_NAME[categoryId - 1]);
            categoryName =  Definitions.CATEGORY_NAME[categoryId - 1];
        } else {
            categoryName = "null";
        }
    }
}
