package com.game.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 需要处理的省市未定义表
 */
@Entity
@Table(name = "common_log_city_undefine")
public class LogCityUndefine implements java.io.Serializable {

    private static final long serialVersionUID = -8531908803032793299L;
    private Long id;
    private String city; // 洲名
    private String province;
    private String country;
    private Integer level; // 哪一层找不到

    public LogCityUndefine() {
        super();
    }

    public LogCityUndefine(String city, String province, String country, Integer level) {
        super();
        this.city = city;
        this.province = province;
        this.country = country;
        this.level = level;
    }

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}