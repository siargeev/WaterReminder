package mraqs.water.ui.gdpr

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.ads.consent.ConsentInformation
import com.google.ads.consent.ConsentStatus
import com.google.ads.consent.ConsentStatus.NON_PERSONALIZED
import com.google.ads.consent.ConsentStatus.PERSONALIZED
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_gdpr.btnAcceptGdpr
import kotlinx.android.synthetic.main.activity_gdpr.btnDeclineGdpr
import mraqs.water.R
import mraqs.water.ui.gdpr.GdprViewModel.UIState
import mraqs.water.ui.gdpr.GdprViewModel.UIState.OnNextClick
import mraqs.water.ui.gdpr.GdprViewModel.UIState.ShowWarning
import mraqs.water.ui.intro.IntroActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

class GdprActivity : DaggerAppCompatActivity() {

    companion object {
        private val TAG = "GdprActivity"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    val viewModel: GdprViewModel by lazy { ViewModelProviders.of(this, factory).get(GdprViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gdpr)
        observeUIState()
        setupOnClicks()
    }

    private fun setupOnClicks() {
        btnAcceptGdpr.setOnClickListener {
            viewModel.onClick(PERSONALIZED)
        }
        btnDeclineGdpr.setOnClickListener {
            viewModel.onClick(NON_PERSONALIZED)
        }
    }

    private fun updateUIState(state: UIState) {
        when (state) {
            is OnNextClick -> onClickNext(state.status)
            is ShowWarning -> toast(getString(R.string.permissions_required))
        }
    }

    private fun onClickNext(status: ConsentStatus) {
        ConsentInformation.getInstance(this).consentStatus = status
        startActivity<IntroActivity>()
    }

    private fun observeUIState() {
        viewModel.uiState.observe(this, Observer { updateUIState(it) })
    }
}
