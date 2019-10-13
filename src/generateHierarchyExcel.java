import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class generateHierarchyExcel {
	//Logger
	private static final Log logger = LogFactory.get();
	public static String path = System.getProperty("user.dir");

	public static void main(String[] args) {
		List<Map<String, Object>> machineDatas = readExcelData("MachineData.xlsx");
		List<Map<String, Object>> operatorDatas = readExcelData("OperatorData.xlsx");

		List<List<?>> rows = CollUtil.newArrayList();

		for (Map<String, Object> map : machineDatas) {
			String machineID = (String) map.get("ID");
			for (Map<String, Object> map2 : operatorDatas) {
				List<?> row = CollUtil.newArrayList(null, machineID, map2.get("Definition value"), map2.get("Step type name"));
				rows.add(row);
			}
		}

		//通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter(path + "/HierachyData.xlsx");
		// 一次性写出内容，使用默认样式
		writer.write(rows);
		// 关闭writer，释放内存
		writer.close();
		
		logger.info("生成数据完成！");

	}

	public static List<Map<String, Object>> readExcelData(String fileName) {
		ExcelReader reader = ExcelUtil.getReader(path + "/" + fileName);
		return reader.readAll();
	}

}
