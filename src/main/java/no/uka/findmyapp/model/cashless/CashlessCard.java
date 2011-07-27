package no.uka.findmyapp.model.cashless;

import java.util.List;

import net.sourceforge.jtds.jdbc.DateTime;

public class CashlessCard {
	
	private long cardNo;
	private int balance;
	private DateTime lastUsed;
	private List<CashlessInvoice> transactions;
	
	public CashlessCard(long cardNo) {
		this.setCardNo(cardNo);
	}
	
	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}
	public long getCardNo() {
		return cardNo;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getBalance() {
		return balance;
	}
	
	public void setLastUsed(DateTime lastUsed) {
		this.lastUsed = lastUsed;
	}
	public DateTime getLastUsed() {
		return lastUsed;
	}
	
	public void setTransactions(List<CashlessInvoice> transactions) {
		this.transactions = transactions;
	}
	public List<CashlessInvoice> getTransactions() {
		return transactions;
	}
	
}
