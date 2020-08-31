package skripsi.uki.smartroom.ui.account.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import skripsi.uki.smartroom.R

class AdminFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            AdminFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_edit:Button = view.findViewById(R.id.btn_edit)
        btn_edit.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_edit ->{
                val intentEdit = Intent(activity, EditUserActivity::class.java)
                startActivity(intentEdit)
            }
        }
    }

}