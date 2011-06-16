package com.accenture.findmyapp.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestObject { 
	
	public int getTest1() {
		return test1;
	}
	public void setTest1(int test1) {
		this.test1 = test1;
	}
	public String getTest2() {
		return test2;
	}
	public void setTest2(String test2) {
		this.test2 = test2;
	}
	private int test1;
	private String test2;
	
}
