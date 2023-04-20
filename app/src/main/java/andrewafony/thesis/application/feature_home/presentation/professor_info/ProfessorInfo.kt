package andrewafony.thesis.application.feature_home.presentation.professor_info

import com.google.firebase.firestore.DocumentReference

data class ProfessorInfo(
    val photo: String,
    val info: DocumentReference
)

data class DetailProfessorInfo(
    val name: String,
    val photo: String,
    val birthday: String,
    val email: String,
    val phone_number: String,
    val position: String
)