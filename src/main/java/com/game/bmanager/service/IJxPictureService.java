package com.game.bmanager.service;

import java.util.List;
import java.util.Map;

import com.game.bmanager.entity.JxPicture;
import com.game.bmanager.entity.JxView;
import com.game.modules.orm.Page;
import com.game.modules.service.GenericManager;

public interface IJxPictureService extends GenericManager<JxPicture, Long>{

	Page<JxPicture> queryByPicId(Page<JxPicture> pagePic, Long prod_picid);

	String queryByDefault(Long srcpath);

	Long queryIdByUrl(String pic_url);

	void updateUrl(Long picId, String url);

	void update(JxView jxView);

	List<Map<String, Object>> findNmaeAndUrl(String proid, String color);

}
