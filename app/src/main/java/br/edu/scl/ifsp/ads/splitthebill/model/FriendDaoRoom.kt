package br.edu.scl.ifsp.ads.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Friend::class], version = 1)
abstract class FriendDaoRoom : RoomDatabase() {

    companion object Constants {
        const val FRIEND_DATABASE_FILE = "friends_room"
    }

    abstract fun getFriendDao(): FriendDao

}