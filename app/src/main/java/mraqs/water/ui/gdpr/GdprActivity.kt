package mraqs.water.ui.gdpr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ads.consent.ConsentInformation
import com.google.ads.consent.ConsentStatus.NON_PERSONALIZED
import com.google.ads.consent.ConsentStatus.PERSONALIZED
import kotlinx.android.synthetic.main.activity_gdpr.btnAcceptGdpr
import kotlinx.android.synthetic.main.activity_gdpr.btnDeclineGdpr
import mraqs.water.R
import mraqs.water.ui.intro.IntroActivity
import org.jetbrains.anko.startActivity

class GdprActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gdpr)
        btnAcceptGdpr.setOnClickListener {
            ConsentInformation.getInstance(this).consentStatus = PERSONALIZED
            startActivity<IntroActivity>()
        }
        btnDeclineGdpr.setOnClickListener {
            ConsentInformation.getInstance(this).consentStatus = NON_PERSONALIZED
            startActivity<IntroActivity>()
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
