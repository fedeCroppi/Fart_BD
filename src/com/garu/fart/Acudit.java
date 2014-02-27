package com.garu.fart;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Acudit implements Comparable<Object>, Serializable {

	private Integer num;
	private String titol, cos;
	private Float rate;
	
	public Acudit(){
		this(null, "", "", null);
	}

	public Acudit(Integer num, String titol, String cos, Float rate) {
		this.num = num;
		this.titol = titol;
		this.cos = cos;
		this.rate = rate;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}
	
	public String getCos() {
		return cos;
	}

	public void setCos(String cos) {
		this.cos = cos;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	@Override
	public int compareTo(Object another) {
		return this.getRate().compareTo(((Acudit)another).getRate());
	}
}