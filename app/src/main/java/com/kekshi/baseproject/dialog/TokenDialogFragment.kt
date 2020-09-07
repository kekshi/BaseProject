package com.kekshi.baseproject.dialog
//
//import android.os.Bundle
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentTransaction
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.kekshi.baselib.utils.DensityUtils
//import com.kekshi.baseproject.R
//
//class TokenDialogFragment : DialogFragment() {
//
//
////    private val tokenAdapter: TokenDialogAdapter by unsafeLazy { TokenDialogAdapter() }
////    private var tokenEntities: List<TokenEntity> = emptyList()
//    private lateinit var useFor: String
//    private lateinit var mTokenSelectListener: ITokenSelectListener
//
//    companion object {
//        fun newTokenDialogFragment(useFor: String, tokenSelectListener: ITokenSelectListener): TokenDialogFragment {
//
//            return TokenDialogFragment().apply {
//                mTokenSelectListener = tokenSelectListener
////                tokenEntities = tokenRepository.tokens.value!!
//                arguments = Bundle().apply {
//                    putString("useFor", useFor)
//                }
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_full)
//
//        useFor = arguments!!["useFor"] as String
//
//
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        with(dialog?.window!!) {
//            setBackgroundDrawableResource(android.R.color.transparent)
//            decorView.setPadding(0, 0, 0, 0)
//            attributes.gravity = Gravity.BOTTOM
//            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
////            attributes.height = DensityUtils.dip2px(420f)
//        }
////        Timber.d("FM--->onCreateView--->${tokenEntities}")
//        return inflater.inflate(R.layout.dialog_token, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        Timber.d("FM--->onActivityCreated--->${tokenEntities}")
////        tv_title.text = getString(R.string.select_token, useFor)
//        with(rv_token_list) {
//            layoutManager = LinearLayoutManager(context)
//            itemAnimator = null
//            setHasFixedSize(true)
//            tokenAdapter.items = tokenEntities
//            adapter = tokenAdapter
//
//            addOnItemTouchListener(object : OnRecyclerViewItemClickListener(this) {
//                override fun onItemClick(vh: RecyclerView.ViewHolder) {
//                    mTokenSelectListener.onTokenInfoSelect(tokenEntities[vh.layoutPosition])
//                    hide()
//                }
//            })
//        }
//
//        iv_close.setOnClickListener {
//            hide()
//        }
//    }
//
//    fun getTokens(): List<TokenEntity> {
//        return tokenEntities
//    }
//
//    fun updataTokens(tokens: List<TokenEntity>) {
//        tokenEntities = tokens
//        rv_token_list?.adapter = tokenAdapter.apply { items = tokenEntities }
//        Timber.d("FM--->updataTokens--->${tokenEntities}")
//
//    }
//
//    fun show(supportFragmentManager: FragmentManager) {
//        val ft = supportFragmentManager.beginTransaction()
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        show(ft, "TokenDialogFragment")
//    }
//
//    fun hide() {
//        dismiss()
//    }
//
//    interface ITokenSelectListener {
//        fun onTokenInfoSelect(tokenEntity: TokenEntity)
//    }
//}