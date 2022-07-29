package co.kr.woowahan_accountbook.presentation.ui.main.setting

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentClassificationAddBinding
import co.kr.woowahan_accountbook.presentation.adapter.setting.ClassificationColorAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.setting.ClassificationAddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassificationAddFragment : BaseFragment<FragmentClassificationAddBinding>() {
    override val TAG: String
        get() = ClassificationAddFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_classification_add

    private var classificationId: Int? = null
    private var classificationType: String? = null
    private var classificationColor: String? = null
    private var isIncome: Int = 0
    private val classificationColorAdapter by lazy {
        ClassificationColorAdapter {
            viewModel.selectColor(it)
        }
    }
    private val viewModel by viewModels<ClassificationAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            classificationId = it.getInt("_ID")
            classificationType = it.getString("CLASSIFICATION_TYPE")
            classificationColor = it.getString("CLASSIFICATION_COLOR")
            isIncome = it.getInt("IS_INCOME")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        initView()
        initColorsInfo()
        observeData()
    }

    private fun initView() {
        with(binding.tbPaymentAdd) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        binding.rvClassificationColor.adapter = classificationColorAdapter
        binding.tvTitle.text = if (isIncome == 1) "수입 카테고리 추가" else "지출 카테고리 추가"
        binding.btnAdd.setOnClickListener {
            when (classificationId) {
                0 -> viewModel.addClassification()
                else -> viewModel.updateClassification()
            }
        }
    }

    private fun initColorsInfo() {
        val colors =
            if (isIncome == 1) {
                requireContext().resources.getIntArray(R.array.income_colors).toList()
            } else {
                requireContext().resources.getIntArray(R.array.expenditure_colors).toList()
            }
        val colorsInfo: List<ClassificationAddViewModel.ColorInfo> =
            mutableListOf<ClassificationAddViewModel.ColorInfo>().apply {
                colors.forEachIndexed { index, color ->
                    when (classificationId) {
                        0 -> {
                            if (index == 0) {
                                add(ClassificationAddViewModel.ColorInfo(color, true))
                            } else {
                                add(ClassificationAddViewModel.ColorInfo(color, false))
                            }
                        }
                        else -> {
                            if (color == Color.parseColor(classificationColor)) {
                                add(ClassificationAddViewModel.ColorInfo(color, true))
                                viewModel.updateSelectedColorIndex(index)
                            } else {
                                add(ClassificationAddViewModel.ColorInfo(color, false))
                            }
                        }
                    }
                }
            }
        viewModel.colors.value = colorsInfo
        if (classificationId != 0) {
            viewModel.initDataSet(requireNotNull(classificationId))
            viewModel.name.value = requireNotNull(classificationType)
        }
    }

    private fun observeData() {
        viewModel.colors.observe(viewLifecycleOwner) {
            classificationColorAdapter.updateItems(it)
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }
}