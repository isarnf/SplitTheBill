package br.edu.scl.ifsp.ads.splitthebill.model

import androidx.room.*

@Dao
interface FriendDao {

    @Insert
    fun createFriend(friend: Friend)

    @Query("SELECT * FROM Friend WHERE id = :id")
    fun retrieveFriend(id: Int): Friend?

    @Query("SELECT * FROM Friend")
    fun retrieveFriends(): MutableList<Friend>

    @Update
    fun updateFriend(friend: Friend): Int

    @Delete
    fun deleteFriend(friend: Friend): Int
}