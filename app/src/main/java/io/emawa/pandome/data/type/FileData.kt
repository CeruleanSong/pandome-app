package io.emawa.pandome.data.type

import java.util.Date;

class FileData (
	val fileID: String,
	val fileName: String,
	val fileType: String,
	val fileProtected: Boolean,
	val fileHidden: Boolean,
	val downloads: Int,
	val views: Int,
	val bytes: Int,
	val create_date: Date,
    val expire_date: Date,
	val sha256: String,
	val md5: String,
)