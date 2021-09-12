package com.example.swengineering

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_subscriber.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SubscriberFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_subscriber, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item = arrayListOf(
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User1"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User2"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User3"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User4"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User5"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User6"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User7"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User8"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User9"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User10"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User11"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User12"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User13"),
            Data_Subscriber(R.drawable.ic_baseline_person_24,"User14"),
            )
        recyclerview_subscriber.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_subscriber.adapter = CustomAdapter_Subscriber(item,requireContext(), view)
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            SubscriberFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}