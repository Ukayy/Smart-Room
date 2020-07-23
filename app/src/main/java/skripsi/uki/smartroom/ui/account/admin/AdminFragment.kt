package skripsi.uki.smartroom.ui.account.admin

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import skripsi.uki.smartroom.R

class AdminFragment : Fragment() {

    companion object {
        fun newInstance() =
            AdminFragment()
    }

    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AdminViewModel::class.java)
        // TODO: Use the ViewModel
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()


    }

}