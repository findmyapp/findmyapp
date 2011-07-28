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
	private String saleLocation; // should be changed to a Location object
	
	/**
	 * Amount for sale
	 */
	private int saleTotalAmount;
	
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
		this.saleLocation = location;
	}
	public String getLocation() {
		return saleLocation;
	}
	public void setAmount(int amount) {
		this.saleTotalAmount = amount;
	}
	public int getAmount() {
		return saleTotalAmount;
	}
	public void setProducts(List<CashlessInvoiceItem> products) {
		this.products = products;
	}
	public List<CashlessInvoiceItem> getProducts() {
		return products;
	}
	
}
