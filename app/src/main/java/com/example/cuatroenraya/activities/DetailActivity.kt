package com.example.cuatroenraya.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cuatroenraya.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val message = intent.getStringExtra(EXTRA_ARGUMENT)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            val fragment = RightFragment.newInstance(message)
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
    companion object {
        val EXTRA_ARGUMENT = "es.uam.eps.dadm.er12.extra"
        fun newIntent(package_context: Context, message: String): Intent {
            val intent = Intent(package_context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ARGUMENT, message)
            return intent
        }
    }
}
