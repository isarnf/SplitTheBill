package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.TileFriendBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Friend

class FriendAdapter(
    context: Context,
    private val friendList: MutableList<Friend>
) : ArrayAdapter<Friend>(context, R.layout.tile_friend, friendList) {
    private lateinit var tfb: TileFriendBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val friend: Friend = friendList[position]
        var tileFriendView = convertView
        if (tileFriendView == null) {
            tfb = TileFriendBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            tileFriendView = tfb.root

            val tfvh: TileFriendViewHolder = TileFriendViewHolder(
                tfb.nameTv,
                tfb.amountInReaisTv
            )
            tileFriendView.tag = tfvh
        }

        with(tileFriendView.tag as TileFriendViewHolder) {
            nameTv.text = friend.name
            amountInReaisTv.text = "Spent R$ " + String.format("%.2f", friend.amountSpent)
        }
        return tileFriendView
    }

    private data class TileFriendViewHolder(
        val nameTv: TextView,
        val amountInReaisTv: TextView
    )
}