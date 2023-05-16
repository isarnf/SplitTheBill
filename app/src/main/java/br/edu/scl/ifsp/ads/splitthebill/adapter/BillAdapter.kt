package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.TileBillBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Friend
import kotlin.math.abs

class BillAdapter(
    context: Context,
    private val friendList: MutableList<Friend>
) : ArrayAdapter<Friend>(context, R.layout.tile_bill, friendList) {
    private lateinit var tbb: TileBillBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val friend: Friend = friendList[position]
        var tileBillView = convertView
        if (tileBillView == null) {
            tbb = TileBillBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            tileBillView = tbb.root

            val tbvh: TileBillViewHolder = TileBillViewHolder(
                tbb.nameTv,
                tbb.amountToPayTv,
                tbb.amountToReceiveTv
            )
            tileBillView.tag = tbvh
        }

        val bill = calculateIndividualBill(friendList, friend)


        with(tileBillView.tag as TileBillViewHolder) {
            nameTv.text = friend.name
            if (bill > 0.00) amountToReceiveTv.visibility = View.GONE
            if (bill < 0.00) amountToPayTv.visibility = View.GONE
            amountToPayTv.text = "Must PAY: R$ " + String.format("%.2f", bill)
            amountToReceiveTv.text = "Must RECEIVE: R$ " + String.format("%.2f", (abs(bill)))
        }
        return tileBillView
    }

    private data class TileBillViewHolder(
        val nameTv: TextView,
        val amountToPayTv: TextView,
        val amountToReceiveTv: TextView
    )

    private fun calculateIndividualBill(friends: MutableList<Friend>, friend: Friend): Double {
        var totalBill = 0.00
        var bill = 0.00
        for (friend in friends) {
            bill = friend.amountSpent
            totalBill += bill
        }

        val dividedBill = totalBill / friends.size

        val individualBill = dividedBill - friend.amountSpent
        return individualBill
    }
}
