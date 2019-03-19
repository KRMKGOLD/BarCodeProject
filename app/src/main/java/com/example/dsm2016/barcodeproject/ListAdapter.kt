package com.example.dsm2016.barcodeproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(private val context: Context, val codeList : ArrayList<codeData>) : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int = codeList.size

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(codeList[position])
    }

    inner class ListHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val codeImage = itemView.findViewById<ImageView>(R.id.listImage)
        val codeText = itemView.findViewById<TextView>(R.id.listText)
        val codeCheckbox = itemView.findViewById<CheckBox>(R.id.rowCheckButton)

        fun bind(toBindList : codeData){
            codeImage.setImageBitmap(toBindList.codeImage)
            codeText.text = toBindList.content
            codeCheckbox.isChecked = toBindList.checked

            codeCheckbox.setOnClickListener {
                codeList[adapterPosition].checked = !(codeList[adapterPosition].checked)
            }

            codeText.movementMethod = ScrollingMovementMethod()
        }
    }
}