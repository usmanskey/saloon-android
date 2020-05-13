package saloon.app.android.ui.questions.creator

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import saloon.app.android.data.repository.questions.QuestionsItemKeyedFactory
import saloon.app.android.data.repository.questions.QuestionsRepositoryImpl
import saloon.app.android.ui.main.MainActivity
import java.util.*


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
                    QuestionsItemKeyedFactory(
                        Firebase.firestore,
                        Firebase.auth.uid!!,
                        QuestionsRepositoryImpl.QUESTIONS_COLLECTION_NAME
                    )
                )
            )
        ).get(QuestionCreatorViewModel::class.java)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancel_button.setOnClickListener {
            clearQuestion()
            scrollToFeed()
        }

        question_title.addTextChangedListener {
            create_question_button.isEnabled = it?.isNotEmpty() == true && it.last() == '?'
        }

        open_image_picker.setOnClickListener {
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
            if (filePath != null)
                uploadImage()
            else
                uploadQuestion()
        }
    }

    private fun scrollToFeed() =
        (activity as? MainActivity)?.scrollToFeed()

    private fun clearQuestion() {
        question_image.setImageResource(android.R.color.transparent);
        do_not_display_author.isChecked = false
        filePath = null
        question_title.text = null
    }

    private fun uploadQuestion(pathUrl: String? = null) {
        lifecycleScope.launch {
            viewModel.createQuestion(
                Question(
                    title = question_title.text.toString(),
                    date = Date().time,
                    imageUrl = pathUrl
                )
            )
            clearQuestion()

            Toast.makeText(
                context,
                getString(R.string.question_upload_successfull),
                Toast.LENGTH_SHORT
            ).show()

            scrollToFeed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {
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

            val pathUrl = "images/" + UUID.randomUUID().toString().replace("-", "")

            // Defining the child of storageReference
            val ref: StorageReference = storageReference.child(pathUrl)
            ref.putFile(filePath!!).addOnSuccessListener {
                progressDialog.dismiss()


                uploadQuestion(pathUrl)
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