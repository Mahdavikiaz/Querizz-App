package com.example.querizz_app.data.response

import com.google.gson.annotations.SerializedName

data class SumResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class ResultsItem(

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("model")
	val model: String? = null,

	@field:SerializedName("label")
	val label: String? = null
)
