package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {

        //1.从Redis中查询如果list集合为null，或者list.size=0就从dao中查询并存入Redis
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> set = jedis.zrange("category", 0, -1);
        Set<Tuple> set = jedis.zrangeWithScores("category", 0, -1);

        List<Category> list = null;
        if (set==null||set.size()==0){//redis缓存中没有数据要从数据库中读取
            //System.out.println("从数据库查数据。。。");
            list = dao.findAllCategory();

            for (int i = 0; i < list.size(); i++) {
                //将集合存储到Redis
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }else{//redis中有数据从Redis中获取
            //System.out.println("从Redis查数据。。。");
            list = new ArrayList<Category>();
            for (Tuple tuple : set) {
                String cname = tuple.getElement();
                int cid = (int)tuple.getScore();
                Category category = new Category();
                category.setCid(cid);
                category.setCname(cname);
                list.add(category);
            }


        }


        return list;
    }
}
