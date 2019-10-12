
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

public class ExecUtil {
	public static String path = System.getProperty("user.dir");

	public static void main(String[] args) {
		/*if (args != null && args[0] != null) {
			if ("item".equals(args[0])) {
				addItemRestrict();
			} else if ("user".equals(args[0])) {
				addUserRestrict();
			} else if ("group".equals(args[0])) {
				addGroupRestrict();
			}
		}*/
		addItemRestrict();
		addUserRestrict();
		addGroupRestrict();
	}

	private static void addItemRestrict() {
		Map<String, JSONObject> dataMap = readExcel("ItemRestrict.xlsx");
		System.out.println(dataMap);
		if (dataMap != null && !dataMap.isEmpty()) {
			for (String key : dataMap.keySet()) {
				SmsApi.addRestrictItem(dataMap.get(key));
			}
		}
	}

	private static void addUserRestrict() {
		Map<String, JSONObject> dataMap = readExcel("UserRestrict.xlsx");
		System.out.println(dataMap);
		if (dataMap != null && !dataMap.isEmpty()) {
			for (String key : dataMap.keySet()) {
				SmsApi.addRestrictUser(dataMap.get(key));
			}
		}
	}

	private static void addGroupRestrict() {
		Map<String, JSONObject> dataMap = readExcel("GroupRestrict.xlsx");
		System.out.println(dataMap);
		if (dataMap != null && !dataMap.isEmpty()) {
			for (String key : dataMap.keySet()) {
				SmsApi.addRestrictUserGroup(dataMap.get(key));
			}
		}
	}

	private static Map<String, JSONObject> readExcel(String fileName) {
		ExcelReader reader = ExcelUtil.getReader(path + "/" + fileName);
		List<List<Object>> excelDataList = reader.read();

		Map<String, JSONObject> dataMap = new HashMap<>();
		for (int i = 1; i < excelDataList.size(); i++) {
			List<Object> dataRow = excelDataList.get(i);
			if (dataRow != null) {
				String instanceId = dataRow.get(0).toString();
				String entityID = dataRow.get(1).toString();
				JSONObject jsonData = dataMap.get(instanceId);
				if (jsonData != null) {
					JSONArray entitys = (JSONArray) jsonData.get("entityIds");
					entitys.add(entityID);
				} else {
					//json data
					JSONObject json = new JSONObject();
					json.put("instanceId", instanceId);
					JSONArray entitys = new JSONArray();
					entitys.add(entityID);
					json.put("entityIds", entitys);

					//put map
					dataMap.put(instanceId, json);
				}
			}

		}

		return dataMap;
	}

}
