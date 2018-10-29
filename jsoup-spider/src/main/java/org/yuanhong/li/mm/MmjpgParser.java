package org.yuanhong.li.mm;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MmjpgParser {

	/**
	 * http://www.mmjpg.com/mm/${listId}/${pageId}
	 * @param listId
	 * @return
	 */
	public List<String> getMztList(String listId) {
		String url = "http://www.mmjpg.com/mm/"+listId;
		String refer = "http://www.mmjpg.com/";
		List<String> imgs = new ArrayList<String>();
		int total = 0;
		int page = 1;
		do {
			if(page > 1) {
				refer = url;
				url = "http://www.mmjpg.com/mm/"+listId+"/"+page;
			}
			
			try {
				@SuppressWarnings("unused")
				Document doc = Jsoup.connect(url)
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.9")
						.header("Cache-Control", "max-age=0")
						.header("Connection", "keep-alive")
						.header("Host", "www.mmjpg.com")
						.header("Upgrade-Insecure-Requests", "1")
						.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
						.referrer(refer)
						.get();
				
				if(page == 1) {
					Elements totalElements = doc.select("#page > a");
					Element totalElement = totalElements.get(totalElements.size()-2);
					total = Integer.parseInt(totalElement.text());
				}
				
				Element imgElment = doc.selectFirst("#content > a > img");
				String src = imgElment.attr("src");
				if(src != null && src.trim().length() !=0 ) {
					imgs.add(src);
				}
				
				page++;
				Thread.sleep(100);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}while(page <= total);
		
		
		
		return imgs;
	}
}
