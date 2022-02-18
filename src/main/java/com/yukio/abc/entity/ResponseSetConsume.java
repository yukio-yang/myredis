package com.yukio.abc.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yukio.abc.utils.easyexcel.converter.SetConsumeDiscernLogURLConverter;
import com.yukio.abc.utils.easyexcel.converter.SetConsumePayTypeConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author yukio
 * @create 2022-02-10 14:50
 * essyExcel测试实体类
 */
@Data
@ExcelIgnoreUnannotated//easy排除未标记的选项(或字段上使用@ExcelIgnore)
@ContentRowHeight(66)
public class ResponseSetConsume extends BaseRowModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("订单号")
	@ExcelProperty(value = "订单号",index = 0)
	@ColumnWidth(30)
	private String orderNo;

	@ApiModelProperty("姓名")
	@ExcelProperty(value = "姓名",index = 1)
	@ColumnWidth(15)
	private String userName;

	@ApiModelProperty("人员类型")
	@ExcelProperty(value = "人员类型",index = 2)
	@ColumnWidth(20)
	private String userType;

	@ApiModelProperty("订单金额")
	@ExcelProperty(value = "订单金额",index = 3)
	@ColumnWidth(20)
	private String amount;

	@ApiModelProperty("下单时间")
	@ExcelProperty(value = "下单时间",index = 4)
	@ColumnWidth(30)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@ApiModelProperty("商户名称")
	@ExcelProperty(value = "商户名称",index = 5)
	@ColumnWidth(30)
	private String merchantName;

	@ApiModelProperty("支付方式(1_刷卡支付,2_刷脸支付)")
	@ExcelProperty(value = "支付方式",index = 6, converter = SetConsumePayTypeConverter.class)
	@ColumnWidth(20)
	private String payType;

	@ApiModelProperty("餐卡卡号")
	@ExcelProperty(value = "餐卡卡号",index = 7)
	@ColumnWidth(30)
	private String cardNo;

	@ApiModelProperty("识别记录")//SetConsumeDiscernLogURLConverter.class
	@ExcelProperty(value = "识别记录",index = 8, converter = SetConsumeDiscernLogURLConverter.class)
	@ColumnWidth(20)
	private String discernLog;

	@ApiModelProperty("设备编号")
	@ExcelProperty(value = "设备编号",index = 9)
	@ColumnWidth(30)
	private String deviceNo;

	@ApiModelProperty("设备地址")
	@ExcelProperty(value = "设备地址",index = 10)
	@ColumnWidth(30)
	private String deviceAddress;
}
