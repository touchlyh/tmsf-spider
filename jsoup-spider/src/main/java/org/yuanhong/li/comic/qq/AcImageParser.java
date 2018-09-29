package org.yuanhong.li.comic.qq;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AcImageParser {

	public List<String> parseImageList(String html){
		Document doc = Jsoup.parse(html);
		Elements imgElements = doc.select("body > section.comic-pic-area.scroll-mode > section.comic-pic-list-all > ul > li.comic-pic-item.pic-loaded > div.comic-pic-box > img");
		List<String> urlList = new ArrayList<String>();
		for(Element img : imgElements) {
			String url = img.attr("data-src");
			urlList.add(url);
		}
		return urlList;
	}
}
