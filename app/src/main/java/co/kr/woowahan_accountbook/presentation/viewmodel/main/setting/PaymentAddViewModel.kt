package co.kr.woowahan_accountbook.presentation.viewmodel.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.usecase.SettingPaymentAddUseCase
import co.kr.woowahan_accountbook.domain.usecase.SettingPaymentUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PaymentAddViewModel @Inject constructor(
    private val settingPaymentAddUseCase: SettingPaymentAddUseCase,
    private val settingPaymentUpdateUseCase: SettingPaymentUpdateUseCase
) : ViewModel() {
    val name = MutableLiveData<String>()
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun addPayment() {
        viewModelScope.launch {
            runCatching {
                settingPaymentAddUseCase(requireNotNull(name.value))
            }.onSuccess {
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }

    fun updatePayment(id: Int) {
        viewModelScope.launch {
            runCatching {
                settingPaymentUpdateUseCase(id, requireNotNull(name.value))
            }.onSuccess {
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }
}