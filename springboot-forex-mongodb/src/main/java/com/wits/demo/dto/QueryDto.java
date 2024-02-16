package com.wits.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class QueryDto {

	@Schema(description = "startDate")
	@JsonProperty("startDate")
	private String startDate;
	@Schema(description = "endDate")
	@JsonProperty("endDate")
	private String endDate;
	@Schema(description = "currency")
	@JsonProperty("currency")
	private String currency;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
