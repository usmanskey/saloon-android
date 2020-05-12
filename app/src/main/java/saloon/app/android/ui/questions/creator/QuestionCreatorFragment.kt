package saloon.app.android.ui.questions.creator

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.create_question_fragment.*
import kotlinx.coroutines.launch
import saloon.app.android.R
import saloon.app.android.data.models.Question
import saloon.app.android.data.repository.questions.QuestionsItemKeyed
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import java.util.*
import java.util.concurrent.Executors


private const val PICK_IMAGE_REQUEST = 101

class QuestionCreateFragment : Fragment(R.layout.create_question_fragment) {

    private var filePath: Uri? = null
    private lateinit var viewModel: QuestionCreatorViewModel

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, QuestionCreatorViewModelProvider(
                QuestionsRepositoryImpl(
                    QuestionsItemKeyed(
                        Firebase.firestore,
                        Firebase.auth.uid!!,
                        QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME
                    ),
                    Executors.newSingleThreadExecutor(),
                    ContextCompat.getMainExecutor(context)
                )
            )
        ).get(QuestionCreatorViewModel::class.java)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question_title.addTextChangedListener {
            create_question_button.isEnabled = it?.isNotEmpty() == true && it.last() == '?'
        }

        open_image_picker.setOnClickListener {

            // Defining Implicit Intent to mobile gallery
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image from here..."
                ),
                PICK_IMAGE_REQUEST
            )
        }

        create_question_button.setOnClickListener {
            uploadImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null
        ) {

            filePath = data.data
            Glide.with(this).load(filePath).into(question_image)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val pathUrl = "images/" + UUID.randomUUID().toString()

            // Defining the child of storageReference
            val ref: StorageReference = storageReference.child(pathUrl)
            ref.putFile(filePath!!).addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    context,
                    getString(R.string.question_upload_successfull),
                    Toast.LENGTH_SHORT
                ).show()

                lifecycleScope.launch {
                    viewModel.createQuestion(
                        Question(
                            title = question_title.text.toString(),
                            date = Date().time,
                            imageUrl = pathUrl
                        )
                    )
                }
            }.addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog.dismiss()
                Toast
                    .makeText(
                        context,
                        "Failed " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }.addOnProgressListener { taskSnapshot ->
                val progress = (100.0
                        * taskSnapshot.bytesTransferred
                        / taskSnapshot.totalByteCount)
                progressDialog.setMessage(
                    "Uploaded "
                            + progress.toInt() + "%"
                )
            }
        }
    }
}