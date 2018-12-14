package com.czq.kotlinarch.data.model;

import java.util.List;

public class Page<T> {
    public int totalNum;
    public int pageNo;
    public int pageSize;
    public List<T> listData;
}