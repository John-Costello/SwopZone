package org.wit.swopzone.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_swopitem.view.*
import org.wit.swopzone.R
import org.wit.swopzone.helpers.readImageFromPath
import org.wit.swopzone.models.SwopItemListener
import org.wit.swopzone.models.SwopItemModel


class SwopItemAdapter constructor(private var swopitemsIn:List<SwopItemModel>,
                                  private val listener: SwopItemListener):
                                             RecyclerView.Adapter<SwopItemAdapter.MainHolder>()
{

     override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):MainHolder
     {
         return MainHolder(LayoutInflater.from(parent?.context)
                      .inflate(R.layout.card_swopitem,parent,false))
     }

     override fun onBindViewHolder(holder:MainHolder, position:Int){
        val swopItem = swopitemsIn[holder.adapterPosition]
             holder.bind(swopItem, listener)
     }

     override fun getItemCount(): Int = swopitemsIn.size

     class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView)
     {
          fun bind(swopItem: SwopItemModel, listener:SwopItemListener){
              itemView.swopitemName.text=swopItem.name
              itemView.category.text=swopItem.category
              itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context,swopItem.image))
              itemView.setOnClickListener{listener.onSwopItemClick(swopItem)}
          }
     }
}