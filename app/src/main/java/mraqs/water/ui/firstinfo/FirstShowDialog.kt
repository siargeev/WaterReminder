package mraqs.water.ui.firstinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_first_show_dialog.btnOk
import kotlinx.android.synthetic.main.fragment_first_show_dialog.labelGoalNumber
import mraqs.water.R
import mraqs.water.manager.LivePreferenceManager

class FirstShowDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_show_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val prefs = LivePreferenceManager(activity!!.applicationContext)
        val goal = prefs.loadGoal().value!!.toString()
        val ml = resources.getString(R.string.ml)
        labelGoalNumber.text = goal + " " + ml
        btnOk.setOnClickListener { dismiss() }
    }
}
