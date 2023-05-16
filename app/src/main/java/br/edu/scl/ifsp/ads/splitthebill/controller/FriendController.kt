package br.edu.scl.ifsp.ads.splitthebill.controller

import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.Friend
import br.edu.scl.ifsp.ads.splitthebill.model.FriendDao
import br.edu.scl.ifsp.ads.splitthebill.model.FriendDaoRoom
import br.edu.scl.ifsp.ads.splitthebill.model.FriendDaoRoom.Constants.FRIEND_DATABASE_FILE
import br.edu.scl.ifsp.ads.splitthebill.view.MainActivity

class FriendController(private val mainActivity: MainActivity) {
    private val friendDaoImpl: FriendDao = Room.databaseBuilder(
        mainActivity, FriendDaoRoom::class.java, FRIEND_DATABASE_FILE
    ).build().getFriendDao()

    fun insertFriend(friend: Friend) {
        Thread {
            friendDaoImpl.createFriend(friend)
            val friendsList = friendDaoImpl.retrieveFriends()
            mainActivity.runOnUiThread {
                mainActivity.updateFriendList(friendsList)
            }
        }.start()
    }

    fun getFriends() {
        Thread {
            val friendsList = friendDaoImpl.retrieveFriends()
            mainActivity.runOnUiThread {
                mainActivity.updateFriendList(friendsList)
            }
        }.start()
    }

    fun editFriend(friend: Friend) {
        Thread {
            friendDaoImpl.updateFriend(friend)
            val friendsList = friendDaoImpl.retrieveFriends()
            mainActivity.runOnUiThread {
                mainActivity.updateFriendList(friendsList)
            }
        }.start()
    }

    fun removeFriend(friend: Friend) {
        Thread {
            friendDaoImpl.deleteFriend(friend)
            val friendsList = friendDaoImpl.retrieveFriends()
            mainActivity.runOnUiThread {
                mainActivity.updateFriendList(friendsList)
            }
        }.start()
    }
}






