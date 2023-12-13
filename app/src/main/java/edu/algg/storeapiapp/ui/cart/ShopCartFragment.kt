package edu.algg.storeapiapp.ui.cart

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import edu.algg.storeapiapp.data.repository.ProductRepository
import edu.algg.storeapiapp.databinding.FragmentShopCartBinding
import javax.inject.Inject

@AndroidEntryPoint
class ShopCartFragment : Fragment() {

    private lateinit var binding: FragmentShopCartBinding
    private val viewModel: ShopCartViewModel by viewModels()


    @Inject
    lateinit var repository: ProductRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ShopCartAdapter(requireContext(),
            onIncrease = { product -> viewModel.increaseProductQuantity(product.id) },
            onDecrease = { product -> viewModel.decreaseProductQuantity(product.id) }
        )
        binding.rvCartItems.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.products)
                }
            }
        }

        binding.btnCheckout.setOnClickListener {
            // Implementar lógica de checkout
        }
    }

    private fun showCreateListDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Crear Lista") //builder.setTitle(getString(R.string.create_list))

        // Creo un EditText para que el usuario ingrese el nombre de la lista
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        /*
        * // Creo un EditText para que el usuario ingrese el nuevo nombre del carrito
    val input = EditText(requireContext()).apply {
        inputType = InputType.TYPE_CLASS_TEXT
        hint = getString(R.string.cart_name)
    }
    builder.setView(input)
        * */

        // Botón positivo para confirmar y cambiar el nombre del carrito
        builder.setPositiveButton("Cambiar") { _, _ ->
            val newName = input.text.toString()
            if (newName.isNotBlank()) {
                viewModel.changeCartName(newName)
                Toast.makeText(requireContext(), "Nombre del carrito actualizado a: $newName", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón negativo para cancelar el diálogo
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}