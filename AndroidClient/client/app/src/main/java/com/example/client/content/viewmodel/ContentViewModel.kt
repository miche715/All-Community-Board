package com.example.client.content.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.content.domain.Content
import com.example.client.content.repository.ContentRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ContentViewModel(private val contentRepository: ContentRepository = ContentRepository()) : ViewModel()
{
    val addContentResult: MutableLiveData<Boolean> = MutableLiveData()
    val getAllResult: MutableLiveData<MutableList<Content>> = MutableLiveData()
    val getSearchResult: MutableLiveData<MutableList<Content>> = MutableLiveData()
    val modifyContentResult: MutableLiveData<Content> = MutableLiveData()
    val removeContentResult: MutableLiveData<Boolean> = MutableLiveData()

    fun addContent(content: Content, userId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.addContentCheck(content, userId)  // AddContentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 201 && response.body!!)  // 게시글 생성 성공
                {
                    addContentResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }

    fun getAll(page: Int, size: Int)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.getAllCheck(page, size)  // GetAllResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 게시글 로딩 성공
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

    fun getSearch(keyword: String, page: Int, size: Int)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.getSearchCheck(keyword, page, size)  // GetSearchResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 게시글 검색 로딩 성공
                {
                    getSearchResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }

    fun modifyContent(content: Content, userId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.modifyContentCheck(content, userId)  // ModifyContentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 게시글 수정 성공
                {
                    modifyContentResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }

    fun removeContent(contentId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.removeContentCheck(contentId)  // RemoveContentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body!!)  // 게시글 삭제 성공
                {
                    removeContentResult.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}