package co.kr.woowahan_accountbook.presentation.ui.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentHistoryBinding
import co.kr.woowahan_accountbook.presentation.adapter.history.HistoryAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val TAG: String
        get() = HistoryFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_history

    private val viewModel by viewModels<HistoryViewModel>()
    private val historyAdapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHistories(2022, 7)
        initView()
        initOnClickListener()
        observeData()
    }

    private fun initView() {
        binding.rvHistory.adapter = historyAdapter
    }

    private fun initOnClickListener() {
        binding.fabAdd.setOnClickListener {
            parentFragmentManager.commit {
                replace<HistoryAddFragment>(R.id.fcv_main)
                addToBackStack(HistoryAddFragment::class.java.simpleName)
            }
        }
    }

    private fun observeData() {
        viewModel.histories.observe(viewLifecycleOwner) {
            historyAdapter.updateItems(it)
        }
    }
}