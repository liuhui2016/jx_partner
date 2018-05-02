package com.game.bmanager.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxDetailPicture;
import com.game.bmanager.entity.JxPicture;
import com.game.bmanager.entity.JxProdetail;
import com.game.bmanager.entity.JxView;
import com.game.bmanager.service.IJxPictureService;
import com.game.bmanager.service.IJxProdetailService;
import com.game.bmanager.service.IJxViewService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.util.Constants;
import com.game.util.Md5Encoder;

@Namespace("/bmanager/prodetail")
@Results({ @Result(name = "reload", location = "prodetail.action?authId=${authId}", type = "redirect") })
public class ProdetailAction extends CrudActionSupport<JxProdetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1622948248440509749L;

	@Autowired
	private IJxProdetailService prodetailService;

	@Autowired
	private IJxPictureService pictrueService;

	@Autowired
	private IJxViewService viewService;

	private JxProdetail prodetail;

	private JxView view = new JxView();

	private Long id;
	private Long oldId;
	private List<Long> ids;
	private Page<JxProdetail> pages = new Page<JxProdetail>(15);

	private Page<JxView> page = new Page<JxView>(15);

	private JxDetailPicture jxDetailPicture;

	private File iconfile;

	private String iconfileFileName;

	private JxPicture picture;

	private String pic_url;

	private Long prot_type;

	private Long prot_id;

	public static Long proId;

	private String color;

	private String tone;
	
	private String pic_urls;

	@Override
	public JxProdetail getModel() {
		if (this.id != null) {
			this.prodetail = ((JxProdetail) prodetailService.get(this.id));
			this.picture = pictrueService.get(Long.valueOf(prodetail
					.getProd_picid()));
		} else {
			this.prodetail = new JxProdetail();
			this.picture = new JxPicture();
		}
		return prodetail;
	}


	public String select() throws Exception {
		page = prodetailService.queryByProtId(page, prot_type);
		proId = prot_type;
		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		if (id != null) {
			prodetailService.remove(id);
			pictrueService.remove(picture.getId());
			viewService.remove(id);
		}
		prot_type = prodetail.getProt_id();
		return select();
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = HibernateUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		if (!this.pages.isOrderBySetted()) {
			this.pages.setOrderBy("id");
			this.pages.setOrder("desc");
		}
		pages = prodetailService.searchPage(pages, filters);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public String save() throws Exception {
		boolean flag = false;
		if (id == null) {
			prodetail.setProd_addtime(new Date());
			flag = true;
			picture.setPic_addtime(new Date());
		}
		prodetail.setProd_modtime(new Date());
		picture.setPic_modtime(new Date());

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

		if (flag) {
			this.logToDB(106, "新增图片ID：" + prodetail.getId());
		} else {
			this.logToDB(106, "修改图片ID：" + prodetail.getId());
		}

		prodetail.setProt_id(proId);
		picture.setPic_name(prodetail.getProd_name());
		picture.setPic_color(color);
		picture.setProtype_id(prodetail.getProt_id());
		picture.setPic_tone(tone);
		picture.setPic_default(0);

		if (url != null) {
			picture.setPic_url(url);
		}

		picture = pictrueService.save(picture);
		prodetail.setProd_picid(picture.getId());
		prodetailService.save(prodetail);

		view.setId(prodetail.getId());
		view.setPic_color(picture.getPic_color());
		view.setProt_id(prodetail.getProt_id());
		view.setProd_name(prodetail.getProd_name());
		view.setPic_tone(picture.getPic_tone());
		view.setPic_url(picture.getPic_url());
		view.setProd_picid(prodetail.getProd_picid());
		view.setProd_addtime(picture.getPic_addtime());
		view.setProd_modtime(picture.getPic_modtime());
		viewService.save(view);
		prot_type = prodetail.getProt_id();
		return select();
	}

	public JxProdetail getProdetail() {
		return prodetail;
	}

	public void setProdetail(JxProdetail prodetail) {
		this.prodetail = prodetail;
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

	public Page<JxProdetail> getPages() {
		return pages;
	}

	public void setPages(Page<JxProdetail> pages) {
		this.pages = pages;
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

	public JxPicture getPicture() {
		return picture;
	}

	public void setPicture(JxPicture picture) {
		this.picture = picture;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public Long getProt_type() {
		return prot_type;
	}

	public void setProt_type(Long prot_type) {
		this.prot_type = prot_type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Page<JxView> getPage() {
		return page;
	}

	public void setPage(Page<JxView> page) {
		this.page = page;
	}

	public Long getProt_id() {
		return prot_id;
	}

	public void setProt_id(Long prot_id) {
		this.prot_id = prot_id;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	public JxDetailPicture getJxDetailPicture() {
		return jxDetailPicture;
	}

	public void setJxDetailPicture(JxDetailPicture jxDetailPicture) {
		this.jxDetailPicture = jxDetailPicture;
	}

	public JxView getView() {
		return view;
	}

	public void setView(JxView view) {
		this.view = view;
	}

	public String getPic_urls() {
		return pic_urls;
	}

	public void setPic_urls(String pic_urls) {
		this.pic_urls = pic_urls;
	}

}
