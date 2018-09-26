package org.yuanhong.li.comic.nte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.yuanhong.li.comic.ComicInfo;
import org.yuanhong.li.comic.ComicSection;
import org.yuanhong.li.utils.JacksonUtil;

public class NteComicParser {

	public ComicInfo getMainInfo(String comicId) {
		String url = "https://h5.manhua.163.com/source/"+comicId;
		try {
			Document doc = Jsoup.connect(url).header("Connection", "keep-alive")
				.header("Cache-Control", "max-age=0")
				.userAgent("Upgrade-InAndroid 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36")
				.header("Accept", "textwebp,image/apng,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language", "zh-CN,zh;q=0.9")
				.header("Cookie", "___rl__test__cookies=1537803968605; OUTFOX_SEARCH_USER_ID_NCOO=1927545782.3182015; isFirstTime=isFirstTime; __f_=1536562405341; _ntes_nnid=2fd5a760b0a1e3ba77a4fe444fa13315,1536562405901; _ntes_nuid=2fd5a760b0a1e3ba77a4fe444fa13315; _cw.us.c.v.online=CSkhOSIQfzgDQUtUWVcJFlpSBUBJSkhVbAxQclZbXxdLdlwaVgBPelJATQFHTwb14f8b49; __usertracker=303ff552c8a449aab20c113451a0c7cb; _ch5.us.c.v.online=WR0WB18XcyMJG3wcAhEGElpSBUBJSkhVbAxQc1VUXxdLdlwaVgBPelJATQFHTw17774cb4; __utma=140496042.1596120123.1537803945.1537803945.1537803945.1; __utmb=140496042.1.9.1537803968746; __utmc=140496042; __utmz=140496042.1537803945.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)")
				.header("Upgrade-Insecure-Requests", "1").get();
			
			ComicInfo comicInfo = new ComicInfo();
			Element titleEle = doc.select("#info > h1").get(0);
			String title = titleEle.text();
			Element authorEle = doc.selectFirst("#info > h2");
			String author = authorEle.text();
			Element tagEle = doc.selectFirst("#info > h3 > div");
			String tag = tagEle.text();
			Element descriptionEle = doc.selectFirst("#intro");
			String description = descriptionEle.html();
			Element coverEle = doc.selectFirst("#cover-info");
			String cover = coverEle.attr("src");
			
			comicInfo.setTitle(title);
			comicInfo.setComicId(comicId);
			comicInfo.setAuthor(author);
			comicInfo.setRawTags(tag);
			comicInfo.setDescription(description);
			comicInfo.setCover(cover);
			comicInfo.setSource("NTE");
			comicInfo.setStatus(0);
			return comicInfo;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ComicSection> getSectionList(String comicId){
		String url = String.format("https://h5.manhua.163.com/book/catalog/%s.json", comicId);
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
					.header("Accept", "*/*")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language", "zh-CN,zh;q=0.9")
				.header("Connection", "keep-alive")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Cookie", "OUTFOX_SEARCH_USER_ID_NCOO=1927545782.3182015; isFirstTime=isFirstTime; __f_=1536562405341; _ntes_nnid=2fd5a760b0a1e3ba77a4fe444fa13315,1536562405901; _ntes_nuid=2fd5a760b0a1e3ba77a4fe444fa13315; _cw.us.c.v.online=CSkhOSIQfzgDQUtUWVcJFlpSBUBJSkhVbAxQclZbXxdLdlwaVgBPelJATQFHTwb14f8b49; __usertracker=303ff552c8a449aab20c113451a0c7cb; _ch5.us.c.v.online=WR0WB18XcyMJG3wcAhEGElpSBUBJSkhVbAxQc1VUXxdLdlwaVgBPelJATQFHTw17774cb4; __utmc=140496042; __utma=140496042.1596120123.1537803945.1537803982.1537865821.3; __utmz=140496042.1537865821.3.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); ___rl__test__cookies=1537865835802; __utmb=140496042.14.9.1537865835865")
				.header("Host", "h5.manhua.163.com")
				.header("Referer", "https://h5.manhua.163.com/source/"+comicId)
				.header("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36")
				.header("Connection", "keep-alive")
				.get();
			String rawJson = doc.text();
			JsonNode root = JacksonUtil.parseJsonToTree(rawJson);
			JsonNode sectionNodeList = root.get("catalog").get("sections").get(0).get("sections");
			Iterator<JsonNode> sectionIt = sectionNodeList.getElements();
			List<ComicSection> sectionList = new ArrayList<ComicSection>();
			int sort = 1;
			while(sectionIt.hasNext()) {
				ComicSection sec = new ComicSection();
				sectionList.add(sec);
				JsonNode section = sectionIt.next();
				sec.setComicId(section.get("bookId").asText());
				sec.setSectionId(section.get("sectionId").asText());
				sec.setSectionName(section.get("title").asText());
				sec.setSort(sort*10+"");
				sort++;
				sec.setSource("NTE");
				sec.setStatus(0);
			}
			return sectionList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
