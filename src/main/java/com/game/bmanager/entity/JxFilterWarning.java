package com.game.bmanager.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jx_filter_warning")
public class JxFilterWarning implements java.io.Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 6273198899042055561L;
    private Integer id;
    private Long order_id;
    private String order_no;
    private Long user_id;
    private String user_phone;
    private String pro_no;
    private String filter_name;
    private Integer time_left;
    private Long manager_no;
    private Integer status;//0=警报，1=已处理
    private Date create_time;
    private Date modify_time;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Long getOrder_id() {
        return order_id;
    }
    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
    public String getOrder_no() {
        return order_no;
    }
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public String getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
    public String getPro_no() {
        return pro_no;
    }
    public void setPro_no(String pro_no) {
        this.pro_no = pro_no;
    }
    public String getFilter_name() {
        return filter_name;
    }
    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }
    public Integer getTime_left() {
        return time_left;
    }
    public void setTime_left(Integer time_left) {
        this.time_left = time_left;
    }
    public Long getManager_no() {
        return manager_no;
    }
    public void setManager_no(Long manager_no) {
        this.manager_no = manager_no;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    public Date getModify_time() {
        return modify_time;
    }
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
    
}
