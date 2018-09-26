package org.yuanhong.li;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yuanhong.li.comic.nte.NteComicParser;
import org.yuanhong.li.comic.nte.NteComicSpider;
import org.yuanhong.li.repository.ComicService;

public class ApplicationComic {

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spider-datasource.xml");
		ComicService comicService = (ComicService) context.getBean("comicService");
		NteComicSpider nteSpider = new NteComicSpider();
		nteSpider.setComicService(comicService);
		nteSpider.setNteComicParser(new NteComicParser());
		String comicId = "5320624447990050041";
		nteSpider.doCrawelMainInfo(comicId);
		nteSpider.doCrawelSections(comicId);
	}
}
