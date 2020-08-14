package com.example.to_doappadvanced.fragments.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.to_doappadvanced.R
import com.example.to_doappadvanced.data.models.ToDoData
import com.example.to_doappadvanced.data.viewmodel.ToDoViewModel
import com.example.to_doappadvanced.fragments.ShareViewModel
import kotlinx.android.synthetic.main.fragment_save.view.*

class SaveFragment : Fragment() {

    private val args by navArgs<SaveFragmentArgs>()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_save, container, false)

        view.task_save.text = args.currentItem.title

        val complete = !args.currentItem.complete
        val updatedItem = ToDoData(
            args.currentItem.id,
            args.currentItem.title,
            args.currentItem.priority,
            args.currentItem.description,
            complete
        )
        mToDoViewModel.updateData(updatedItem)
        Toast.makeText(requireContext(), "Successfully completed!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_saveFragment_to_listFragment)
        return view
    }
}