package com.game.bmanager.action;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxPicture;
import com.game.bmanager.entity.JxProdetail;
import com.game.bmanager.entity.JxView;
import com.game.bmanager.service.IJxPictureService;
import com.game.bmanager.service.IJxProdetailService;
import com.game.bmanager.service.IJxViewService;
import com.game.modules.orm.Page;
import com.game.modules.web.CrudActionSupport;
import com.game.util.Constants;
import com.game.util.Md5Encoder;

@Namespace("/bmanager/view")
@Results({ @Result(name = "reload", location = "view.action?authId=${authId}", type = "redirect") })
public class ViewAction extends CrudActionSupport<JxView>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915119569915135307L;
	
	@Autowired
	private IJxViewService viewService;
	
	@Autowired
	private IJxProdetailService prodetailService;
	
	@Autowired
	private IJxPictureService pictrueService;
	
	private JxPicture picture;
	
	private JxProdetail prodetail;
	
	private JxView jxView;
	
	private Long id;
    private Long oldId;
    private List<Long> ids;
    
    private Page<JxView> page = new Page<JxView>(15);
    
    private Long prot_type;
    
    private File iconfile;
    
    private String iconfileFileName;

    public static Long po_id;
    

	@Override
	public JxView getModel() {
		if (this.id != null) {
            this.jxView = ((JxView) viewService.get(this.id));
        } else {
            this.jxView = new JxView();
        }
		return jxView;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return "viewinput";
	}

	@Override
	public String list() throws Exception {
		page = prodetailService.queryByProtId(page,po_id);
        return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		String url = null;
		if (iconfile != null) {
			String iconPath = Constants.CONS_PROPERTIES.getValue("PIC_PATH");
			String iconUrl = Constants.CONS_PROPERTIES.getValue("PIC_URL");
			String iconMd5 = Md5Encoder.md5(iconfile);
			String newFileName = iconMd5
					+ iconfileFileName.substring(iconfileFileName
							.lastIndexOf("."));

			// 保存路径
			File targeta = new File(iconPath + File.separator, newFileName);
			FileUtils.copyFile(iconfile, targeta);

			url = iconUrl + "/" + newFileName;// 下载路径
		} else {
			System.out.println("pic is null");
		}
		if(url != null){
			jxView.setPic_url(url);
		}
		this.prodetail = this.prodetailService.get(Long.valueOf(jxView.getId()));
		prodetail.setProd_name(jxView.getProd_name());
		prodetail.setProd_modtime(jxView.getProd_modtime());
		this.picture = this.pictrueService.get(Long.valueOf(prodetail.getProd_picid()));
		picture.setPic_color(jxView.getPic_color());
		picture.setPic_name(jxView.getProd_name());
		picture.setPic_tone(jxView.getPic_tone());
		picture.setPic_url(jxView.getPic_url());
		picture.setPic_modtime(jxView.getProd_modtime());
		viewService.save(jxView);
		prodetailService.save(prodetail);
		pictrueService.save(picture);
		po_id = prodetail.getProt_id();
		return RELOAD;
	}

	public JxView getJxView() {
		return jxView;
	}

	public void setJxView(JxView jxView) {
		this.jxView = jxView;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Page<JxView> getPage() {
		return page;
	}

	public void setPage(Page<JxView> page) {
		this.page = page;
	}

	public Long getProt_type() {
		return prot_type;
	}

	public void setProt_type(Long prot_type) {
		this.prot_type = prot_type;
	}

	public JxPicture getPicture() {
		return picture;
	}

	public void setPicture(JxPicture picture) {
		this.picture = picture;
	}

	public JxProdetail getProdetail() {
		return prodetail;
	}

	public void setProdetail(JxProdetail prodetail) {
		this.prodetail = prodetail;
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
