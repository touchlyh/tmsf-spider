package org.yuanhong.li;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yuanhong.li.huajiao.VideoInfo;
import org.yuanhong.li.huajiao.VideoJsonParser;
import org.yuanhong.li.repository.HuajiaoService;

public class ApplicationHuajiao {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		if(args == null || args.length<2) {
			System.err.println("input args error.");
			return ;
		}
		String op = args[0];
		ApplicationContext context = new ClassPathXmlApplicationContext("spider-datasource.xml");
		HuajiaoService huajiaoService = (HuajiaoService) context.getBean("huajiaoService");
		
		if(op.equalsIgnoreCase("--get")) {
			String datetime = args[1];
			List<VideoInfo> videoList = huajiaoService.queryByDateTime(Long.parseLong(datetime));
			if(videoList == null || videoList.size() ==0) {
				return;
			}
			for(VideoInfo info : videoList) {
				if(info.getPlayCount()>=0) {
					System.out.print(info.getUid());
					System.out.print("\t");
					System.out.print(info.getMd5sum());
					System.out.print("\t");
					System.out.println(info.getMp4());
				}
			}
		}
		if(op.equalsIgnoreCase("--save")) {
			String path = args[1];
			String datetime = args[2];
			int count = 0;
			try {
				FileInputStream inputStream = new FileInputStream(path);
				VideoJsonParser jsonParser = new VideoJsonParser(inputStream, path, datetime);
				List<VideoInfo> videos = jsonParser.parseJson();
				if(videos != null && videos.size()!=0) {
					for(VideoInfo info : videos) {
						VideoInfo exsisted = huajiaoService.getByMd5sum(info.getMd5sum());
						if(exsisted == null) {
							huajiaoService.addVideo(info);
							count++;
						} else {
							System.out.println("exsisted in db."+info.getMd5sum());
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("parse file finished. filename="+path+".save count="+count);
		}
		
		
	}
}
