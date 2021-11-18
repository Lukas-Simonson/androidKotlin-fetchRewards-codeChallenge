package co.hydradesign.fetchrewardscodingchallenge.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.hydradesign.fetchrewardscodingchallenge.databinding.CardLayoutListGroupHeaderBinding
import co.hydradesign.fetchrewardscodingchallenge.databinding.CardLayoutListItemRecyclerBinding
import co.hydradesign.fetchrewardscodingchallenge.model.HeaderItem
import co.hydradesign.fetchrewardscodingchallenge.model.ListItem

class ItemRecyclerAdapter() : ListAdapter<Any, RecyclerView.ViewHolder>( ItemDiffCallback() ) {

	// Gets the Items View Type
	override fun getItemViewType(position: Int): Int {

		// Checks what type the item is.
		if ( getItem( position ) is ListItem ) {

			return ViewTypes.ITEM_TYPE.typeValue    // List Item
		}

		return ViewTypes.HEADER_TYPE.typeValue      // Header Item
	}

	// Binds the ViewHolders
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

		// Selects the correct viewHolder based on the type of item given.
			// List Item
		if ( getItem( position ) is ListItem ) {
			holder as ItemViewHolder
			holder.bind( getItem( position ) as ListItem )
		}
			// Header Item
		else if ( getItem( position ) is HeaderItem ) {
			holder as GroupTitleViewHolder
			holder.bind(getItem(position) as HeaderItem)
		}
	}

	// Types of Views in this Recycler View
	enum class ViewTypes( val typeValue : Int ) {
		ITEM_TYPE( 0 ),
		HEADER_TYPE(1)
	}

	// Item View Holder
	class ItemViewHolder private constructor(val binding: CardLayoutListItemRecyclerBinding ) : RecyclerView.ViewHolder( binding.root ) {

		// Gets the Information for the Recycler View from the Item
		fun bind( item : ListItem ) {

			val idText = "ID : ${ item.id }"
			val nameText = "Name : ${ item.name }"

			binding.idTitle.text = idText
			binding.name.text = nameText
		}

		companion object {

			// Creates, Inflates, Binds, and Returns the View Holder
			fun from(parent: ViewGroup): ItemViewHolder {

				// Gets the Inflater
				val layoutInflater = LayoutInflater.from(parent.context)

				// Binds and Inflates the View
				val binding = CardLayoutListItemRecyclerBinding.inflate( layoutInflater, parent, false )

				// Returns the View Holder
				return ItemViewHolder( binding )
			}
		}
	}

	// Group Title View Holder
	class GroupTitleViewHolder private constructor( val binding: CardLayoutListGroupHeaderBinding) : RecyclerView.ViewHolder( binding.root ) {

		// Gets the Information for the Recycler View from the Item
		fun bind( item : HeaderItem ) {

			val listIDText = "List ID - ${ item.headerName }"

			binding.listID.text = listIDText
		}

		companion object {

			// Creates, Inflates, Binds, and Returns the View Holder
			fun from(parent: ViewGroup): GroupTitleViewHolder {

				// Gets the Inflater
				val layoutInflater = LayoutInflater.from(parent.context)

				// Binds and Inflates the View
				val binding = CardLayoutListGroupHeaderBinding.inflate( layoutInflater, parent, false )

				// Returns the View Holder
				return GroupTitleViewHolder( binding )
			}
		}
	}

	// Selects which type of ViewHolder to return for the given view.
	override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecyclerView.ViewHolder {

		return when ( viewType ) {

			ViewTypes.ITEM_TYPE.typeValue -> ItemViewHolder.from( parent )
			ViewTypes.HEADER_TYPE.typeValue -> GroupTitleViewHolder.from( parent )

			else -> ItemViewHolder.from( parent )
		}
	}
}

class ItemDiffCallback : DiffUtil.ItemCallback< Any? >() {

	override fun areItemsTheSame(oldItem : Any, newItem : Any) : Boolean {

		if ( oldItem is ListItem && newItem is ListItem ) {
			return oldItem == newItem
		}

		return false
	}

	override fun areContentsTheSame(oldItem : Any, newItem : Any) : Boolean {

		return oldItem == newItem
	}
}