package cn.itcast.travel.service;

public interface FavoriteService {
    boolean isFavorite(String rid,int uid);

    void addFavorite(String rid, int uid);
}
