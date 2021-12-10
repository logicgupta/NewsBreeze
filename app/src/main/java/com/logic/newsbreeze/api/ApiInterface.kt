import io.reactivex.Single
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    companion object {

        val BASE_URL = "https://newsapi.org/v2/"
        val CONVERTER_FACTORY: Converter.Factory = GsonConverterFactory.create()
        val TIMEOUT_SECONDS: Long = 60
        const val OS_NAME = "Android"
    }

    @GET("top-headlines?sources=techcrunch&apiKey=34a546b3e5364f0080ccdd07431e4e0f")
    fun getData(): Single<com.logic.newsbreeze.main.model.Response>


}
