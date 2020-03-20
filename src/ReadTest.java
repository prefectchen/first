import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
//将百度知道 地址转成数组
public class ReadTest {
	 public static void main(String[] args) throws Exception {
	        String inputPath = "C:\\Users\\月\\Desktop\\test.txt";
	        String outputPath = "C:\\Users\\月\\Desktop\\test2.txt";
	        String content = readTxt(inputPath,outputPath);
	        System.out.println(content);
	    }
	 
	 
	    /**
	     * 解析普通文本文件 如txt
	     * @param path
	     * @return
	     */
	    @SuppressWarnings("unused")
	    public static String readTxt(String path,String outputPath){
	    	 List <String> list =new ArrayList<>();
	            List <String> list2 =new ArrayList<>();
	        StringBuilder content = new StringBuilder("");
	        try {
	            FileOutputStream writerStream = new FileOutputStream(outputPath, true);
	            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
	            String code = getCode(path);
	            File file = new File(path);
	            InputStream is = new FileInputStream(file);
	            InputStreamReader isr = new InputStreamReader(is, code);
	            BufferedReader br = new BufferedReader(isr);
	            String str = "";
	            String jd =null;
	           
	            while (null != (str = br.readLine())) {
	                jd = str.trim();
	                writer.write(jd+" "+"ns");
	                writer.write("\r\n");
	                list.add(jd);
	        }
	     //       list.forEach(System.out::println);
	            list.forEach(a->{
	       //     	System.out.println("http"+a.split("http")[1].toString());
	            	list2.add("'"+"http"+a.split("https")[1].toString()+"'");
	            });
	            br.close();
	            writer.close();
	            list2.forEach(System.out::println);
	            System.out.println(list2);
	            
	            return "ok";
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("读取文件:" + path + "失败!");
	            return "读取文件:" + path + "失败!";
	        }
	        
	    }
	 
	 
	 
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