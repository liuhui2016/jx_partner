package com.game.smvc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxOrder;

public interface IJxOrderService extends GenericManager<JxOrder, Long>{

	
	
	int modifyOrderStatus(String out_trade_no);
	//根据订单号删除订单
	Boolean deleteProductByordNo(String id);
	Map<String, Object> findAddressById(Long id);
	Boolean queryPaternerByManagerNo(String managerNo);
	String findProNameById(Long pro_id);
	String findpayWayByProNo(String pro_no);
	List<Map<String, Object>> findServiceDetailByProNo(String pro_no);
	List<Map<String, Object>> queryAllMess(String uid,String page);
	int updateMessageStatusById(String id);
	//查看续费订单详情
	List<Map<String, List<Map<String, Object>>>> findAgainOrderDetailByOno(String ono,String productId);
	//查询当前生效的订单
    JxOrder queryOrderByProno(String pro_no);
	List<Map<String, Object>> findServiceDetailByProNo(String pro_no,String user);
	int updateStatusAndProNo(String pro_no);
	List<Map<String, List<Map<String, Object>>>> unTabletBinding(int id);
	int selectType(String orderno);
	String findProNameByIds(int pro_id);
	JxOrder selectOrdernoByProno(String prono);
	List<Map<String, Object>> selectOrdernoByPronos(String prono);
	int selectCity(String city);
	int findwater(String uid);
	List<Map<String, Object>> findrestflow(String uid);
	int findnumber(String uid);
	List<Map<String, Object>> findtime(String uid);
	int findmuit(String s);
	int updatemultiple(String ono);
	JxOrder findall(String ono);
	JxOrder findorder(String ono);
	int findTotalPrice(String fimOrderNo);
	Map<String,Object> findTotalPrices(String fimOrderNo);
	List<Map<String, Object>> findState(String uid);
	int findppdnum(String uid);
	int findYearsOrFlow(String uid);
	
	
	List<Map<String, Object>> queryOrdersByuid(String ord_managerno, int page);
	List<Map<String, Object>> findGenerationOfPayment(String ord_managerno,
			String state, int page);
	List<Map<String, Object>> findPaymentHasBenn(String ord_managerno,
			String state, int page);
	List<Map<String, Object>> findIsBinding(String ord_managerno, String state,
			int page);
	List<Map<String, Object>> findRenewal(String ord_managerno, String state,
			int page);
	List<Map<String, Object>> findOrderDetailByOno(String ono);
	Float findTotalMoneyOrBg(String username,String time);
	Float findTotalMoneyOrTs(String username);
	Float findTotalMoneyOrLs(String username);
	Float findRenewalBg(String username,String time);
	Float findRenewalTs(String username);
	Float findRenewalLs(String username);
	int findWallNumber(String username, String time, String last_add_time);
	int findVerticalNumber(String username, String time, String last_add_time);
	int findDesktopNumber(String username, String time, String last_add_time);
	int findWallRenewNumber(String username, String time, String last_add_time);
	int findVerticalRenewNumber(String username, String time, String last_add_time);
	int findDesktopRenewNumber(String username, String time, String last_add_time);
	Float findCostMoneyOrBg(String username,String time, String last_add_time);
	Float findCostMoneyOrTs(String username);
	Float findCostMoneyOrLs(String username);
	Float findAllMoney(Object object,String time);
	Float findAllMoneyOfTime(Object object, String time, String last_add_time);
	int updateTradeState(String addtime, String modtime, String user_name);
	Float findServiceChargeYJ(String username, String time);
	Float findRenewalYJ(String username, String time);
	Float findCostYJ(String username, String time, String last_add_time);
	Float findAllTotalPledgeOfLower(Object object, String time);
	Float findAllTotalPledgeOfLowerToLastTime(Object object, String time,
			String last_add_time);
	Float findTotalSfee(String username, String time);
	Float findAllTotalSfeeYJ(String username, String time);
	Float findAllInstall(String username, String time);
	Float findAllInstallToYJ(String username, String time);
	Float findAllXfMoneyOfTime(Object object, String time, String last_add_time);
	Float findAllXfPledgeMoneyOfTime(Object object, String time,
			String last_add_time);
	int updateTradeStateToSuccess(String addtime, String modtime,
			String user_name);
	int findWallNumbers(String username, String time, String last_add_time);
	int findVerticalNumbers(String username, String time, String last_add_time);
	int findDesktopNumbers(String username, String time, String last_add_time);
	int findWallRenewNumbers(String username, String time, String last_add_time);
	int findVerticalRenewNumbers(String username, String time,
			String last_add_time);
	int findDesktopRenewNumbers(String username, String time,
			String last_add_time);
	int updateTradeStateToFail(String time, String last_add_time,
			String username);
	Map<String, Object> finTime(String s1);
	Float findAllMoneyOfTimeF(String username, String time, String last_add_time);
	Float findAllTotalPledgeOfLowerToLastTimeY(String username, String time,
			String last_add_time);
	Float findAllXfMoneyOfTimeX(String username, String time,
			String last_add_time);
	Float findAllXfPledgeMoneyOfTimeXY(String username, String time,
			String last_add_time);
	Float selectTotalUserRevenue(Object username);
	Float totalDeposit(Object username);
	Float selectTxZong(Object username, String time,String last_add_time);
	Float selecttxYaJin(Object username, String time,String last_add_time);
	int findWallNumberNew(Object username, String time,String last_add_time);
	int findVerticalNumberNew(Object username, String time,String last_add_time);
	int findDesktopNumberNew(Object username, String time,String last_add_time);
	int findWallRenewNumberNew(Object username, String time,String last_add_time);
	int findVerticalRenewNumberNew(Object username, String time,String last_add_time);
	int findDesktopRenewNumberNew(Object username, String time, String last_add_time);
	Float selectTxFwf(Object username, String time, String last_add_time);
	Float selectTxFwfYaJin(Object username, String time,String last_add_time);
	Float selectTxXuFei(Object username, String time,String last_add_time);
	Float selectTxXuFeiYaJin(Object username, String time,String last_add_time);
	int selectNumberOfInstalledIsers(String username);
	int selectNumberOfInstalledIsersThreeYears(String username);
	
	
	
	
	
}
