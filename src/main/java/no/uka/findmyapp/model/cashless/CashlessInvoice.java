package no.uka.findmyapp.model.cashless;

import java.sql.Timestamp;
import java.util.List;;

public class CashlessInvoice {
	
	/**
	 * Field that identifies a sale
	 */
	private String invoiceNo;
	
	/**
	 * Timestamp for the sale
	 */
	private Timestamp saleTime;
	
	/**
	 * Location for the sale
	 */
	private String location; // should be changed to a Location object
	
	/**
	 * Card balance after the sale
	 */
	private int cardBalanceAfter;
	
	/**
	 * List of items bought
	 */
	private List<CashlessInvoiceItem> products;
	
	
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setSaleTime(Timestamp saleTime) {
		this.saleTime = saleTime;
	}
	public Timestamp getSaleTime() {
		return saleTime;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setCardBalanceAfter(int cardBalanceAfter) {
		this.cardBalanceAfter = cardBalanceAfter;
	}
	public int getCardBalanceAfter() {
		return cardBalanceAfter;
	}
	public void setProducts(List<CashlessInvoiceItem> products) {
		this.products = products;
	}
	public List<CashlessInvoiceItem> getProducts() {
		return products;
	}
	
}
