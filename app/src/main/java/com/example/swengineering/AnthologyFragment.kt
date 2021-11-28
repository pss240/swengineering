package com.example.swengineering

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_anthology.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AnthologyFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anthology, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item = arrayListOf(
            Data_Anthology("Title1","User1","10"),
            Data_Anthology("Title2","User2","10"),
            Data_Anthology("Title3","User3","10"),
            Data_Anthology("Title4","User4","10"),
            Data_Anthology("Title5","User5","10"),
            Data_Anthology("Title6","User6","10"),
            Data_Anthology("Title7","User7","10"),
            Data_Anthology("Title8","User8","10"),
            Data_Anthology("Title9","User9","10"),
            Data_Anthology("Title10","User10","10"),
            Data_Anthology("Title11","User11","10"),
            Data_Anthology("Title12","User12","10"),
            Data_Anthology("Title13","User13","10"),
            Data_Anthology("Title14","User14","10"),

            )
        recyclerview_Anthology.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_Anthology.adapter = CustomAdapter_Anthology(item,requireContext(),view)
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            AnthologyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}