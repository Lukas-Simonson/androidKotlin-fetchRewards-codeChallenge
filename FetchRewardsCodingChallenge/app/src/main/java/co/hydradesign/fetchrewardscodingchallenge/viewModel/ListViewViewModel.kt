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

	// Get Data for Recycler View
	private val _itemData = MutableLiveData< List< Any? > >()

	val itemData : LiveData< List< Any? > >
		get() = _itemData

	init { getItemData() }

	fun getItemData() {

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

	private fun filterAndSortData( data: List< ListItem > ) : List< Any? > {

		val notNullData = data.filter { it.name != null }
		val notEmptyData = notNullData.filter { it.name!!.trim() != "" }
		val sortedData = notEmptyData.sortedWith( compareBy( { it.listId }, { it.name } ) )

		val returnData : MutableList< Any? > = mutableListOf()
		var lastListID : Int? = null

		sortedData.forEach {

			if ( lastListID == null ) {
				lastListID = it.listId
				returnData.add( HeaderItem( headerName = "${it.listId}" ) )
				returnData.add( it )
			}
			else if ( lastListID != it.listId ) {
				lastListID = it.listId
				returnData.add( HeaderItem( headerName = "${it.listId}" ) )
				returnData.add( it )
			}
			else { returnData.add( it ) }
		}

		return returnData
	}


}