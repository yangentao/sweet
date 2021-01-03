package dev.entao.speak.model

import dev.entao.base.Length
import dev.entao.sql.*

class Account : Model() {

	@PrimaryKey
	@AutoInc
	var id: Long by model

	@Unique
	@Index
	@Length(32)
	var phone: String by model

	@Length(64)
	var pwd: String by model

	companion object : ModelClass<Account>()
}