package com.yukio.abc.utils.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author yukio
 * @create 2022-02-10 14:56
 * 根据url转成单张图片的
 */
public class SetConsumeDiscernLogURLConverter implements Converter<String> {
	@Override
	public Class supportJavaTypeKey() {
		return String.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.IMAGE;
	}

	@Override
	public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
									GlobalConfiguration globalConfiguration) {
		throw new UnsupportedOperationException("Cannot convert images to url.");
	}

	@Override
	public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) throws IOException {
		InputStream inputStream = null;
		try {
			URL uuURL = new URL(value);
			//开启连接(加一下代理看运行变快不)
//            InetSocketAddress address = new InetSocketAddress("localhost", 8080);
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
//            URLConnection uc = uuURL.openConnection(proxy);
			URLConnection uc = uuURL.openConnection();
			URL url  = null;
			//获取响应状态
			HttpURLConnection urlCon = (HttpURLConnection) uc;
			urlCon.setConnectTimeout(3000);//毫秒
			urlCon.setReadTimeout(3000);
			int statusCode = urlCon.getResponseCode();
			switch (statusCode){
				case 200:
					inputStream = uuURL.openStream();
					break;
				case 404:
					//默认给一个图片
					//https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598096095144&di=9a72ad26e83effb9341c711c9818b85f&imgtype=0&src=http%3A%2F%2Fpic.616pic.com%2Fys_bnew_img%2F00%2F11%2F69%2Fj2AjnHspwT.jpg
					url = new URL("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2823594453,1696565924&fm=15&gp=0.jpg");
					inputStream = url.openStream();
					break;
				default :
					url = new URL("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2823594453,1696565924&fm=15&gp=0.jpg");
					inputStream = url.openStream();
					break;
			}
			byte[] bytes = IoUtils.toByteArray(inputStream);
			//关流(防止一直打开IO而不关闭IO)
			inputStream.close();
			urlCon.disconnect();//断开连接
			return new CellData(bytes);
		}catch (ConnectException exception){
			//捕获下链接异常
			URL url = new URL("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2823594453,1696565924&fm=15&gp=0.jpg");
			inputStream = url.openStream();
			byte[] bytes = IoUtils.toByteArray(inputStream);
			return new CellData(bytes);
		}catch (FileNotFoundException fileNotFoundException){
			URL url = new URL("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2823594453,1696565924&fm=15&gp=0.jpg");
			inputStream = url.openStream();
			byte[] bytes = IoUtils.toByteArray(inputStream);
			return new CellData(bytes);
		}finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
