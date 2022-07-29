package co.kr.woowahan_accountbook.presentation.viewmodel.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.usecase.SettingClassificationAddUseCase
import co.kr.woowahan_accountbook.domain.usecase.SettingClassificationUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClassificationAddViewModel @Inject constructor(
    private val settingClassificationAddUseCase: SettingClassificationAddUseCase,
    private val settingClassificationUpdateUseCase: SettingClassificationUpdateUseCase
) : ViewModel() {
    private var id: Int = 0

    val name = MutableLiveData<String>()
    val colors = MutableLiveData<List<ColorInfo>>()
    private var selectedColorIndex = 0
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun initDataSet(id: Int) {
        this.id = id
    }

    fun selectColor(colorIndex: Int) {
        selectedColorIndex = colorIndex
        colors.value = requireNotNull(colors.value).mapIndexed { index, colorInfo ->
            colorInfo.copy().apply { isSelected = index == colorIndex }
        }.toList()
    }

    fun updateSelectedColorIndex(colorIndex: Int) {
        selectedColorIndex = colorIndex
    }

    fun addClassification() {
        val type = requireNotNull(name.value)
        val color = requireNotNull(colors.value)[selectedColorIndex].color.toHexCode()
        val isIncome = requireNotNull(colors.value).size == 10

        viewModelScope.launch {
            runCatching {
                settingClassificationAddUseCase(type, color, isIncome)
            }.onSuccess {
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }

    fun updateClassification() {
        val type = requireNotNull(name.value)
        val color = requireNotNull(colors.value)[selectedColorIndex].color.toHexCode()
        val isIncome = requireNotNull(colors.value).size == 10

        viewModelScope.launch {
            runCatching {
                settingClassificationUpdateUseCase(id, type, color, isIncome)
            }.onSuccess {
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }

    private fun Int.toHexCode(): String = String.format("#%06X", 0xFFFFFF and this)

    data class ColorInfo(
        val color: Int,
        var isSelected: Boolean
    )
}