package com.example.cuatroenraya.activities


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuatroenraya.R
import kotlinx.android.synthetic.main.fragment_right.*


private const val ARG_PARAM1 = "com.example.cuatroenraya"
class RightFragment : Fragment() {
    private var param1: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_right, container, false)
    }
    override fun onStart() {
        super.onStart()
        textViewRF.text = param1
    }
    companion object {
        fun newInstance(param1: String): RightFragment {
            val fragment = RightFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
            return fragment
        }
    }
}
