package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgeDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgeDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao dao = new RouteDaoImpl();
    private RouteImgeDao imgDao = new RouteImgeDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public Route findOne(String rid) {

        //封装路线详细数据
        Route route = dao.findByRid(Integer.parseInt(rid));
        System.out.println(route.getRid());
        //封装图片集合
        List<RouteImg> routeList = imgDao.findImg(route.getRid());

        route.setRouteImgList(routeList);

        //设置商家
        Seller seller = sellerDao.findSellById(route.getSid());
        route.setSeller(seller);
        //根据route的rid查询收藏表中的数量，并设置到route对象中
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
