package com.example.client.comment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.comment.repository.CommentRepository
import com.example.client.content.domain.Content
import kotlinx.coroutines.launch
import java.lang.Exception

class RemoveCommentViewModel(private val commentRepository: CommentRepository = CommentRepository()) : ViewModel()
{
    val result: MutableLiveData<Content> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감

    fun removeComment(commentId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = commentRepository.removeCommentCheck(commentId)  // RemoveCommentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 댓글 삭제 성공
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