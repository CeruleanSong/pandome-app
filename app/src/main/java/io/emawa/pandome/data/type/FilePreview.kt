package io.emawa.pandome.data.type

import java.util.Date

data class FilePreview(
		val fileID: String = "",
		val fileName: String = "",
		val fileType: String = "",
		val fileProtected: Boolean = false,
		val fileHidden: Boolean = false,
		val bytes: Int = 0,
		val create_date: Date = Date(0),
		val expire_date: Date = Date(0),
		val inProgress: Boolean = false,
		val progress: Int = 0,
)