package com.example.myfashion.ui.outfits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfashion.Image
import com.example.myfashion.ImageAdapter
import com.example.myfashion.MainViewModel
import com.example.myfashion.databinding.FragmentDashboardBinding

class OutfitsFragment : Fragment() {

private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
    //val outfitsViewModel = ViewModelProvider(this).get(OutfitsViewModel::class.java)
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root
    viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//    val textView: TextView = binding.textDashboard
//    outfitsViewModel.text.observe(viewLifecycleOwner) {
//        textView.text = it
//    }
    var images = ArrayList<Image>()
    if (viewModel.outfits.value != null && viewModel.outfitsTitles.value != null) {
        for(outfit in viewModel.outfits.value!!.zip(viewModel.outfitsTitles.value!!)) {
            val img1 = viewModel.getTshirts("image${outfit.first.first}").value     // first image from outfit
            val img2 = viewModel.getTrousers("image${outfit.first.second}").value   // second image from outfit
            if (img1 != null && img2 != null) {
                images.add(Image(outfit.second, img1, img2))        // outfit.second gives a desired title
            }
        }
    }
    val recyclerView = binding.outfitsRecycleView
    recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = ImageAdapter(requireActivity(), images)
    return root
}

override fun onDestroyView()
{
    super.onDestroyView()
    _binding = null
}
}