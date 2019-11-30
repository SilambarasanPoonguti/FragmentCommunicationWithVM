package com.silambar.fragmentcommunicationwithvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_person_b.*

class PersonB : Fragment() {

    private lateinit var personViewModel: PersonViewModel
    private lateinit var messageAdapter: MessageAdapter
    private val messages = ArrayList<String>()
    private var message: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_b, container, false)
    }

    override fun onResume() {
        super.onResume()

        personViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                PersonViewModel::class.java
            )


        sendBtn.setOnClickListener {
            val msg = msgBox.text.toString()
            if (msg.isNotEmpty() && !msg.contentEquals(message)) {
                personViewModel.messageA.value = msg
                msgBox.setText("")
                message = msg
            }
        }

        personViewModel.messageB.observe(requireActivity(), Observer<String> { msg ->
            messages.add(msg)
            messageAdapter.notifyItemInserted(messages.size)
        })

        messageAdapter = MessageAdapter(messages)
        val lManager = LinearLayoutManager(requireActivity()).apply { reverseLayout = true }
        msgListB.apply {
            layoutManager = lManager
            adapter = messageAdapter
        }

    }
}