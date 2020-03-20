import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;


//下载抖音无水印视频
public class DouYinQushuiyin {
	
	public static void main(String[] args) throws IOException {
		
		String []s = {"https://haokan.baidu.com/v?vid=9560616820981234147"};
	DownDouyinVideo(s,new ArrayList());

   
    }
	
	
	public static void DownDouyinVideo(String [] s,List name) throws IOException{
//	     DataResponse dataResponse=new DataResponse();
        //String url1 ="#在抖音，记录美好生活#是你，是你，还是你！ http://v.douyin.com/xj4Agk/ 复制此链接，打开【抖音短视频】，直接观看视频！";

        //过滤链接，获取http连接地址
     //   String finalUrl = "https://v.douyin.com/WaPyms/";
        String [] st = s;
       
      //  int i = 0;
        for(int i =0;i<st.length;i++){
       // 	i=2;
        	System.out.println("right");
        	
        	System.out.println(st[i]);
        	//1.利用Jsoup抓取抖音链接
            //抓取抖音网页
            String htmls = Jsoup.connect(st[i]).ignoreContentType(true).execute().body();
         //   System.out.println(htmls);
            //2.利用正则匹配可以抖音下载链接
            //playAddr: "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200ffc0000bfil6o4mavffbmroeo80&line=0",
            //具体匹配内容格式：「https://aweme.snssdk.com/aweme/...line=0」
            Pattern patternCompile = Pattern.compile("(?<=playAddr: \")https?://.+(?=\",)");
            //利用Pattern.compile("正则条件").matcher("匹配的字符串对象")方法可以将需要匹配的字段进行匹配封装 返回一个封装了匹配的字符串Matcher对象

            //3.匹配后封装成Matcher对象
            Matcher m = patternCompile.matcher(htmls);

            //4.①利用Matcher中的group方法获取匹配的特定字符串 ②利用String的replace方法替换特定字符,得到抖音的去水印链接
            String matchUrl ="";
            while(m.find()) {
                matchUrl = m.group(0).replaceAll("playwm", "play");
            }

            //5.将链接封装成流
            //注:由于抖音对请求头有限制,只能设置一个伪装手机浏览器请求头才可实现去水印下载
            Map<String, String> headers = new HashMap<>();
            headers.put("Connection", "keep-alive");
            headers.put("Host", "aweme.snssdk.com");
            headers.put("User-Agent",
                    "Mozilla/5.0 (iPod; CPU iPhone OS 6_0_1 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A523 Safari/8536.25");
            headers.put("Content-Encoding","gzip");
           //7.利用Joup获取视频对象,并封装成一个输入流对象
            //BufferedInputStream in = Jsoup.connect(matchUrl).headers(headers).timeout(10000).ignoreContentType(true).execute().bodyStream();
            byte[] ins = Jsoup.connect(matchUrl).maxBodySize(60000000).headers(headers).timeout(150000).ignoreContentType(true).execute().bodyAsBytes();
            String filename="//"+name.get(i).toString()+i+".mp4";
            //UploadUtil.uploadFile(filename,in);

            //8.保存文件到指定位置
            File fileParent= new File("D://chen");
            fileParent.setWritable(true, false);
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }

            File file= new File("D://chen"+filename);
            file.createNewFile();

            BufferedOutputStream out =
                    new BufferedOutputStream(
                            new FileOutputStream(file));
            //copyFile(in,out);
            out.write(ins,0,ins.length);
            out.flush();
            out.close();
        }
    //    https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=202003181117030101440602140C014A80
        //in.close();

     //   dataResponse.setVideoUrl("/images/"+filename);

    //    return dataResponse;
	}
}

