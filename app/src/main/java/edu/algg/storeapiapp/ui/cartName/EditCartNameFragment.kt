package edu.algg.storeapiapp.ui.cartName

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.algg.storeapiapp.databinding.FragmentEditCartNameBinding
import edu.algg.storeapiapp.ui.cart.ShopCartViewModel

class EditCartNameFragment : Fragment() {

    private lateinit var binding: FragmentEditCartNameBinding
    private val viewModel: ShopCartViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditCartNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentName = arguments?.let {
            EditCartNameFragmentArgs.fromBundle(it).cartName
        }
        binding.etCartName.setText(currentName)
        binding.btnSaveCartName.setOnClickListener {
            val newName = binding.etCartName.text.toString()
            viewModel.changeCartName(newName)
            findNavController().popBackStack()
        }
    }
}