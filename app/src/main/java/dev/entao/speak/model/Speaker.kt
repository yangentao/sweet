package dev.entao.speak.model

import dev.entao.base.Length
import dev.entao.sql.*
import dev.entao.page.FormOptions

class Speaker : Model() {

	@PrimaryKey
	@AutoInc
	var id: Long by model

	@Index
	@Length(32)
	var name: String by model

	@Index
	@Length(32)
	var phone: String by model

	@Length(64)
	var userId: Long by model

	@FormOptions("0:Normal", "1:Deleted")
	var flag: Int by model

	companion object : ModelClass<Speaker>()
}