package skripsi.uki.smartroom.ui.power

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import skripsi.uki.smartroom.R


class PowerFragment : Fragment() {

    companion object {
        fun newInstance() = PowerFragment()
    }

    private lateinit var viewModel: PowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.power_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PowerViewModel::class.java)
        // TODO: Use the ViewModel
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

}