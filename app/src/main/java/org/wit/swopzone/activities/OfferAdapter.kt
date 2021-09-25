package org.wit.swopzone.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_offer.view.*
import org.wit.swopzone.R
import org.wit.swopzone.main.MainApp
import org.wit.swopzone.models.OfferListener
import org.wit.swopzone.models.OfferModel
import org.wit.swopzone.models.SwopItemModel
import org.wit.swopzone.models.UserModel

class OfferAdapter constructor(private var user: UserModel,
                               private var swopItemsIn:List<SwopItemModel>,
    private var offersIn:List<OfferModel>,
    private val listener: OfferListener):
    RecyclerView.Adapter<OfferAdapter.MainHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):MainHolder
    {

        return MainHolder(LayoutInflater.from(parent?.context)
            .inflate(R.layout.card_offer,parent,false))
    }

    override fun onBindViewHolder(holder:MainHolder, position:Int){
        val offer = offersIn[holder.adapterPosition]
        var userId=user.id
        holder.bind(userId,swopItemsIn,offer, listener)
    }

    override fun getItemCount(): Int = offersIn.size

    class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(userId:Long,swopItemsIn:List<SwopItemModel>,offer: OfferModel, listener:OfferListener){

                      var giveItemId=offer.giveItemId
                      var receiveItemId=offer.receiveItemId
                      var giveItem:SwopItemModel?=swopItemsIn.find{p->p.id==giveItemId}
                      var receiveItem:SwopItemModel?=swopItemsIn.find{p->p.id==receiveItemId}
            var cardStringOne="error"
            var cardStringTwo="error"
            if(giveItem != null  && receiveItem !=null){
                if(giveItem.ownerId==userId){
                    cardStringOne="My: ${giveItem.name}  (${giveItem.category})"
                    cardStringTwo="For their: ${receiveItem.name}  (${receiveItem.category})"
                }
                else if(receiveItem.ownerId==userId){
                    cardStringOne="Their: ${giveItem.name}  (${giveItem.category})"
                    cardStringTwo="For my: ${receiveItem.name}  (${receiveItem.category})"
                }
            }
            itemView.offer_giveInfo.text=cardStringOne
            itemView.offer_receiveInfo.text=cardStringTwo
            itemView.setOnClickListener { listener.onOfferClick(offer)}
        }
    }
}