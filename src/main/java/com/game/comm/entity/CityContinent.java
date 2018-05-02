package com.game.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 洲-国家对照表
 */
@Entity
@Table(name = "common_city_continent")
public class CityContinent implements java.io.Serializable {

    private static final long serialVersionUID = -8531908803032793299L;
    private Long id;
    private String name; // 洲名

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}