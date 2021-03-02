package io.emawa.pandome.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.emawa.pandome.R
import io.emawa.pandome.data.type.FilePreview
import io.emawa.pandome.ui.common.PreviewAdapter
import java.io.InputStream
import java.io.OutputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.net.URI

class MainActivity : AppCompatActivity() {

    lateinit var previews: ArrayList<FilePreview>
	lateinit var adapter: PreviewAdapter
	lateinit var add_fab: FloatingActionButton
	lateinit var previewList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

		previewList = findViewById<RecyclerView>(R.id.preview_list)
		previewList.layoutManager = LinearLayoutManager(
				this,
				RecyclerView.VERTICAL,
				false
		)
 
		previews = ArrayList<FilePreview>()
		adapter = PreviewAdapter(previews)
		add_fab = findViewById<FloatingActionButton>(R.id.fab_add)

		previewList.adapter = adapter

        add_fab.setOnClickListener {

			val fileIntent = Intent()
					.setType("*/*")
					.setAction(Intent.ACTION_GET_CONTENT)

			startActivityForResult(Intent.createChooser(fileIntent, "Select a file"), 20)

		}
    }

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == 20 && resultCode == RESULT_OK) {
			val selectedFile = data?.data
			var fileName: String? = null
			var fileSize: String? = null

			if (selectedFile != null) {
				if (selectedFile.scheme.equals("content")) {
					val cursor = contentResolver.query(selectedFile, null, null, null, null)
					try {
						if (cursor != null && cursor.moveToFirst()) {
							fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
							fileSize = cursor.getString(cursor.getColumnIndex(OpenableColumns.SIZE))
						}
					} finally {
						cursor!!.close()
					}
				}
				if (fileName == null) {
					fileName = selectedFile.path
					try {
						val cut: Int = fileName!!.lastIndexOf('/')

						if (cut != -1) {
							fileName = fileName.substring(cut + 1)
						}
					} catch (err: Error) {
						// do something
					}
				}

				previews.add(FilePreview(
						fileName = fileName.toString(),
						fileID = fileSize.toString(),
						fileType = fileSize.toString(),
						bytes = 123
				));

				adapter.notifyItemInserted(previews.size)

				val toast = Toast.makeText(applicationContext, fileName, Toast.LENGTH_LONG)
				toast.show()
			} else {
				val msg = "Null filename data received!"
				val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG)
				toast.show()
			}
		}
	}
	private fun sendFile(out: OutputStream, name: String, `in`: InputStream, fileName: String) {
		val o = "Content-Disposition: form-data; name=\"" + URLEncoder.encode(name, "UTF-8")
				.toString() + "\"; filename=\"" + URLEncoder.encode(fileName, "UTF-8").toString() + "\"\r\n\r\n"
		out.write(o.toByteArray(StandardCharsets.UTF_8))
		val buffer = ByteArray(2048)
		var n = 0
		while (n >= 0) {
			out.write(buffer, 0, n)
			n = `in`.read(buffer)
		}
		out.write("\r\n".toByteArray(StandardCharsets.UTF_8))
	}

	private fun sendField(out: OutputStream, name: String, field: String) {
		val o = ("Content-Disposition: form-data; name=\""
				+ URLEncoder.encode(name, "UTF-8")) + "\"\r\n\r\n"
		out.write(o.toByteArray(StandardCharsets.UTF_8))
		out.write(URLEncoder.encode(field, "UTF-8").toByteArray(StandardCharsets.UTF_8))
		out.write("\r\n".toByteArray(StandardCharsets.UTF_8))
	}
}