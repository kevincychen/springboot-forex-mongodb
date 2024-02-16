package com.wits.demo.dto;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Document
public class Collection {

	@Schema(description = "Date")
	@JsonProperty("Date")
	private String date;
	@Schema(description = "USD/NTD")
	@JsonProperty("USD/NTD")
	private String usdNtd;
	@Schema(description = "RMB/NTD")
	@JsonProperty("RMB/NTD")
	private String rmbNtd;
	@Schema(description = "EUR/USD")
	@JsonProperty("EUR/USD")
	private String eurUsd;
	@Schema(description = "USD/JPY")
	@JsonProperty("USD/JPY")
	private String usdJpy;
	@Schema(description = "GBP/USD")
	@JsonProperty("GBP/USD")
	private String gbpUsd;
	@Schema(description = "AUD/USD")
	@JsonProperty("AUD/USD")
	private String audUsd;
	@Schema(description = "USD/HKD")
	@JsonProperty("USD/HKD")
	private String usdHkd;
	@Schema(description = "USD/RMB")
	@JsonProperty("USD/RMB")
	private String usdRmb;
	@Schema(description = "USD/ZAR")
	@JsonProperty("USD/ZAR")
	private String usdZar;
	@Schema(description = "NZD/USD")
	@JsonProperty("NZD/USD")
	private String nzdUsd;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsdNtd() {
		return usdNtd;
	}

	public void setUsdNtd(String usdNtd) {
		this.usdNtd = usdNtd;
	}

	public String getRmbNtd() {
		return rmbNtd;
	}

	public void setRmbNtd(String rmbNtd) {
		this.rmbNtd = rmbNtd;
	}

	public String getEurUsd() {
		return eurUsd;
	}

	public void setEurUsd(String eurUsd) {
		this.eurUsd = eurUsd;
	}

	public String getUsdJpy() {
		return usdJpy;
	}

	public void setUsdJpy(String usdJpy) {
		this.usdJpy = usdJpy;
	}

	public String getGbpUsd() {
		return gbpUsd;
	}

	public void setGbpUsd(String gbpUsd) {
		this.gbpUsd = gbpUsd;
	}

	public String getAudUsd() {
		return audUsd;
	}

	public void setAudUsd(String audUsd) {
		this.audUsd = audUsd;
	}

	public String getUsdHkd() {
		return usdHkd;
	}

	public void setUsdHkd(String usdHkd) {
		this.usdHkd = usdHkd;
	}

	public String getUsdRmb() {
		return usdRmb;
	}

	public void setUsdRmb(String usdRmb) {
		this.usdRmb = usdRmb;
	}

	public String getUsdZar() {
		return usdZar;
	}

	public void setUsdZar(String usdZar) {
		this.usdZar = usdZar;
	}

	public String getNzdUsd() {
		return nzdUsd;
	}

	public void setNzdUsd(String nzdUsd) {
		this.nzdUsd = nzdUsd;
	}

}