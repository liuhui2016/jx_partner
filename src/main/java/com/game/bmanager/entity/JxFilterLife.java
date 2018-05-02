package com.game.bmanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jx_proflt_life")
public class JxFilterLife implements java.io.Serializable{
    private static final long serialVersionUID = -4838034317809451156L;
    private Long id;
    private String code;
    private Integer pp;
    private Integer cto;
    private Integer ro;
    private Integer t33;
    private Integer wfr;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Integer getPp() {
        return pp;
    }
    public void setPp(Integer pp) {
        this.pp = pp;
    }
    public Integer getCto() {
        return cto;
    }
    public void setCto(Integer cto) {
        this.cto = cto;
    }
    public Integer getRo() {
        return ro;
    }
    public void setRo(Integer ro) {
        this.ro = ro;
    }
    public Integer getT33() {
        return t33;
    }
    public void setT33(Integer t33) {
        this.t33 = t33;
    }
    public Integer getWfr() {
        return wfr;
    }
    public void setWfr(Integer wfr) {
        this.wfr = wfr;
    }

}
