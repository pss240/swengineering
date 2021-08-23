package com.example.swengineering

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_anthology.*
import kotlinx.android.synthetic.main.fragment_subscribe.*


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
            Data_Anthology("Title1","User1"),
            Data_Anthology("Title2","User2"),
            Data_Anthology("Title3","User3"),
            Data_Anthology("Title4","User4"),
            Data_Anthology("Title5","User5"),
            Data_Anthology("Title6","User6"),
            Data_Anthology("Title7","User7"),
            Data_Anthology("Title8","User8"),
            Data_Anthology("Title9","User9"),
            Data_Anthology("Title10","User10"),
            Data_Anthology("Title11","User11"),
            Data_Anthology("Title12","User12"),
            Data_Anthology("Title13","User13"),
            Data_Anthology("Title14","User14"),

            )

        recyclerview_Anthology.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_Anthology.adapter = CustomAdapter_Anthology(item,requireContext())



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