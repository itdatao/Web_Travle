package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
   private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {

        String sql ="select count(*) from tab_route where 1=1 ";
        List list = new ArrayList();
        StringBuffer sb = new StringBuffer(sql);

        if (cid!=1){
            sb.append(" and cid=? ");
            list.add(cid);
        }

        if (rname!=null&&rname.length()>0){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }

        sql = sb.toString();

        int total = jdbcTemplate.queryForObject(sql, Integer.class,list.toArray());
        return total;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {

        String sql = "select * from tab_route where 1=1 ";

        List params = new ArrayList();
        StringBuilder builder = new StringBuilder(sql);

        if (cid!=1){
            builder.append(" and cid = ? ");
            params.add(cid);
        }

        //System.out.println(rname);
        if (rname!=null&&rname.length()>0){
            builder.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        builder.append(" limit ? , ? ");
        sql = builder.toString();

        params.add(start);
        params.add(pageSize);

        List<Route> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());

        return list;
    }

    @Override
    public Route findByRid(int rid) {
        String sql = "select * from tab_route where rid = ? ";

        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);

        return route;
    }
}
