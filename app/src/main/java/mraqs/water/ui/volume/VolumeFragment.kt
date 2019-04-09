package mraqs.water.ui.volume

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.volume_fragment.adView
import kotlinx.android.synthetic.main.volume_fragment.cvBottle
import kotlinx.android.synthetic.main.volume_fragment.cvCup
import kotlinx.android.synthetic.main.volume_fragment.cvGlass
import kotlinx.android.synthetic.main.volume_fragment.cvSmallBottle
import kotlinx.android.synthetic.main.volume_fragment.cvSmallGlass
import mraqs.water.databinding.VolumeFragmentBinding
import mraqs.water.ui.volume.VolumeViewModel.UIState
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnBottleClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnCupClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnGlassClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnSaveClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnSmallBottleClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnSmallGlassClick
import mraqs.water.util.show
import javax.inject.Inject

class VolumeFragment : DaggerDialogFragment() {

    companion object {
        fun newInstance() = VolumeFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: VolumeViewModel by lazy { ViewModelProviders.of(this, factory).get(VolumeViewModel::class.java) }
    private lateinit var binding: VolumeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VolumeFragmentBinding>(inflater, mraqs.water.R.layout.volume_fragment, container, false)
            .apply { binding = this }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBinding()
        observeUIState()
        adView.show()
    }

    private fun updateUIState(uiState: UIState) = when (uiState) {
        OnSaveClick -> dismiss()
        OnSmallGlassClick -> updateVolumeCards(arrayOf(1, 0, 0, 0, 0))
        OnCupClick -> updateVolumeCards(arrayOf(0, 1, 0, 0, 0))
        OnGlassClick -> updateVolumeCards(arrayOf(0, 0, 1, 0, 0))
        OnSmallBottleClick -> updateVolumeCards(arrayOf(0, 0, 0, 1, 0))
        OnBottleClick -> updateVolumeCards(arrayOf(0, 0, 0, 0, 1))
    }

    private fun updateVolumeCards(indicators: Array<Int>) {

        cvSmallGlass.backgroundTintList =
            if (indicators[0] == 1) ColorStateList.valueOf(resources.getColor(mraqs.water.R.color.blue)) else ColorStateList.valueOf(
                resources.getColor(
                    mraqs.water.R.color.white
                )
            )
        cvCup.backgroundTintList =
            if (indicators[1] == 1) ColorStateList.valueOf(resources.getColor(mraqs.water.R.color.blue)) else ColorStateList.valueOf(
                resources.getColor(
                    mraqs.water.R.color.white
                )
            )

        cvGlass.backgroundTintList =
            if (indicators[2] == 1) ColorStateList.valueOf(resources.getColor(mraqs.water.R.color.blue)) else ColorStateList.valueOf(
                resources.getColor(
                    mraqs.water.R.color.white
                )
            )

        cvSmallBottle.backgroundTintList =
            if (indicators[3] == 1) ColorStateList.valueOf(resources.getColor(mraqs.water.R.color.blue)) else ColorStateList.valueOf(
                resources.getColor(
                    mraqs.water.R.color.white
                )
            )

        cvBottle.backgroundTintList =
            if (indicators[4] == 1) ColorStateList.valueOf(resources.getColor(mraqs.water.R.color.blue)) else ColorStateList.valueOf(
                resources.getColor(
                    mraqs.water.R.color.white
                )
            )
    }

    private fun observeUIState() {
        viewModel.uiState.observe(this, Observer { updateUIState(it) })
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, mraqs.water.R.style.FullScreenDialogStyle)
    }
}
