package com.accenture.findmyapp.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CalcObject { 
	private int n1;
	private int n2;
	private String operator;
	
	public int getNumber1() {
		return n1;
	}
	public void setNumber1(int n1) {
		this.n1 = n1;
	}
	public int getNumber2() {
		return n2;
	}
	public void setNumber2(int n2) {
		this.n2 = n2;
	}
	public String getAnswer() {
		if (this.operator.equals("add")){
			return (n1+n2)+"";	
		} else if (this.operator.equals("subtract")){
			return (n1-n2)+"";	
		}else if (this.operator.equals("divide")){
			if(n1==0){
				return "division by zero";
			} else {
			return (n1/n2)+"";	
			}
		}else if (this.operator.equals("multiply")){
			return (n1*n2)+"";	
		} else {
			return "nothing! (Because I do not know that operator)";
		}
		
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperator() {
		return operator;
	}


	
}
