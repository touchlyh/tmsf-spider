package org.yuanhong.li.comic.qq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yuanhong.li.comic.ComicInfo;
import org.yuanhong.li.comic.ComicParser;
import org.yuanhong.li.comic.ComicSection;

public class AcqqParser implements ComicParser{

	@Override
	public ComicInfo getMainInfo(String comicId) {
		String url = "http://m.ac.qq.com/comic/index/id/"+comicId;
		try {
			@SuppressWarnings("unused")
			Document doc = Jsoup.connect(url)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.9")
					.header("Cache-Control", "max-age=0")
					.header("Connection", "keep-alive")
					.header("Cookie", "___rl__test__cookies=1538133895118; pgv_pvi=1475834880; OUTFOX_SEARCH_USER_ID_NCOO=1570497525.7691536; pt2gguin=o0398564993; pgv_pvid=3882518120; RK=ebSV3vX1Fl; ptcz=33b9703e3509b2e4faab87e09d37591688d7bda5a5060cd28b1113ec375ce826; pgv_info=ssid=s6408725800; ts_refer=www.google.co.jp/; ts_uid=6617856580; H5_BLOCKER-default=1538115756204; ___rl__test__cookies=1538133888760; ts_last=m.ac.qq.com/comic/index/id/627521")
					.header("Host", "m.ac.qq.com")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
					.get();
			ComicInfo comicInfo = new ComicInfo();
			Element titleEle = doc.selectFirst("body > article > div.lay-head > section.mod-head > div.head-info > div.head-info-detail > ul > li.head-info-title > h1");
			String title = titleEle.text();
			Element authorEle = doc.selectFirst("body > article > div.lay-head > section.mod-head > div.head-info > div.head-info-detail > ul > li.head-info-author");
			String author = authorEle.text();
			Elements tagElements = doc.select("body > article > div.lay-head > section.mod-head > div.head-info > div.head-info-detail > ul > li.head-info-tags > span");
			StringBuilder tgBuilder = new StringBuilder();
			for(Element tagEle : tagElements) {
				tgBuilder.append(tagEle.text()).append(" ");
			}
			Element descriptionEle = doc.selectFirst("div.detail-summary > p");
			String description = descriptionEle.html();
			Element coverEle = doc.selectFirst("body > article > div.lay-head > section.mod-head > div.head-info > div.head-info-cover > img");
			String cover = coverEle.attr("src");
			
			comicInfo.setTitle(title);
			comicInfo.setComicId(comicId);
			comicInfo.setAuthor(author);
			comicInfo.setRawTags(tgBuilder.toString().trim());
			comicInfo.setDescription(description);
			comicInfo.setCover(cover);
			comicInfo.setSource("QQ");
			comicInfo.setStatus(0);
			return comicInfo;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ComicSection> getSectionList(String comicId) {
		String url = "http://m.ac.qq.com/comic/chapterList/id/"+comicId;
		try {
			Document doc = Jsoup.connect(url)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.9")
					.header("Connection", "keep-alive")
					.header("Cookie", "___rl__test__cookies=1538133918991; pgv_pvi=1475834880; OUTFOX_SEARCH_USER_ID_NCOO=1570497525.7691536; pt2gguin=o0398564993; pgv_pvid=3882518120; RK=ebSV3vX1Fl; ptcz=33b9703e3509b2e4faab87e09d37591688d7bda5a5060cd28b1113ec375ce826; pgv_info=ssid=s6408725800; ts_refer=www.google.co.jp/; ts_uid=6617856580; H5_BLOCKER-default=1538115756204; ___rl__test__cookies=1538133888760; ts_last=m.ac.qq.com/comic/index/id/627521")
					.header("Host", "m.ac.qq.com")
					.header("Referer", "http://m.ac.qq.com/comic/index/id/"+comicId)
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
					.get();
			
			List<ComicSection> sectionList = new ArrayList<ComicSection>();
			
			Elements chapterElements = doc.select("body > section.chapter-list-box.list-expanded > ul.chapter-list.normal > li > a");
			for(Element chapterEle : chapterElements) {
				ComicSection sec = new ComicSection();
				sectionList.add(sec);
				sec.setComicId(comicId);
				sec.setSectionId(chapterEle.attr("data-cid"));
				sec.setSectionName(chapterEle.text());
				sec.setSort(Integer.parseInt(chapterEle.attr("data-seq"))*10+"");
				sec.setSource("QQ");
				sec.setStatus(0);
			}
			return sectionList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
