import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//最终方法  调用后即可下载视频
public class ReadSourceVideoAddress {

	
	public static void main(String[] args) throws Exception {
		//读取文件路径
		String inputPath = "C:\\Users\\月\\Desktop\\sourcevideo.txt";
        //写入文件路径
		String outputPath = "C:\\Users\\月\\Desktop\\sourcevideo2.txt";
		//调用读写方法
		String []s = (String[]) readTxt(inputPath,outputPath).get("address");
//   String []s =returnStringArray(outputPath); 
//        List list =new ArrayList();
//        list =Arrays.asList(s);
//        System.out.println(list);
//        list.stream().filter(a->a!=null&&!a.equals(""));
        DouYinQushuiyin.DownDouyinVideo(s,(List) readTxt(inputPath,outputPath).get("name"));
	}
	
	
	 /**
     * 解析普通文本文件 如txt
     * @param path
     * @return
	 * @throws Exception 
     */
    @SuppressWarnings("unused")
    public static Map readTxt(String path,String outputPath) throws Exception{
    	 //存储txt内每行数据的集合
    	 List <String> list =new ArrayList<>();
    	 //存储处理过后的数据
         List <String> list2 =new ArrayList<>();
         
         List <String> list3 =new ArrayList<>();
         
         //创建stringbuilder
        StringBuilder content = new StringBuilder("");
     
        	//创建文件输出流
            FileOutputStream writerStream = new FileOutputStream(outputPath, true);
            //创建写管道流
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            //获取txt编码格式
            String code = getCode(path);
            //创建文件对象
            File file = new File(path);
            //创建输入流
            InputStream is = new FileInputStream(file);
            //创建读
            InputStreamReader isr = new InputStreamReader(is, code);
            //创建读管道
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            String jd =null;
           
            while (null != (str = br.readLine())) {
                jd = str.trim();
                list.add(jd);
            	}
            list.stream().filter(a->a!=null||!a.equals("")).filter(a->a.split("https").length!=1).forEach(a->{
            	list2.add("https"+a.split("https")[1].split("复制此链接")[0].toString());
            	list3.add(a.split("记录美好生活")[1].split("https")[0]);
//            	try {
//					writer.write("https"+a.split("https")[1].split("复制此链接")[0].toString());
//					writer.write("\r\n");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
            });
            String [] ss = list2.toArray(new String[list2.size()]);
            br.close();
            
           
            writer.close();
        
            Map map = new HashMap();
        	map.put("address", ss);
        	map.put("name", list3);
        	
        return map;
    }
 
	
	/***
	 * 读取文件中的抖音下载地址	
	 * @param path
	 * @return
	 */
    public static String[] returnStringArray(String path){
    	//存储txt内每行数据的集合
   	 List <String> list =new ArrayList<>();
   			 
   	 //创建stringbuilder
     StringBuilder content = new StringBuilder("");
     
     try {
     	
         //获取txt编码格式
         String code = getCode(path);
         //创建文件对象
         File file = new File(path);
         //创建输入流
         InputStream is = new FileInputStream(file);
         //创建读
         InputStreamReader isr = new InputStreamReader(is, code);
         //创建读管道
         BufferedReader br = new BufferedReader(isr);
         String str = "";
         String jd =null;
        
         while (null != (str = br.readLine())) {
             jd = str.trim().toString();
             list.add(jd);
             
         	}
         System.out.println(list.size());
         System.out.println(list);
    	
     }catch (Exception e) {
         e.printStackTrace();
         System.err.println("读取文件:" + path + "失败!");
         
     }
     String [] ss = list.toArray(new String[list.size()]);
    	return ss;
    }
	
	
	  /***
	   * 获取txt文件编码格式
	   * @param path
	   * @return
	   * @throws Exception
	   */
	  public static String getCode(String path) throws Exception {
	        InputStream inputStream = new FileInputStream(path);
	        byte[] head = new byte[3];
	        inputStream.read(head);
	        String code = "gb2312";  //或GBK
	        if (head[0] == -1 && head[1] == -2 )
	            code = "UTF-16";
	        else if (head[0] == -2 && head[1] == -1 )
	            code = "Unicode";
	        else if(head[0]==-17 && head[1]==-69 && head[2] ==-65)
	            code = "UTF-8";
	        inputStream.close();
	        System.out.println(code);
	        return code;
	    }
	  
	  
	
}
