package mraqs.water

import android.app.Activity
import android.os.Build
import com.google.android.material.button.MaterialButton
import junit.framework.Assert.assertNotNull
import mraqs.water.manager.PermissionManager
import mraqs.water.ui.gdpr.GdprActivity
import mraqs.water.ui.gdpr.GdprViewModel
import mraqs.water.ui.gdpr.GdprViewModel.UIState
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.runner.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(constants = BuildConfig::class, sdk = [Build.VERSION_CODES.N], application = App::class)
@RunWith(RobolectricTestRunner::class)
class GdprActivityTest {

    private lateinit var activity: Activity
    private lateinit var model: GdprViewModel

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(GdprActivity::class.java).create().get()
        model = GdprViewModel(PermissionManager(activity.applicationContext))
    }

    @Test
    fun validateActivityNotNull() {
        assertNotNull(activity)
    }

    @Test
    fun `Button Accept Should Call Intro Activity`() {
        val btn = activity.findViewById<MaterialButton>(R.id.btnAcceptGdpr)

        btn.callOnClick()
        model.uiState.observeForever { assertThat(it, instanceOf(UIState.ShowWarning::class.java)) }
    }
}