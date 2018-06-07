package ACQUA.inventory.model;

import java.util.Date;

public class Fish {
	private String recordID;
	private String fishRecordID;
	private String fishCategory;
	private String batchNo;
	private Double rate;
	private int quantity;
	private Date purchaseDate;
	private Date dateAdded;
	private Date lastModifiedDate;
	
	public String getRecordID() {
		return recordID;
	}
	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
	public String getFishRecordID() {
		return fishRecordID;
	}
	public void setFishRecordID(String fishRecordID) {
		this.fishRecordID = fishRecordID;
	}
	public String getFishCategory() {
		return fishCategory;
	}
	public void setFishCategory(String fishCategory) {
		this.fishCategory = fishCategory;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
}
