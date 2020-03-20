import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import cn.hutool.core.date.DateUtil;

public interface PrefectUtils {
	/**
	 * 根据map 里面的时间 进行升序排列  需要引入jar包  hutool进行时间转换
	 * @param list 传入需要排序的集合
	 * @param Orderby 传入排序的字段
	 * @param ugjl 传入升序或降序排列 desc/asc
	 * @return List 
	 */
	public static List returnListMapOrderByTime(List<Map<Object, Object>> list, String Orderby, String ugjl){
		//降序排列
		if(ugjl.equals("asc")){
			list.sort((b,a) ->DateUtil.parse(a.get(Orderby).toString()).getTime() - DateUtil.parse(b.get(Orderby).toString()).getTime() >= 0 ? -1 : 1);
		}else{
		//升序排列	
			list.sort((a,b) ->DateUtil.parse(a.get(Orderby).toString()).getTime() - DateUtil.parse(b.get(Orderby).toString()).getTime() >= 0 ? -1 : 1);
		}
		return list;
	}
	/**
	 * 计算 各种率之后  保留n位小数
	 * @param result 传入需要保留小数的源数字
	 * @param wwuu 传入需要保留的位数 默认为2
	 * @return double
	 */
	public static Double returnDouble(double result, Integer wwuu){
		double doubleresult = 0.0;
		if (wwuu!=null){
			BigDecimal bigDecimal = new BigDecimal(result);
			//这里的wwuu 就是你要保留几位小数。
			 doubleresult = bigDecimal.setScale(wwuu, BigDecimal.ROUND_HALF_UP).doubleValue();
		}else{
			BigDecimal bigDecimal = new BigDecimal(result);
			//这里的 2 就是你要保留几位小数。
			 doubleresult = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return doubleresult;
	}
	/**
	 * 	返回昨天的日期	
	 * @param geui 返回的日期格式
	 * @return String
	 */
	public static String returnYesterday(String geui){
		DateFormat dateFormat=new SimpleDateFormat(geui);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,-24);
		String yesterdayDate=dateFormat.format(calendar.getTime());
		return yesterdayDate;
	}
	/**
	 * list集合循环去空  null "" 都去除
	 * @param list 传入需要去空的集合
	 * @return list
	 */
	public static List returnNotNullList(List list){
	for (int i = 0; i < list.size(); i++) {
		if(list.get(i) == null ||"".equals(list.get(i))){
			list.remove(i);
		}
	}
	return list;
	}
	/***
	 * 将时间字符串按照给的格式格式化
	 * @param dates  源时间串
	 * @param source 原格式
	 * @param wantto 想要改换后的格式
	 * @return String
	 * @throws ParseException 抛出异常
	 */
	public static String returnWantDate(String dates, String source, String wantto) throws ParseException{
		Date date = new SimpleDateFormat(source).parse(dates);
		String dateStr = new SimpleDateFormat(wantto).format(date);
		return dateStr;
	}
	/**
	 * 将指定网址生成二维码 并存储在本地磁盘上
	 * @param url 要生成二维码的图片
	 * @param path 本地存储路径
	 * @param imageType 图片的类型
	 * @param picname 图片的名字
	 * @param width 图片的宽度
	 * @param length 图片的高度
	 * @throws IOException 抛出异常
	 */
	public static void code(String url, String path, String imageType, String picname, int width, int length) throws IOException {
 		 File file =new File(path);
		 if (file.exists()) {
	            if (file.isDirectory()) {
	                System.out.println("dir exists");
	            } else {
	                System.out.println("the same name file exists, can not create dir");
	            }
	        } else {
	            System.out.println("dir not exists, create it ...");
	            file.mkdir();
	        }
		try {
            String content = url;
            String codeName = picname ;// 二维码的图片名
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, length, hints);
            File file1 = new File(path, codeName + "." + imageType);
            MatrixToImageWriter.writeToFile(bitMatrix, imageType, file1);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/****
	 * list 集合去空  只去除null
	 * @param oldList 源集合
	 * @return List
	 */
	public static <T> List<T> removeNull(List<? extends T> oldList) {
	    oldList.removeAll(Collections.singleton(null)); 
	    return (List<T>) oldList;  
	}
	/***
	 * java 发送get请求
	 * @param url 请求的网址
	 * @param param 传入的参数
	 * @return get请求的结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url  + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(50000); 
			connection.setReadTimeout(50000); 
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}






}
