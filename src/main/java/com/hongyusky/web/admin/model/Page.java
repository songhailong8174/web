package com.hongyusky.web.admin.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 分页对象
  @description:
  @version:0.1
  @author:Cherry
  @date:2013-6-25
 */
public class Page {
	/**
	 * 页码
	 */
	private int pageIndex = 1;
	/**
	 * 每页显示的记录数,默认是10条
	 */
	private int pageSize = 10;
	/**
	 * 页总数
	 */
	private int pageCount = 0;
	/**
	 * 记录数
	 */
	private int rowCount = 0;
	
	private Object orderCondition;
	
	/**得到总条数
	 * @return
	 */
	public int getRowCount() {
		return rowCount;
	}
	/**设置总条数
	 * @param rowCount 
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		setPageCount();
	}
	/**
	 * @param pageIndex	 页码
	 * @param pageSize	 每页条数
	 */
	public Page(int pageIndex, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	/**
	 * @param pageIndex		页码
	 * @param pageSize		每页条数
	 * @param pageCount		总页数
	 * @param rowCount		总条数
	 */
	public Page(int pageIndex, int pageSize, int pageCount, int rowCount) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.pageCount = pageCount;
		this.rowCount = rowCount;
	}
	
	public Page(){}
	
	/**得到页码
	 * @return	 
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	/**设置页码
	 * @param pageIndex	
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**得到每页条数
	 * @return	 
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**设置每页条数
	 * @param pageSize	
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 设置总页数
	 */
	protected void setPageCount() {
		if(rowCount>0){
			if(this.rowCount/this.pageSize==0)
				pageCount=1;
			else{
				if(this.rowCount%this.pageSize==0)
					pageCount=this.rowCount/this.pageSize;
				else
					pageCount=this.rowCount/this.pageSize+1;
			}
		}
		else
			pageCount=0;
	}
	/**得到总页数
	 * @return	 
	 */
	public int getPageCount() {
		return pageCount;
	}
	
	
	public Object getOrderCondition() {
		return orderCondition;
	}
	public void setOrderCondition(Object orderCondition) {
		this.orderCondition = orderCondition;
	}
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
	