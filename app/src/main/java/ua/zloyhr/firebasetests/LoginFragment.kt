package ua.zloyhr.firebasetests

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import ua.zloyhr.firebasetests.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val persons = FirebaseFirestore.getInstance().collection("persons")

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.apply {
            btnSendButton.setOnClickListener {
                val person: Person
                try{
                    person = Person(
                        tfFirst.editText?.text.toString(),
                        tfLast.editText?.text.toString(),
                        tfAge.editText?.text.toString().toInt()
                    )
                    savePerson(person)
                }catch (it: Exception){
                    Toast.makeText(requireContext(), "Error while creating person: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun savePerson(person: Person) {
        persons.add(person)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Added user to db with id: ${it.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error while adding to db: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}

