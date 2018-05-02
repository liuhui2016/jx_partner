package com.game.comm.action;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.game.comm.entity.Picr;
import com.game.comm.service.IPicrManager;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.services.account.ResourceManager;
import com.game.util.Constants;
import com.game.util.Md5Encoder;

/**
 * pic资源管理Action.
 */
@Namespace("/comm/picr")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "picr.action?authId=${authId}", type = "redirect") })
public class PicrAction extends CrudActionSupport<Picr> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IPicrManager picrManager;
    private Long id;

    /**
     * 多选操作使用
     */
    private List<Long> ids;
    private Picr entity;
    private Page<Picr> page = new Page<Picr>(15);

    private File iconfile;
    private String iconfileFileName;

    @Override
    public Picr getModel() {
        return entity;
    }

    @Override
    protected void prepareModel() throws Exception {
        if (null != id) {
            entity = picrManager.get(id);
        } else {
            entity = new Picr();
        }
    }

    // -- CRUD Action 函数 --//
    /**
     * list页面显示用户分页列表.
     */
    @Override
    public String list() throws Exception {
        List<PropertyFilter> filters = HibernateUtils.buildPropertyFilters(Struts2Utils.getRequest());
        // 设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }

        page = picrManager.searchPage(page, filters);
        // excel导出
        if (page.isExcelExp())
            return toExcel(page);

        return SUCCESS;
    }

    /**
     * 详情界面
     * 
     * @return
     */
    public String detail() {
        entity = picrManager.get(id);
        return "detail";
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String save() throws Exception {
        boolean flag = false;
        if (entity.getId() == null) {
            flag = true;
            picrManager.save(entity);
        }

        // 图片处理
        if (iconfile != null) {
            String iconPath = Constants.CONS_PROPERTIES.getValue("PIC_PATH");
            String iconUrl = Constants.CONS_PROPERTIES.getValue("PIC_URL");
            String iconMd5 = Md5Encoder.md5(iconfile);
            entity.setPicMd5(iconMd5);
            // 重命名文件 规则：资源ID + "p" + md5
            String newFileName = entity.getId() + "p" + iconMd5 + iconfileFileName.substring(iconfileFileName.lastIndexOf("."));

            // 保存路径
            String filepath = iconPath + File.separator + newFileName;
            entity.setPicPath(filepath);
            File targeta = new File(iconPath + File.separator, newFileName);
            FileUtils.copyFile(iconfile, targeta);
            entity.setPicUrl(iconUrl + "/" + newFileName); // 下载路径
        }

        picrManager.save(entity);
        addActionMessage("保存图片 " + entity.getTitle() + " 成功");
        if (flag) {
            this.logToDB(106, "新增图片ID：" + entity.getId());
        } else {
            this.logToDB(106, "修改图片ID：" + entity.getId());
        }
        return RELOAD;
    }

    @Override
    public String delete() throws Exception {
        // ApkManager.delete(id);
        addActionMessage("删除成功");
        return RELOAD;
    }

    public String delAll() throws Exception {
        try {
            Assert.notEmpty(ids, "没有选择删除列！！！");
            if (picrManager.delAll(ids)) {
                this.addActionMessage("删除成功！！！");
                this.logToDB(106, "删除资源ID：" + ids);
            } else {
                this.addActionMessage("删除失败！！！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            addActionMessage("资源使用中无法删除！！！");
        }

        return RELOAD;
    }

    /**
     * excel导入
     * 
     * @return
     * @throws Exception
     */
    public String importExcel() throws Exception {
        return importExcel(ResourceManager.class, "saveResource");
    }

    // ************* getting and setting *****************
    public Picr getEntity() {
        return entity;
    }

    public void setEntity(Picr entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Page<Picr> getPage() {
        return page;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public void setPage(Page<Picr> page) {
        this.page = page;
    }

    public File getIconfile() {
        return iconfile;
    }

    public void setIconfile(File iconfile) {
        this.iconfile = iconfile;
    }

    public String getIconfileFileName() {
        return iconfileFileName;
    }

    public void setIconfileFileName(String iconfileFileName) {
        this.iconfileFileName = iconfileFileName;
    }

}