
import java.io.File;
import java.util.HashMap;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;

public class SmsApi {
	//Logger
	private static final Log logger = LogFactory.get();
	private static Setting setting = new Setting(new File(RunAddRestrict.path + "/config.setting"), true);

	/*	public static void main(String[] args) {
			System.out.println(getToken());
		}*/

	public static String getToken() {
		//parameter map
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("client_id", "erp_client");
		paramMap.put("scope", "erp_api");
		paramMap.put("grant_type", "password");
		//read data from setting
		paramMap.put("client_secret", setting.get("client_secret"));//jja$dCJhWK3UmvPg_Op4ow7RF
		paramMap.put("username", setting.get("username"));//ticntestcloud\\admin
		paramMap.put("password", setting.get("password"));//secret
		paramMap.put("acr_values", setting.get("acr_values"));//tenant:ticntestcloud
		System.out.println(paramMap);
		//send request : https://test.vending.allting.com/adminportalidentity/connect/token
		String result = HttpRequest.post(setting.getStr("url") + "/adminportalidentity/connect/token")//url
				.header("Content-Type", "application/x-www-form-urlencoded").form(paramMap)//表单内容
				.timeout(20000)//超时，毫秒
				.execute().body();

		JSONObject json = new JSONObject(result);
		if (json == null || json.isEmpty()) {
			return null;
		} else {
			return json.getStr("access_token");
		}
	}

	//https://test.vending.allting.com/StockAdministration/CostAllocationInstanceItemRelations/SaveMany
	public static void addRestrictItem(JSONObject json) {
		/*JSONObject json = new JSONObject();
		json.put("instanceId", "Stock.CostAllocationInstance630");
		JSONArray entitys = new JSONArray();
		entitys.add("5763005");
		entitys.add("5763195");
		json.put("entityIds", entitys);*/
		System.out.println(json);
		String result = HttpRequest.post(setting.getStr("url") + "/StockAdministration/CostAllocationInstanceItemRelations/SaveMany")//url
				.header("Authorization", "Bearer " + getToken())//Token
				.header("Content-Type", "application/json")//Content-Type
				.body(json)//
				.timeout(20000)//超时，毫秒
				.execute().body();

		logger.info(result);
	}

	//https://test.vending.allting.com/StockAdministration/CostAllocationInstanceUserRelations/SaveMany
	public static void addRestrictUser(JSONObject json) {
		/*JSONObject json = new JSONObject();
		json.put("instanceId", "Stock.CostAllocationInstance630");
		JSONArray entitys = new JSONArray();
		entitys.add("AccessControl.User1");
		entitys.add("AccessControl.User2");
		json.put("entityIds", entitys);*/
		System.out.println(json);
		String result = HttpRequest.post(setting.getStr("url") + "/StockAdministration/CostAllocationInstanceUserRelations/SaveMany")//url
				.header("Authorization", "Bearer " + getToken())//Token
				.header("Content-Type", "application/json")//Content-Type
				.body(json)//
				.timeout(20000)//超时，毫秒
				.execute().body();

		logger.info(result);
	}

	//https://test.vending.allting.com/StockAdministration/CostAllocationInstanceUserGroupRelations/SaveMany
	public static void addRestrictUserGroup(JSONObject json) {
		/*JSONObject json = new JSONObject();
		json.put("instanceId", "Stock.CostAllocationInstance630");
		JSONArray entitys = new JSONArray();
		entitys.add("AccessControl.UserGroup1");
		entitys.add("AccessControl.UserGroup2");
		entitys.add("AccessControl.UserGroup3");
		json.put("entityIds", entitys);*/
		System.out.println(json);
		String result = HttpRequest.post(setting.getStr("url") + "/StockAdministration/CostAllocationInstanceUserGroupRelations/SaveMany")//url
				.header("Authorization", "Bearer " + getToken())//Token
				.header("Content-Type", "application/json")//Content-Type
				.body(json)//
				.timeout(20000)//超时，毫秒
				.execute().body();

		logger.info(result);
	}

	public static void getHierarchy() {
		//send request
		String result = HttpRequest.get("https://test.vending.allting.com/ERP/CostAllocation/GetHierarchy")//url
				.header("Authorization", "Bearer " + getToken())//Token
				.header("Content-Type", "application/x-www-form-urlencoded")//Content-Type
				.timeout(20000)//超时，毫秒
				.execute().body();
		System.out.println(result);
	}

	public static String updateHierarchy() {
		//parameter map
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("client_id", "erp_client");
		paramMap.put("client_secret", "jja$dCJhWK3UmvPg_Op4ow7RF");
		paramMap.put("scope", "erp_api");
		paramMap.put("username", "ticntestcloud\\admin");
		paramMap.put("password", "secret");
		paramMap.put("acr_values", "tenant:ticntestcloud");
		paramMap.put("grant_type", "password");

		//send request
		String result = HttpRequest.post("https://test.vending.allting.com/ERP/CostAllocation/UpdateHierarchy")//url
				.header("Content-Type", "application/x-www-form-urlencoded")//Content-Type
				.form(paramMap)//表单内容
				.body("")//hierarchy json
				.timeout(20000)//超时，毫秒
				.execute().body();

		return result;
	}

	public static void getRequestResult(String guid) {
		//send request
		String result = HttpRequest.get("https://test.vending.allting.com/ERP/CostAllocation/GetRequestResult")//url
				.header("Authorization", "Bearer " + getToken())//Token
				.header("Content-Type", "application/x-www-form-urlencoded")//Content-Type
				.form("requestGuid", guid)//parameter
				.timeout(20000)//超时，毫秒
				.execute().body();
		System.out.println(result);
	}
}
