package io.emawa.pandome.ui.common

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class ItemAdapter<T : RecyclerView.ViewHolder>
	(@LayoutRes open val layoutId: Int) {

	abstract fun onCreateViewHolder(itemView: View): T

	@Suppress("UNCHECKED_CAST")
	fun bindViewHolder(holder: RecyclerView.ViewHolder) {
		(holder as T).onBindViewHolder()
	}

	protected abstract fun T.onBindViewHolder()
}