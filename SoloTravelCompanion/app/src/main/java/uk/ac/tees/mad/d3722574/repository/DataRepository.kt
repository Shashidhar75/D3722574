package uk.ac.tees.mad.d3722574.repository

import com.google.firebase.firestore.FirebaseFirestore
import uk.ac.tees.mad.d3722574.data.Destination
import uk.ac.tees.mad.d3722574.data.TravelDetails

object DataRepository {

    fun getAllDestinations(
        onSuccess: (List<Destination>) -> Unit,
        onFailure: () -> Unit
    ) {
        val destinationList = mutableListOf<Destination>()
        FirebaseFirestore.getInstance().collection("destinations").get().addOnCompleteListener {
            if(it.isSuccessful) {
                for (document in it.result.documents) {
                    if (document.exists()) {
                        val destination = document.toObject(Destination::class.java)
                        if (destination != null) {
                            destinationList.add(destination)
                        }
                    }
                }
                onSuccess.invoke(destinationList)
            } else {
                onFailure.invoke()
            }
        }
    }


    fun getAllTravelDetails(
        onSuccess: (allTravels:List<TravelDetails>) -> Unit,
        onFailure: () -> Unit,
        currentUserId : String
    ) {
        FirebaseFirestore.getInstance().collection("travels").get().addOnCompleteListener {
            var allTravels = mutableListOf<TravelDetails>()
            if(it.isSuccessful) {
                for (document in it.result.documents) {
                    if (document.exists()) {
                        val travels = document.toObject(TravelDetails::class.java)
                        if (travels != null && travels.travellerId != currentUserId ) {
                            allTravels.add(travels)
                        }
                    }
                }
                onSuccess.invoke(allTravels)
            } else {
                onFailure.invoke()
            }
        }
    }

    fun getMyTravelDetails(
        onSuccess: (allTravels:List<TravelDetails>) -> Unit,
        onFailure: () -> Unit,
        currentUserId : String
    ) {
        FirebaseFirestore.getInstance().collection("travels").get().addOnCompleteListener {
            val allTravels = mutableListOf<TravelDetails>()
            if(it.isSuccessful) {
                for (document in it.result.documents) {
                    if (document.exists()) {
                        val travels = document.toObject(TravelDetails::class.java)
                        if (travels != null && travels.travellerId == currentUserId) {
                            allTravels.add(travels)
                        }
                    }
                }
                onSuccess.invoke(allTravels)
            } else {
                onFailure.invoke()
            }
        }
    }

    fun deleteMyTravel(travelId:String,onSuccess:()->Unit,onFailure:()->Unit ){
        FirebaseFirestore.getInstance().collection("travels").document(travelId).delete()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }
    }



    fun saveNewTravel(
    travelDetails: TravelDetails,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
    ) {
        FirebaseFirestore.getInstance().collection("travels").document(travelDetails.travelId?:"")
            .set(travelDetails)
            .addOnCompleteListener {
            var allTravels = mutableListOf<TravelDetails>()
            if(it.isSuccessful) {
                onSuccess.invoke()
            } else {
                onFailure.invoke()
            }
        }
    }

}