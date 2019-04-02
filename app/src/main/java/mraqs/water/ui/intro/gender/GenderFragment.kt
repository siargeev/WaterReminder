package mraqs.water.ui.intro.gender

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import mraqs.water.R

class GenderFragment : Fragment() {

    companion object {
        fun newInstance() = GenderFragment()
    }

    private lateinit var viewModel: GenderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gender_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GenderViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
