package br.edu.scl.ifsp.ads.splitthebill.view

import android.os.Bundle
import br.edu.scl.ifsp.ads.splitthebill.adapter.BillAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.BillController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityBillBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Friend

class BillActivity : BaseActivity() {
    private val abb: ActivityBillBinding by lazy {
        ActivityBillBinding.inflate(layoutInflater)
    }

    private val billList: MutableList<Friend> = mutableListOf()

    private val billAdapter: BillAdapter by lazy {
        BillAdapter(
            this, billList
        )
    }

    private val billController: BillController by lazy {
        BillController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(abb.root)

        supportActionBar?.subtitle = "Bill division"
        billController.getFriends()
        abb.billsLv.adapter =
            billAdapter
    }

    fun updateBillList(_billList: MutableList<Friend>) {
        billList.clear()
        billList.addAll(_billList)
        billAdapter.notifyDataSetChanged()
    }
}