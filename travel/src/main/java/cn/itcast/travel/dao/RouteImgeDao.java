package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgeDao {
    public List<RouteImg> findImg(int id);
}
