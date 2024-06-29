package com.example.collegeapp_jetpackcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collegeapp_jetpackcompose.model.BannerModel
import com.example.collegeapp_jetpackcompose.model.NoticeModel
import com.example.collegeapp_jetpackcompose.utils.Constants.NOTICE
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class NoticeViewModel : ViewModel() {

    private val noticeRef = Firebase.firestore.collection(NOTICE)

    private val storageRef = Firebase.storage.reference.child(NOTICE)

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted


    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted


    private val _noticeList = MutableLiveData<List<NoticeModel>>()
    val noticeList: LiveData<List<NoticeModel>> = _noticeList

    fun saveNotice(notice: NoticeModel,uri: Uri?) {
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID()

        val imageRef = storageRef.child("${NOTICE}/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri!!)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                uploadNotice(it.toString(),randomUid.toString(),notice)
            }
        }
    }

    private fun uploadNotice(url:String,docId:String,notice: NoticeModel) {

        val map = mutableMapOf<String, String>()
        map["docId"] = docId
        map["title"] = notice.title!!
        map["link"] = notice.link!!
        map["imageUrl"] = url

        noticeRef.document(docId).set(map).addOnSuccessListener {
            _isPosted.postValue(true)
        }.addOnFailureListener {
            _isPosted.postValue(false)
        }

    }

    fun getNotice() {
        noticeRef.get().addOnSuccessListener {
            val list = mutableListOf<NoticeModel>()

            for (doc in it) {
                list.add(doc.toObject(NoticeModel::class.java))
            }

            _noticeList.postValue(list)
        }
    }

    fun deleteNotice(notice: NoticeModel) {

        noticeRef.document(notice.docId!!)
            .delete()
            .addOnSuccessListener {
                Firebase.storage.getReferenceFromUrl(notice.imageUrl!!).delete()
                _isDeleted.postValue(true)
            }
            .addOnFailureListener {
                _isDeleted.postValue(false)
            }

    }

}