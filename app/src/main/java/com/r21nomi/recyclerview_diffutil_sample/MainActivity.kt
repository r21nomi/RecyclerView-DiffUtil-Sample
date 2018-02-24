package com.r21nomi.recyclerview_diffutil_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.recyclerview_diffutil_sample.diff_util.DiffUtilActivity
import com.r21nomi.recyclerview_diffutil_sample.paging.PagingActivity
import com.r21nomi.recyclerview_diffutil_sample.paging_room.PagingRoomActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diffUtil.setOnClickListener {
            startActivity(Intent(this, DiffUtilActivity::class.java))
        }

        paging.setOnClickListener {
            startActivity(Intent(this, PagingActivity::class.java))
        }

        pagingRoom.setOnClickListener {
            startActivity(Intent(this, PagingRoomActivity::class.java))
        }
    }
}
