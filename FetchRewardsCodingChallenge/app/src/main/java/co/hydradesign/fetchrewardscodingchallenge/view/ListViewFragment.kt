package co.hydradesign.fetchrewardscodingchallenge.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.hydradesign.fetchrewardscodingchallenge.R
import co.hydradesign.fetchrewardscodingchallenge.databinding.ListViewFragmentBinding
import co.hydradesign.fetchrewardscodingchallenge.viewModel.ItemRecyclerAdapter
import co.hydradesign.fetchrewardscodingchallenge.viewModel.ListViewViewModel

class ListViewFragment : Fragment() {

	private lateinit var viewModel : ListViewViewModel

	private lateinit var itemList : RecyclerView
	private lateinit var linearLayoutManager : LinearLayoutManager
	private lateinit var adapter : ItemRecyclerAdapter

	// Binding Variables
	private var _binding : ListViewFragmentBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?,
		savedInstanceState : Bundle?
	) : View? {

		// Inflater
		_binding = ListViewFragmentBinding.inflate( layoutInflater, container, false )

		viewModel = ListViewViewModel()

		setupViewBinding()
		setupRecyclerView()
		setupObservers()

		return binding.root
	}

	private fun setupViewBinding() {
		itemList = binding.recycler
	}

	private fun setupRecyclerView() {

		linearLayoutManager = LinearLayoutManager(context)
		itemList.layoutManager = linearLayoutManager

		adapter = ItemRecyclerAdapter()

		itemList.adapter = adapter
	}

	private fun setupObservers() {

		viewModel.itemData.observe( viewLifecycleOwner, Observer {

			it?.let {

				if ( it.isNotEmpty() ) {
					adapter.submitList(it)
				}
			}
		} )
	}
}