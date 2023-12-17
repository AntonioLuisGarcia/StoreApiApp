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

// Clase para el fragmento de edición del nombre del carrito.
class EditCartNameFragment : Fragment() {

    // Propiedad para el binding del layout del fragmento.
    private lateinit var binding: FragmentEditCartNameBinding
    // ViewModel compartido con la actividad, proporcionado por Hilt.
    private val viewModel: ShopCartViewModel by activityViewModels()

    // Método para crear la vista del fragmento.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla el layout para este fragmento
        binding = FragmentEditCartNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método que se ejecuta después de que la vista ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtiene el nombre actual del carrito de la navegación.
        val currentName = arguments?.let {
            EditCartNameFragmentArgs.fromBundle(it).cartName
        }

        // Establece el nombre actual en el campo de texto.
        binding.etCartName.setText(currentName)

        // Establece un listener para el botón de guardar.
        binding.btnSaveCartName.setOnClickListener {
            val newName = binding.etCartName.text.toString()
            // Llama al ViewModel para cambiar el nombre del carrito.
            viewModel.changeCartName(newName)
            // Vuelve al fragmento anterior en la pila de navegación.
            findNavController().popBackStack()
        }
    }
}