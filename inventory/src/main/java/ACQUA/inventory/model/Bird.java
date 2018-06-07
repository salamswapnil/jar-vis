package ACQUA.inventory.model;

import java.util.Date;

public class Bird {
	private String recordID;
	private String birdRecordID;
	private String birdName;
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
	public String getBirdRecordID() {
		return birdRecordID;
	}
	public void setBirdRecordID(String birdRecordID) {
		this.birdRecordID = birdRecordID;
	}
	public String getBirdName() {
		return birdName;
	}
	public void setBirdName(String birdName) {
		this.birdName = birdName;
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
