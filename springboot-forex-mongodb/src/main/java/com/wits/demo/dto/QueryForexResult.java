package com.wits.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class QueryForexResult {

	@Schema(description = "date")
	@JsonProperty("date")
	private String date;
	@Schema(description = "usd")
	@JsonProperty("usd")
	private String usd;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUsd() {
		return usd;
	}
	public void setUsd(String usd) {
		this.usd = usd;
	}

}
