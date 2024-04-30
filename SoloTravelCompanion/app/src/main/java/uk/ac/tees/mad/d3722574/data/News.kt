package uk.ac.tees.mad.d3722574.data

data class NewsList(
    var results: List<News>
)

data class  News(
    var article_id:String? = null,
    var title:String? = null,
    var link:String? = null,
    var description:String? = null,
    var image_url:String? = null,
)
