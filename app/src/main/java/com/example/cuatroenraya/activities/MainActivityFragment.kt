package com.example.cuatroenraya.activities
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cuatroenraya.R
import kotlinx.android.synthetic.main.activity_fragment.*

class MainActivityFragment : AppCompatActivity(), LeftFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(string: String) {
        val fm = supportFragmentManager
        if (fragment_detail_container != null) {
            val rightFragment = RightFragment.newInstance("Button pressed")
            fm.beginTransaction().add(R.id.fragment_detail_container,
                rightFragment).commit()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fm = supportFragmentManager
        val fragment = LeftFragment()
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
    }
}
