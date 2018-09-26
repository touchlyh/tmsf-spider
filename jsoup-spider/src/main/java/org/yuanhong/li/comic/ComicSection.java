package org.yuanhong.li.comic;

public class ComicSection {

	private Long id;
	
	private String source;
	
    private String comicId;
    
    private String sectionId;

    private String sectionName;

    private String sort;
    
    private Integer status;//0初始化，1已下载，2已同步

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComicId() {
		return comicId;
	}

	public void setComicId(String comicId) {
		this.comicId = comicId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
    
}
