package uk.ac.tees.mad.d3722574.data

data class TravelDetails(
    var travelId:String? = null,
    var travellerId :String?= null,
    var travellerName:String? = null,
    var travelLocation:String? = null,
    var travelLat:Double? = 0.0,
    var travelLong:Double? = 0.0,
    var travelDate:String? = null,
    var travellerProfileUrl:String? = null
)
