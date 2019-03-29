package com.example.cuatroenraya.fragmentsTutorial

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cuatroenraya.R
import kotlinx.android.synthetic.main.activity_fragment.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        //Con esto funciona si lo invoco desde unn uintent normal
        //val intent = newIntent(this, "Probando cosas")
        val message = intent.getStringExtra(EXTRA_ARGUMENT)
        val fm = supportFragmentManager
        if (fragment_detail_container == null) {
            val fragment = RightFragment.newInstance(message)
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
    companion object {
        val EXTRA_ARGUMENT = "com.example.cuatroenraya"
        fun newIntent(package_context: Context, message: String): Intent {
            val intent = Intent(package_context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ARGUMENT, message)
            return intent
        }
    }
}
