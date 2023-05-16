package br.edu.scl.ifsp.ads.splitthebill.controller

import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.FriendDao
import br.edu.scl.ifsp.ads.splitthebill.model.FriendDaoRoom
import br.edu.scl.ifsp.ads.splitthebill.view.BillActivity

class BillController(private val billActivity: BillActivity) {
    private val friendDaoImpl: FriendDao = Room.databaseBuilder(
        billActivity, FriendDaoRoom::class.java, FriendDaoRoom.FRIEND_DATABASE_FILE
    ).build().getFriendDao()

    fun getFriends() {
        Thread {
            val billsList = friendDaoImpl.retrieveFriends()
            billActivity.runOnUiThread {
                billActivity.updateBillList(billsList)
            }
        }.start()
    }
}