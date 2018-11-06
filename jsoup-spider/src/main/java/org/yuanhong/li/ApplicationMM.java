package org.yuanhong.li;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yuanhong.li.mm.MMPageInfo;
import org.yuanhong.li.mm.MMPageParser;
import org.yuanhong.li.mm.MmjpgParser;
import org.yuanhong.li.repository.MMPicService;

public class ApplicationMM {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spider-datasource.xml");
		MMPicService mmPicService = (MMPicService) context.getBean("mmPicService");
		
		String page = args[0];
		int pageIndex = Integer.parseInt(page);
		MMPageParser parser = new MMPageParser();
		MmjpgParser picParser = new MmjpgParser();
		List<MMPageInfo> pageList = parser.parsePage(pageIndex);
		if(pageList == null || pageList.size() == 0) {
			return;
		}
		for(MMPageInfo pageInfo : pageList) {
			String href = pageInfo.getHref();
			MMPageInfo dbInfo = mmPicService.getByHref(href);
			if(dbInfo == null) {
				//DB中不存在，执行添加并打印
				mmPicService.addPageInfo(pageInfo);
				List<String> imgs = picParser.getMztList(href);
				for(String img : imgs) {
					System.out.print(href+"\t");
					System.out.print(img);
					System.out.println("\n");
				}
			}
		}
	}
}
