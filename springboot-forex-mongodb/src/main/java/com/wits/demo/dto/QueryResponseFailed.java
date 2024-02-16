package com.wits.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class QueryResponseFailed {

	@Schema(description = "error")
	@JsonProperty("error")
	private QueryResponse error;

	public QueryResponse getError() {
		return error;
	}

	public void setError(QueryResponse error) {
		this.error = error;
	}

}
