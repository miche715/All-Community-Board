package com.example.client.comment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.comment.domain.Comment
import com.example.client.comment.repository.CommentRepository
import com.example.client.content.domain.Content
import kotlinx.coroutines.launch
import java.lang.Exception

class CommentViewModel(private val commentRepository: CommentRepository = CommentRepository()) : ViewModel()
{
    val addCommentResult: MutableLiveData<Content> = MutableLiveData()
    val getAllResult: MutableLiveData<MutableList<Comment>> = MutableLiveData()
    val removeCommentResult: MutableLiveData<Content> = MutableLiveData()

    fun addComment(comment: Comment, contentId: Long, userId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = commentRepository.addCommentCheck(comment, contentId, userId)  // AddCommentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 201 && response.body != null)  // 댓글 등록 성공
                {
                    addCommentResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }

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
                    getAllResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }

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
                    removeCommentResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}