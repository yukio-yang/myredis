package com.yukio.abc.utils.pdf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yukio
 * @create 2022-02-17 15:57
 */
//字段映射配置
public class PdfMapping {

	public final static Map<String, String> BASE_INFO_MAPPING = new HashMap() {
		{
			put("name", "partyA");
			put("identity", "baseIdentity");

		}
	};
}
