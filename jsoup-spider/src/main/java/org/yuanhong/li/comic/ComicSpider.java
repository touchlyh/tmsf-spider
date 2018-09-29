package org.yuanhong.li.comic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.yuanhong.li.comic.ComicInfo;
import org.yuanhong.li.comic.ComicSection;
import org.yuanhong.li.repository.ComicService;

public class ComicSpider {
	
	private String source;

	private ComicParser comicParser;
	
	private ComicService comicService;
	
	public boolean doCrawelMainInfo(String comicId) {
		ComicInfo info = comicService.getByComicId(this.source, comicId);
		if(info != null) {
			System.out.println("already exisited in db. comicId="+comicId);
			return true;
		}
		info = comicParser.getMainInfo(comicId);
		if(info == null) {
			System.out.println("http parse main info failed. comicId="+comicId);
			return false;
		}
		info.setSource(this.source);
		boolean result = comicService.saveComicInfo(info);
		if(!result) {
			System.out.println("save main info failed. comicId="+comicId);
		}
		return result;
	}
	
	public boolean doCrawelSections(String comicId) {
		List<ComicSection> httpSectionList = comicParser.getSectionList(comicId);
		if(httpSectionList == null) {
			System.out.println("http get section list empty. comicId="+comicId);
			return false;
		}
		List<ComicSection> dbSectionList = comicService.getSectionList(this.source, comicId);
		if(dbSectionList == null) {
			dbSectionList = new ArrayList<ComicSection>();
		}
		Map<String,ComicSection> dbMapping = new HashMap<String,ComicSection>();
		for(ComicSection section : dbSectionList) {
			dbMapping.put(section.getSectionId(), section);
		}
		Iterator<ComicSection> it = httpSectionList.iterator();
		while (it.hasNext()) {
			ComicSection cur = it.next();
			if(dbMapping.get(cur.getSectionId()) != null) {
				it.remove();
			}
		}
		if(httpSectionList == null || httpSectionList.size() == 0) {
			System.out.println("http section retain db Section is empty. comicId="+comicId);
			return false;
		}
		boolean result = true;
		for(ComicSection section : httpSectionList) {
			section.setSource(this.source);
			boolean saved = comicService.saveSection(section);
			if(!saved) {
				System.out.println("doCrawelSections save single section failed. sectionId="+section.getSectionId());
			}
			result = result && saved;
		}
		return result;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public ComicParser getComicParser() {
		return comicParser;
	}

	public void setComicParser(ComicParser comicParser) {
		this.comicParser = comicParser;
	}

	public ComicService getComicService() {
		return comicService;
	}

	public void setComicService(ComicService comicService) {
		this.comicService = comicService;
	}
	
}
