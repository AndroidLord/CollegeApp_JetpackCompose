package com.example.collegeapp_jetpackcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collegeapp_jetpackcompose.model.CollegeInfoModel
import com.example.collegeapp_jetpackcompose.utils.Constants.COLLEGE_INFO
import com.example.collegeapp_jetpackcompose.utils.Constants.COLLEGE_NAME
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class CollegeInfoViewModel : ViewModel() {

    private val collegeInfoRef = Firebase.firestore.collection(COLLEGE_INFO)

    private val storageRef = Firebase.storage.reference.child(COLLEGE_INFO)

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted


    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted


    private val _collegeInfo = MutableLiveData<CollegeInfoModel>()
    val collegeInfo: LiveData<CollegeInfoModel> = _collegeInfo


    fun saveCollegeInfo(
        uri: Uri,
        college: CollegeInfoModel
    ) {
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID()

        val imageRef = storageRef.child("${COLLEGE_INFO}/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri)

        collegeInfoRef.document(COLLEGE_NAME).get().addOnSuccessListener {
            if (it.exists() || it != null) {

                uploadCollegeInfo(
                    college.imageUrl!!,
                    randomUid.toString(),
                    college.name!!,
                    college.address!!,
                    college.description!!,
                    college.websiteLink!!,
                    true
                )

            } else {

                uploadTask.addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener {
                        uploadCollegeInfo(
                            it.toString(),
                            randomUid.toString(),
                            college.name!!,
                            college.address!!,
                            college.description!!,
                            college.websiteLink!!
                        )
                    }
                }
            }

        }
    }

    private fun uploadCollegeInfo(
        imageUrl: String,
        docId: String,
        name: String,
        address: String,
        description: String,
        websiteLink: String,
        isUpdating: Boolean = false
    ) {

        val map = mutableMapOf<String, Any>()
        map["imageUrl"] = imageUrl
        map["name"] = name
        map["address"] = address
        map["description"] = description
        map["websiteLink"] = websiteLink


        if (isUpdating) {

            print("Document exists & will be updated")
            collegeInfoRef.document(COLLEGE_NAME).update(map)
                .addOnSuccessListener {
                    _isPosted.postValue(true)
                }.addOnFailureListener {
                    _isPosted.postValue(false)
                    print("something went wrong")
                }
        } else {
            print("Document does not exist & will be created")
            collegeInfoRef.document(COLLEGE_NAME).set(map)
                .addOnSuccessListener {
                    _isPosted.postValue(true)
                }.addOnFailureListener {
                    _isPosted.postValue(false)
                }
        }
    }




fun getCollegeInfo() {

    collegeInfoRef.document(COLLEGE_NAME).get().addOnSuccessListener {
        val list = mutableListOf<CollegeInfoModel>()
        _collegeInfo.postValue(
            it.toObject(CollegeInfoModel::class.java)
        )
    }.addOnFailureListener {

    }
}

}