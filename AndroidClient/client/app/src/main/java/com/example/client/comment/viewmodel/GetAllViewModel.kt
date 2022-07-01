package com.example.client.comment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.comment.domain.Comment
import com.example.client.comment.repository.CommentRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class GetAllViewModel(private val commentRepository: CommentRepository = CommentRepository()) : ViewModel()
{
    val result: MutableLiveData<MutableList<Comment>> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감

    fun getAll(contentId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = commentRepository.getAllCheck(contentId)  // GetAllResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 댓글 로딩 성공
                {
                    result.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}