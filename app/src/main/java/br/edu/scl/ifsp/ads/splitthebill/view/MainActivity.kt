package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.adapter.FriendAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.FriendController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Friend

class MainActivity : BaseActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val friendList: MutableList<Friend> = mutableListOf()

    private val friendAdapter: FriendAdapter by lazy {
        FriendAdapter(
            this, friendList
        )
    }

    private lateinit var farl: ActivityResultLauncher<Intent>

    private val friendController: FriendController by lazy {
        FriendController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        supportActionBar?.subtitle = "Friends"
        friendController.getFriends()
        amb.friendsLv.adapter =
            friendAdapter

        farl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val friend = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(EXTRA_FRIEND, Friend::class.java)
                } else {
                    result.data?.getParcelableExtra(EXTRA_FRIEND)
                }
                friend?.let { _friend ->
                    val position = friendList.indexOfFirst { it.id == _friend.id }
                    if (position != -1) {
                        friendList[position] = _friend
                        friendController.editFriend(_friend)
                        Toast.makeText(this, "Friend edited!", Toast.LENGTH_LONG).show()
                    } else {
                        friendController.insertFriend(_friend)
                        friendController.getFriends()
                        Toast.makeText(this, "Friend added!", Toast.LENGTH_LONG).show()
                    }
                    friendAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.friendsLv)


        amb.friendsLv.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val friend = friendList[position]
                val friendIntent = Intent(this@MainActivity, FriendActivity::class.java)
                friendIntent.putExtra(EXTRA_FRIEND, friend)
                friendIntent.putExtra(EXTRA_VIEW_FRIEND, true)
                startActivity(friendIntent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addFriendMi -> {
                farl.launch(Intent(this, FriendActivity::class.java))
                true
            }
            R.id.moneyDivisionMi -> {
                farl.launch(Intent(this, BillActivity::class.java))
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val friend = friendList[position]

        return when (item.itemId) {
            R.id.removeFriendMi -> {
                friendList.removeAt(position)
                friendController.removeFriend(friend)
                friendAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Friend removed!", Toast.LENGTH_LONG).show()
                true
            }
            R.id.editFriendMi -> {

                val friendIntent = Intent(this, FriendActivity::class.java)
                friendIntent.putExtra(EXTRA_FRIEND, friend)
                farl.launch(friendIntent)
                true
            }
            else -> {
                false
            }
        }
    }

    fun updateFriendList(_friendList: MutableList<Friend>) {
        friendList.clear()
        friendList.addAll(_friendList)
        friendAdapter.notifyDataSetChanged()
    }

}

