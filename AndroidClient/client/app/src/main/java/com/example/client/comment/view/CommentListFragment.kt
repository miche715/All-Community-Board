package com.example.client.comment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.client.comment.adapter.CommentListItemAdapter
import com.example.client.comment.domain.Comment
import com.example.client.comment.viewmodel.CommentViewModel
import com.example.client.content.domain.Content
import com.example.client.databinding.FragmentCommentListBinding
import com.example.client.user.domain.User

class CommentListFragment : Fragment()
{
    private var binding: FragmentCommentListBinding? = null

    private val commentViewModel: CommentViewModel by viewModels()

    private lateinit var commentListItemAdapter: CommentListItemAdapter

    private var user: User? = null
    private var content: Content? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentCommentListBinding.inflate(inflater, container, false)

        user = requireArguments().getSerializable("user") as User
        content = requireArguments().getSerializable("content") as Content

        commentListItemAdapter = CommentListItemAdapter(this.requireContext(), user!!)
        binding!!.recylerView.adapter = commentListItemAdapter

        // 댓글 로딩 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        commentViewModel.getAll(content!!.contentId!!)
        commentViewModel.getAllResult.observe(requireActivity())
        {result ->
            @Suppress("UNCHECKED_CAST")
            commentListItemAdapter.comments = result
            commentListItemAdapter.notifyDataSetChanged()
        }
        // 댓글 로딩 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 댓글 생성 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding!!.submitButton.setOnClickListener()
        {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.submitButton.windowToken, 0)
            }

            Comment().apply()
            {
                this.writer = user!!.username
                this.text = binding!!.commentTextEdittext.text.toString()
            }.run()
            {
                commentViewModel.addComment(this, content!!.contentId!!, user!!.userId!!)
            }

            binding!!.commentTextEdittext.setText("")

        }
        commentViewModel.addCommentResult.observe(requireActivity())
        {result ->
            activity!!.overridePendingTransition(0, 0)
            activity!!.intent.putExtra("user", user)
            activity!!.intent.putExtra("content", result)
            activity!!.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(activity!!.intent)
            activity!!.overridePendingTransition(0, 0)

            activity!!.finish()
        }
        // 댓글 생성 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 댓글 삭제 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        commentListItemAdapter.setItemClickListener(object: CommentListItemAdapter.OnItemClickListener
        {
            override fun onClick(v: View, position: Int)
            {
                with(AlertDialog.Builder(context!!))
                {
                    this.setMessage("댓글을 삭제 하시겠습니까?")
                    this.setPositiveButton("확인") { _, _ -> commentViewModel.removeComment(commentListItemAdapter.comments[position].commentId!!) }
                    this.setNegativeButton("취소") { _, _ -> }
                }.show()
            }
        })
        commentViewModel.removeCommentResult.observe(requireActivity())
        {result ->
            activity!!.overridePendingTransition(0, 0)
            activity!!.intent.putExtra("user", user)
            activity!!.intent.putExtra("content", result)
            activity!!.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(activity!!.intent)
            activity!!.overridePendingTransition(0, 0)

            activity!!.finish()
        }
        // 댓글 삭제 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return binding!!.root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()

        binding = null
    }
}