package co.kr.woowahan_accountbook.presentation.viewmodel.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.entity.setting.SettingClassification
import co.kr.woowahan_accountbook.domain.entity.setting.SettingPayment
import co.kr.woowahan_accountbook.domain.usecase.setting.SettingClassificationsUseCase
import co.kr.woowahan_accountbook.domain.usecase.setting.SettingPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingPaymentsUseCase: SettingPaymentsUseCase,
    private val settingClassificationsUseCase: SettingClassificationsUseCase
) : ViewModel() {
    private val _payments = MutableLiveData<List<SettingPayment>>()
    val payments: LiveData<List<SettingPayment>> get() = _payments
    private val _incomeClassifications = MutableLiveData<List<SettingClassification>>()
    val incomeClassifications: LiveData<List<SettingClassification>> get() = _incomeClassifications
    private val _expenditureClassifications = MutableLiveData<List<SettingClassification>>()
    val expenditureClassification: LiveData<List<SettingClassification>> get() = _expenditureClassifications

    init {
        getData()
    }

    fun getData() {
        getPayments()
        getClassificationsByType(false)
        getClassificationsByType(true)
    }

    private fun getPayments() {
        viewModelScope.launch {
            runCatching {
                settingPaymentsUseCase()
            }.onSuccess {
                _payments.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun getClassificationsByType(isIncome: Boolean) {
        viewModelScope.launch {
            runCatching {
                settingClassificationsUseCase(isIncome)
            }.onSuccess {
                if (isIncome) _incomeClassifications.value = it
                else _expenditureClassifications.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}