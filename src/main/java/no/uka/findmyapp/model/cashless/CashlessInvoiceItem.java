package no.uka.findmyapp.model.cashless;

public class CashlessInvoiceItem {
	
	private String invoiceNo; 	// Should not be here
	private String productId;
	private int quantitySold;
	private int totalAmount;
	private String userName; 	// ex: "kasse226" - should not be here
	private String productName;	// ex: "0,5L Beer"
	
	
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductId() {
		return productId;
	}
	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}
	public int getQuantitySold() {
		return quantitySold;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductName() {
		return productName;
	}
	
}
