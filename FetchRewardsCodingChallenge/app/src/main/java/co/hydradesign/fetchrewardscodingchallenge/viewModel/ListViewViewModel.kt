package co.hydradesign.fetchrewardscodingchallenge.viewModel

import android.util.Log
import androidx.core.text.trimmedLength
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.hydradesign.fetchrewardscodingchallenge.model.HeaderItem
import co.hydradesign.fetchrewardscodingchallenge.model.ItemAPI
import co.hydradesign.fetchrewardscodingchallenge.model.ListItem
import kotlinx.coroutines.launch

class ListViewViewModel : ViewModel() {

	/** Contains the RecyclerView Data. */
	private val _itemData = MutableLiveData< List< Any? > >()

	/** Interacts with _itemData to get and set data.*/
	val itemData : LiveData< List< Any? > >
		get() = _itemData

	init { getItemData() }

	/**
	 * Gets the Item Data from the ItemAPI
	 */
	private fun getItemData() {

		viewModelScope.launch {

			// Error Handling
			try {

				val data = ItemAPI.retrofitService.getHiringJSON()
				_itemData.value = filterAndSortData( data = data )

			} catch( error : Error ) {
				Log.e( "LISTITEM-RECYCLER", "ERROR : $error" )
			}
		}
	}

	/**
	 *  Filters and Sorts the given List< ListItems >.
	 *
	 *  @param data the list to sort.
	 *  @return a List< Any? > containing ListItems and HeaderItems
	 */
	private fun filterAndSortData( data: List< ListItem > ) : List< Any? > {

		// Filters the Data
		val notNullData = data.filter { it.name != null }                   // Removes all items where the name is null
		val notEmptyData = notNullData.filter { it.name!!.trim() != "" }    // Removes all items where the name is blank

		// Sorts the Data by listID and name
		val sortedData = notEmptyData.sortedWith( compareBy( { it.listId }, { it.name } ) )

		val returnData : MutableList< Any? > = mutableListOf()
		var lastListID : Int? = null

		// Adds the Header Items into the returnData array
		sortedData.forEach {

			when {
				lastListID == null -> {
					lastListID = it.listId
					returnData.add( HeaderItem( headerName = "${it.listId}" ) )
					returnData.add( it )
				}
				lastListID != it.listId -> {
					lastListID = it.listId
					returnData.add( HeaderItem( headerName = "${it.listId}" ) )
					returnData.add( it )
				}
				else -> { returnData.add( it ) }
			}
		}

		return returnData
	}
}