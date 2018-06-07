package ACQUA.inventory.model;

import java.util.Date;

public class RawMaterial {
	private String recordID;
	private String RMRecordID;
	private String RMCategory;
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
	public String getRMRecordID() {
		return RMRecordID;
	}
	public void setRMRecordID(String rMRecordID) {
		RMRecordID = rMRecordID;
	}
	public String getRMCategory() {
		return RMCategory;
	}
	public void setRMCategory(String rMCategory) {
		RMCategory = rMCategory;
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
