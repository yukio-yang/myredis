package com.yukio.abc.utils.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.yukio.abc.entity.CategoryExcel;
import com.yukio.abc.mapper.CategoryMapper;
import com.yukio.abc.utils.easyexcel.exception.ExcelDataRepeatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author yukio
 * @create 2022-02-10 15:07
 */
@Component
public class CateGroyListener extends AnalysisEventListener<CategoryExcel> {
	//HksplatformsetCardManageModule导入实体类(属性上加 @ExcelProperty(value = "餐卡卡号",index = 0)属性即可)
	public List<CategoryExcel> getList() {
		return list;
	}

	public void setList(List<CategoryExcel> list) {
		this.list = list;
	}

	private static final Logger LOGGER =
			LoggerFactory.getLogger(com.yukio.abc.utils.easyexcel.listener.CateGroyListener.class);
	/**
	 * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
	 */
	private static final int BATCH_COUNT = 5;
	List<CategoryExcel> list = new ArrayList<CategoryExcel>();

	/**
	 * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
	 */
	private CategoryMapper categoryMapper;

	/**
	 * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
	 *
	 * @param categoryMapper
	 */
	public CateGroyListener(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	/**
	 * 这个每一条数据解析都会来调用
	 *
	 * @param category
	 *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
	 * @param analysisContext
	 */
	@Override
	public void invoke(CategoryExcel category, AnalysisContext analysisContext) throws ExcelDataRepeatException {
		System.out.println("invoke方法被调用");
		LOGGER.info("解析到一条数据:{}", JSON.toJSONString(category));
		list.add(category);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (list.size() >= BATCH_COUNT) {
			saveData();
			// 存储完成清理 list
			list.clear();
		}

	}
	/**
	 * 所有数据解析完成了 都会来调用
	 *
	 * @param context
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) throws ExcelDataRepeatException{
		System.out.println("doAfterAllAnalysed方法 被调用");
		// 这里也要保存数据，确保最后遗留的数据也存储到数据库
		saveData();
		LOGGER.info("所有数据解析完成！");

	}

	/**
	 * 加上存储数据库
	 */
	private void saveData()  throws ExcelDataRepeatException{
//		List<LinkedHashMap<String, Object>> userOrgs = ModuleUtil.getManagedOrg();
		LOGGER.info("{}条数据，开始存储数据库！", list.size());
		//excel去重
		LinkedHashSet<CategoryExcel> listDistinct = new LinkedHashSet<>();
		for(CategoryExcel category : list) {
			listDistinct.add(category);
		}
		//还需判断这些统一代码是否属于该用户(不属于该用户的不能存)

		//(可能存在同名,但是同一机构代码和餐卡号完全一致的不能存)重名的不能存
//		ArrayList<CategoryExcel> listNew = new ArrayList<>();
		//还需要数据库去重(具体逻辑具体处理)
//		for(CategoryExcel category : listDistinct) {
//
//		}
		//可以直接存集合，我这儿先一个一个的存cardManageService.save(list);
		if(listDistinct.size() > 0) {
			for(CategoryExcel category : listDistinct) {
				//要存orgId,这边拿到的是orgName
//				String orgIdByOrgName = cardManageMapper.getOrgIdByOrgCode(cardManageModuleNew.getOrgCode(),userOrgs);
				//前端只有机构名字和餐卡，其他都是这儿定义
//				HksplatformsetCardManage cardManage = new HksplatformsetCardManage();
//				cardManage.setId(UUIDUtil.getUUID());
//				cardManage.setState("1");//默认正常
//				cardManage.setBindState("0");//默认未绑定
//				cardManage.setCreateTime(new Date());
//				cardManage.setOrgId(orgIdByOrgName);
//				cardManageMapper.insert(cardManage);
				LOGGER.info("数据："+category);
//				categoryMapper.insert(category);
			}
			LOGGER.info("存储数据库成功！");
		}

	}
}
