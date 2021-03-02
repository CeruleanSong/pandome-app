package io.emawa.pandome.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.emawa.pandome.R
import io.emawa.pandome.data.type.FilePreview

class PreviewAdapter(private val list: ArrayList<FilePreview>)
	: RecyclerView.Adapter<PreviewAdapter.ViewHolder>() {
 
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
		: PreviewAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.preview_item, parent, false)
        return ViewHolder(v)
    }
 
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: PreviewAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
 
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }
 
    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
 
        fun bindItems(file: FilePreview) {
            val fileName = itemView.findViewById(R.id.file_name) as TextView
            val fileType = itemView.findViewById(R.id.file_Type) as TextView
            val fileSize = itemView.findViewById(R.id.file_size) as TextView
            val fileProtected
				= itemView.findViewById(R.id.file_protected) as TextView

            fileName.text = file.fileName
            fileType.text = file.fileType
            fileSize.text = "${file.bytes}MB"
            fileProtected.text = if (file.fileProtected) "private" else  "public"
        }
    }
}
 