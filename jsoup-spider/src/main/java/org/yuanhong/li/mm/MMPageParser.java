package org.yuanhong.li.mm;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MMPageParser {

	public List<MMPageInfo> parsePage(int page) {
		String url = "http://www.mmjpg.com";
		String reffer = "http://www.mmjpg.com";
		if(page > 1) {
			url = "http://www.mmjpg.com/home/"+page;
			reffer = "http://www.mmjpg.com/home/"+(page-1);
		}
		if(page == 2) {
			reffer = "http://www.mmjpg.com";
		}
		
		List<MMPageInfo> result = new ArrayList<MMPageInfo>();
		try {
			Document doc = Jsoup.connect(url)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.9")
					.header("Cache-Control", "max-age=0")
					.header("Connection", "keep-alive")
					.header("Host", "www.mmjpg.com")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
					.referrer(reffer)
					.get();
			Elements picElements = doc.select("body > div.main > div.pic > ul > li");
			for(Element pic : picElements) {
				Element titleEle = pic.select("li > span.title > a").first();
				String href = titleEle.attr("href");
				String title = titleEle.text();
				
				Element dateEle = pic.select("li > span:nth-child(3)").first();
				String publish = dateEle.text();
				
				Element viewEle = pic.select("li > span.view").first();
				String view = viewEle.text();
				MMPageInfo pageInfo = new MMPageInfo();
				pageInfo.setHref(href.substring(href.lastIndexOf("/")+1));
				pageInfo.setPublish(publish.substring(0, publish.indexOf(" ")));
				pageInfo.setTitle(title);
				pageInfo.setView(view.substring(view.indexOf("(")+1, view.length()-1));
				result.add(pageInfo);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
