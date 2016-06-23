package com.survivalsos.goldentime.model;

import java.io.Serializable;

/**
 * Created by kiho on 2016. 6. 23..
 */
public class MainImageItemInfo implements Serializable {
    public Integer mainImageCode;
    public String mainImageName;
    public Integer doesLocked; //Todo 디비 테이블 만들 것


    public String toString() {
        String result = "mainImageCode :: " + mainImageCode + "\n" +
                "mainImageName :: " + mainImageName + "\n" +
                "doesLocked :: " + doesLocked;

        return result;
    }
}
