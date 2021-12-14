package com.matheus.gerenciadorcontacorrente.view.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.matheus.gerenciadorcontacorrente.R
import com.matheus.gerenciadorcontacorrente.data.localstore.Actions
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ExtratoAdapter(private val action: List<Actions>, var clickItemListener : OnClickItemListener) : RecyclerView.Adapter<ExtratoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.extrato_item,parent, false)

        return ViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = action[position]
        holder.initialize(item,clickItemListener)
        holder.bindView(item)
    }

    override fun getItemCount(): Int = action.size

    class ViewHolder(itemView: View, parentContext: Context) : RecyclerView.ViewHolder(itemView){
        val valor = itemView.findViewById<TextView>(R.id.valor)
        val data = itemView.findViewById<TextView>(R.id.data)
        val descricao = itemView.findViewById<TextView>(R.id.descricao)
        val context = parentContext


        fun bindView(item: Actions) = with(itemView){
            setValue(item)
        }
        fun initialize(item: Actions, action: OnClickItemListener){
            setValue(item)
            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }

        fun setValue(item:Actions){
            val valorFormatado = if(item.value!! < 0) "R$ (${item.value})" else "R$ ${item.value}"
            valor.text = valorFormatado
            val dataFormatada = item.date?.format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mm"))
            data.text = dataFormatada.toString()
            descricao.text = item.description
        }

    }

    interface OnClickItemListener{
        fun onItemClick(items: Actions, position: Int)
    }

}