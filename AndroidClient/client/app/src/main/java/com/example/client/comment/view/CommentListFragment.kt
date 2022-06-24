package com.example.client.comment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.client.comment.domain.Comment
import com.example.client.comment.service.CommentRetrofitServiceObject
import com.example.client.content.domain.Content
import com.example.client.databinding.FragmentCommentListBinding
import com.example.client.user.domain.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentListFragment : Fragment()
{
    private var binding: FragmentCommentListBinding? = null

    private val commentRetrofitService = CommentRetrofitServiceObject.getRetrofitInstance()

    private var user: User? = null
    private var content: Content? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentCommentListBinding.inflate(inflater, container, false)

        user = requireArguments().getSerializable("user") as User
        content = requireArguments().getSerializable("content") as Content

        binding!!.submitButton.setOnClickListener()
        {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.submitButton.windowToken, 0)
            }

            val comment = Comment().apply()
            {
                this.writer = user!!.username
                this.text = binding!!.commentTextEdittext.text.toString()
            }
            binding!!.commentTextEdittext.setText("")

            commentRetrofitService.addComment(comment, content!!.contentId!!, user!!.userId!!).enqueue(object : Callback<Content>
            {
                override fun onResponse(call: Call<Content>, response: Response<Content>)
                {
                    if(response.isSuccessful)
                    {
                        with(activity!!)
                        {
                            this.overridePendingTransition(0, 0)
                            this.intent.putExtra("user", user)
                            this.intent.putExtra("content", response.body())
                            this.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(this.intent)
                            this.overridePendingTransition(0, 0)

                            this.finish()
                        }
                    }
                }

                override fun onFailure(call: Call<Content>, t: Throwable)
                {
                    Log.e("서버 연결 실패", t.toString())
                }
            })
        }

        return binding!!.root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()

        binding = null
    }
}