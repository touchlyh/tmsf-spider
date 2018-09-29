package org.yuanhong.li.comic;

import java.util.List;

/**
 * 抽象的comicParser接口
 * @author yuanhong.li
 *
 */
public interface ComicParser {
	public ComicInfo getMainInfo(String comicId);
	public List<ComicSection> getSectionList(String comicId);
}
