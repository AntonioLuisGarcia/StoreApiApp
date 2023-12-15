package edu.algg.storeapiapp.ui.cartName

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.algg.storeapiapp.R


class EditCartNameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_cart_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentName = arguments?.let {
            EditCartNameFragmentArgs.fromBundle(it).cartName
        }
        // Establece el nombre actual en el EditText y maneja el botón de guardar
    }
}