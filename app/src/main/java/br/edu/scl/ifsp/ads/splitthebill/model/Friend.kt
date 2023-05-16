package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Friend(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @NonNull var name: String,
    var amountSpent: Double,
    @NonNull var amountToPay: Double,
    @NonNull var amountToReceive: Double,
    var purchasedItems: String,
) : Parcelable


