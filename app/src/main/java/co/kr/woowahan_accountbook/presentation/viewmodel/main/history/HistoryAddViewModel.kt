package co.kr.woowahan_accountbook.presentation.viewmodel.main.history

import androidx.lifecycle.*
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.usecase.history.HistoryClassificationsUseCase
import co.kr.woowahan_accountbook.domain.usecase.history.HistoryInsertUseCase
import co.kr.woowahan_accountbook.domain.usecase.history.HistoryPaymentsUseCase
import co.kr.woowahan_accountbook.domain.usecase.history.HistoryUpdateUseCase
import co.kr.woowahan_accountbook.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryAddViewModel @Inject constructor(
    private val historyPaymentsUseCase: HistoryPaymentsUseCase,
    private val historyClassificationsUseCase: HistoryClassificationsUseCase,
    private val historyInsertUseCase: HistoryInsertUseCase,
    private val historyUpdateUseCase: HistoryUpdateUseCase
) : ViewModel() {
    private val _isIncome = MutableLiveData<Boolean>(true)
    val isIncome: LiveData<Boolean> get() = _isIncome

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> get() = _date
    val amount = MutableLiveData<Int>(0)
    private val _payment = MutableLiveData<Int>(0)
    val payment: LiveData<Int> get() = _payment
    private val _classification = MutableLiveData<Int>(0)
    val classification: LiveData<Int> get() = _classification
    val description = MutableLiveData<String>("")

    private val _payments = MutableLiveData<List<PaymentDto>>()
    val payments: LiveData<List<PaymentDto>> get() = _payments

    private val _classifications = MutableLiveData<List<ClassificationDto>>()
    val classifications: LiveData<List<ClassificationDto>> get() = _classifications

    private val _item = MutableLiveData<HistoryItem>()
    val item: LiveData<HistoryItem> get() = _item

    private val _buttonEnableFlag = MediatorLiveData<Boolean>().apply {
        addSource(_date) { checkButtonEnableFlag() }
        addSource(amount) { checkButtonEnableFlag() }
        addSource(_payment) { checkButtonEnableFlag() }
    }
    val buttonEnableFlag: LiveData<Boolean> get() = _buttonEnableFlag

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun setItem(item: HistoryItem) = with(item) {
        _item.value = this
        _date.value = "${year}." +
                "${String.format("%,2d", month)}." +
                "${String.format("%,2d", day)}." +
                "${DateUtil.getDayOfWeek(year, month, day)}"
        _payment.value = paymentId
        _classification.value = classificationId
        this@HistoryAddViewModel.amount.value = amount
        this@HistoryAddViewModel.description.value = description
    }

    private fun checkButtonEnableFlag() {
        _buttonEnableFlag.value =
            requireNotNull(_date.value).isNotEmpty() &&
                    requireNotNull(amount.value) != 0 &&
                    requireNotNull(_payment.value) != 0
    }

    fun getPayments() {
        viewModelScope.launch {
            runCatching {
                historyPaymentsUseCase()
            }.onSuccess {
                _payments.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun getClassifications() {
        viewModelScope.launch {
            runCatching {
                historyClassificationsUseCase(requireNotNull(isIncome.value))
            }.onSuccess {
                _classifications.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun setDate(date: String) {
        _date.value = date
    }

    fun onSelectedPaymentItem(id: Int) {
        _payment.value = id
    }

    fun onSelectedClassificationItem(id: Int) {
        _classification.value = id
    }

    fun onClickHistoryType(isIncome: Boolean) {
        _isIncome.value = isIncome
    }

    fun insertHistory() {
        val isIncome = requireNotNull(isIncome.value)
        val dateArray = requireNotNull(date.value).split('.')
        val year = dateArray[0].toInt()
        val month = dateArray[1].toInt()
        val day = dateArray[2].toInt()
        val amountValue = requireNotNull(amount.value)
        val paymentId = requireNotNull(payment.value)
        val classificationId = if(classification.value == 0) {
            if(isIncome) 10
            else 3
        } else requireNotNull(classification.value)
        val descriptionValue = requireNotNull(description.value)

        viewModelScope.launch {
            runCatching {
                historyInsertUseCase(
                    amountValue,
                    descriptionValue,
                    year,
                    month,
                    day,
                    paymentId,
                    classificationId
                )
            }.onSuccess {
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }

    fun updateHistory() {
        val id = requireNotNull(item.value).id
        val dateArray = requireNotNull(date.value).split('.')
        val year = dateArray[0].toInt()
        val month = dateArray[1].toInt()
        val day = dateArray[2].toInt()
        val amountValue = requireNotNull(amount.value)
        val paymentId = requireNotNull(payment.value)
        val classificationId = requireNotNull(classification.value)
        val descriptionValue = requireNotNull(description.value)

        viewModelScope.launch {
            runCatching {
                historyUpdateUseCase(
                    id,
                    amountValue,
                    descriptionValue,
                    year,
                    month,
                    day,
                    paymentId,
                    classificationId
                )
            }.onSuccess {
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }
}