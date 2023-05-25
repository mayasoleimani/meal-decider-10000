package com.profjpbaugh.navigationdemo.ui.main

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.profjpbaugh.navigationdemo.databinding.FragmentMainBinding
import org.json.JSONObject


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }//end onCreateView

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.randoRecipeButton.setOnClickListener(){

            printCatData()


        }

    }//end onViewCreated




    fun printCatData( ) {
        var catUrl = "https://www.themealdb.com/api/json/v1/1/random.php"
//        spinnerData:ArrayList<String>
        val queue = Volley.newRequestQueue(context)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            Response.Listener<String> { response ->
                val MealObject : JSONObject = JSONObject(response)



                   // Log.i("MainActivity", "Cat description: ${theCat.getString("description")}")
                   // spinnerData.add(theCat.getString("name").toString())



                //indices from 0 through catsArray.length()-1

                    //${} is to interpolate the string /
                    // uses a string template
                binding.textView2.scrollTo(0,0)

                    var req = MealObject.getString("meals")
                req = req.substring(1, req.length-1)
                Log.i("MainActivity", req)
                    val json = JSONObject(req)
                    Log.i("mainactivity", "stuff: ${json.getString("strMeal")}")
                    Log.i("mainactivity", "recipe: ${json.getString("strInstructions")}")

                //now get the properties we want:  name and description
//                    Log.i("MainActivity", "Cat name: ${theCat.getString("name")}")
//                    Log.i("MainActivity", "Cat description: ${theCat.getString("description")}")
                       // var newmeal : JSONObject = JSONObject(mealobject.getString("meals"))
                        binding.textView.text = json.getString("strMeal").toString()
                      //  Log.i("testing",newmeal.toString())
                        val imgView = binding.imageView

                binding.textView2.setMovementMethod(ScrollingMovementMethod())

                binding.textView2.text = json.getString("strInstructions").toString()

                    //  Log.i("MainActivity", "Cat link: ${theCat.getJSONObject("image").getString("url")}")
                    val imgurl = json.getString("strMealThumb").toString()

//                    spinnerData.add(theCat.getString("name").toString())
                bindImage(imgView,imgurl )



            },
            Response.ErrorListener {
                Log.i("MainActivity", "That didn't work!")
            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


}



fun bindImage(imgView: ImageView , imgUrl: String?) {
    imgUrl?.let {

        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)

    }
}