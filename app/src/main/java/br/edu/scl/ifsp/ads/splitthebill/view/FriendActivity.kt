package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityFriendBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Friend

class FriendActivity : BaseActivity() {
    private val afb: ActivityFriendBinding by lazy {
        ActivityFriendBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        supportActionBar?.subtitle = "Friend"

        val receivedFriend = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_FRIEND, Friend::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_FRIEND)
        }

        receivedFriend?.let { _receivedFriend ->
            with(afb) {
                with(_receivedFriend) {
                    nameEt.setText(name)
                    amountSpentEt.setText(amountSpent.toString())
                    itemsEt.setText(purchasedItems)
                }
            }
            val viewFriend = intent.getBooleanExtra(EXTRA_VIEW_FRIEND, false)
            with(afb) {
                nameEt.isEnabled = !viewFriend
                amountSpentEt.isEnabled = !viewFriend
                itemsEt.isEnabled = !viewFriend
                saveBt.visibility = if (viewFriend) View.GONE else View.VISIBLE
            }
        }

        afb.saveBt.setOnClickListener {
            val friend: Friend = Friend(
                id = receivedFriend?.id,
                name = afb.nameEt.text.toString(),
                amountSpent = if (afb.amountSpentEt.text.toString() == "") 0.00 else afb.amountSpentEt.text.toString()
                    .toDouble(),
                amountToPay = 0.00,
                amountToReceive = 0.00,
                purchasedItems = afb.itemsEt.text.toString(),
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_FRIEND, friend)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}


