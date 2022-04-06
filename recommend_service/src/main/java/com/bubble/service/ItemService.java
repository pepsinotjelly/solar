package com.bubble.service;

import com.bubble.model.ItemBase;
import com.bubble.vo.MovieDetail;
import org.apache.thrift.TException;

import java.util.List;

public interface ItemService {
    Integer insert(ItemBase itemBase);
    List<ItemBase> batchGetItemBase(int startPosition,int endPosition);
    boolean SyncItemBase() throws TException;
    List<MovieDetail> getMovieRecommendByUser(int userId,String page);
    public List<MovieDetail> getMovieDetail(List<Integer> recommendMovieIdList);
}
