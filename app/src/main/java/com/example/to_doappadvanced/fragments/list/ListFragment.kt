package com.example.to_doappadvanced.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doappadvanced.R
import com.example.to_doappadvanced.data.models.ToDoData
import com.example.to_doappadvanced.data.viewmodel.ToDoViewModel
import com.example.to_doappadvanced.databinding.FragmentListBinding
import com.example.to_doappadvanced.fragments.ShareViewModel
import com.example.to_doappadvanced.fragments.list.adapter.ListAdapter
import com.example.to_doappadvanced.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mShareViewModel: ShareViewModel by viewModels()

    private  var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListAdapter by lazy { ListAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mShareViewModel = mShareViewModel
        binding.activeTasksBtn.setTextColor(resources.getColor(R.color.red))

        setupRecyclerview()

        mToDoViewModel.getAllDataComplete.observe(viewLifecycleOwner, Observer { data ->
            mShareViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        setHasOptionsMenu(true)

        hideKeyboard(requireActivity())

        binding.completedTasksBtn.setOnClickListener {

            binding.completedTasksBtn.setTextColor(resources.getColor(R.color.red))
            binding.activeTasksBtn.setTextColor(resources.getColor(R.color.darkGray))
            completedTasks()
        }

        binding.activeTasksBtn.setOnClickListener {
            binding.activeTasksBtn.setTextColor(resources.getColor(R.color.red))
            binding.completedTasksBtn.setTextColor(resources.getColor(R.color.darkGray))
            activeTasks()
        }
        return binding.root

    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = SlideInRightAnimator().apply {
            addDuration = 500
            removeDuration = 500
            changeDuration = 100
        }

        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view:View, deletedItem: ToDoData, position: Int) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority.observe(this, Observer { adapter.setData(it) })
            R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority.observe(this, Observer { adapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed All",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    private fun completedTasks() {
        mToDoViewModel.getAllDataCompleted.observe(viewLifecycleOwner, Observer { data ->
            mShareViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data) })
        }

    private fun activeTasks() {
        mToDoViewModel.getAllDataComplete.observe(viewLifecycleOwner, Observer { data ->
            mShareViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}