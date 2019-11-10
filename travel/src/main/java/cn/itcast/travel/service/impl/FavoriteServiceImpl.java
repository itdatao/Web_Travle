package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao dao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = dao.findFavoriteByRidAndUid(Integer.parseInt(rid), uid);

        return favorite == null ? false: true;
}

    @Override
    public void addFavorite(String rid, int uid) {
        dao.addFavorite(Integer.parseInt(rid),uid);
    }
}
