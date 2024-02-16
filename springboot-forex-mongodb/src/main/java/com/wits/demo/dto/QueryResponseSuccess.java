package com.wits.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class QueryResponseSuccess {

	@Schema(description = "error")
	@JsonProperty("error")
	private QueryResponse error;
	@Schema(description = "currency")
	@JsonProperty("currency")
	private List<QueryForexResult> currency;

	public QueryResponse getError() {
		return error;
	}

	public void setError(QueryResponse error) {
		this.error = error;
	}

	public List<QueryForexResult> getCurrency() {
		return currency;
	}

	public void setCurrency(List<QueryForexResult> currency) {
		this.currency = currency;
	}

}
