package com.game.quartz3;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;


@Service("jobService")
public class JobService {

    @Autowired
    private SessionFactory sessionFactory;
    
	public void filterWarning() {
	    JdbcTemplate jdbcTemplate = new JdbcTemplate(SessionFactoryUtils.getDataSource(sessionFactory));
        System.out.println(new Date() + "  开始统计滤芯警报数据：");
        StringBuilder sb = new StringBuilder();
        sb.append(" insert IGNORE into jx_filter_warning ")
        .append("SELECT pf.prf_id,jo.ord_id,jo.ord_no,jo.u_id,jo.ord_phone,pf.pro_no,pf.prf_pp,pf.prf_cto,pf.prf_ro,pf.prf_t33,pf.prf_wfr,jo.ord_managerno,0")
        .append("FROM jx_proflt pf LEFT JOIN jx_product pd ON pf.pro_no = pd.pro_no LEFT JOIN jx_order jo on jo.pro_no=pf.pro_no where pf.prf_other = 1");
        int result = jdbcTemplate.update(sb.toString());
        System.out.println(new Date() + "  执行滤芯警报数据结束！执行成功：" + result);
	}
}
