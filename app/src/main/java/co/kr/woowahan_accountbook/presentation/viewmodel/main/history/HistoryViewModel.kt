package co.kr.woowahan_accountbook.presentation.viewmodel.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.usecase.history.HistoriesUseCase
import co.kr.woowahan_accountbook.domain.usecase.history.HistoryTotalAmountByTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historiesUseCase: HistoriesUseCase,
    private val historyTotalAmountByTypeUseCase: HistoryTotalAmountByTypeUseCase
) : ViewModel() {
    private val _year = MutableLiveData<Int>()
    val year: LiveData<Int> get() = _year

    private val _month = MutableLiveData<Int>()
    val month: LiveData<Int> get() = _month

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _histories = MutableLiveData<List<HistoryItem>>()
    val histories: LiveData<List<HistoryItem>> get() = _histories
    private val _historiesShown = MutableLiveData<List<HistoryItem>>()
    val historiesShown: LiveData<List<HistoryItem>> get() = _historiesShown

    private val _isIncomeSelected = MutableLiveData<Boolean>(true)
    val isIncomeSelected: LiveData<Boolean> get() = _isIncomeSelected

    private val _isExpenditureSelected = MutableLiveData<Boolean>(true)
    val isExpenditureSelected: LiveData<Boolean> get() = _isExpenditureSelected

    private val _totalIncomeAmount = MutableLiveData<Int>()
    val totalIncomeAmount: LiveData<Int> get() = _totalIncomeAmount
    private val _totalExpenditureAmount = MutableLiveData<Int>()
    val totalExpenditureAmount: LiveData<Int> get() = _totalExpenditureAmount

    fun setDate(year: Int, month: Int) {
        _year.value = year
        _month.value = month
    }

    fun setPreviousMonth() {
        if (requireNotNull(month.value) == 1) {
            _year.value = requireNotNull(year.value) - 1
            _month.value = 12
        } else {
            _month.value = requireNotNull(month.value) - 1
        }
    }

    fun setNextMonth() {
        if (requireNotNull(month.value) == 12) {
            _year.value = requireNotNull(year.value) + 1
            _month.value = 1
        } else {
            _month.value = requireNotNull(month.value) + 1
        }
    }

    fun getHistories() {
        viewModelScope.launch {
            runCatching {
                historiesUseCase(
                    requireNotNull(year.value),
                    requireNotNull(month.value),
                    requireNotNull(isIncomeSelected.value),
                    requireNotNull(isExpenditureSelected.value)
                )
            }.onSuccess {
                _histories.value = it
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }

    fun getTotalAmountByType(isIncome: Boolean) {
        viewModelScope.launch {
            runCatching {
                historyTotalAmountByTypeUseCase(
                    requireNotNull(year.value),
                    requireNotNull(month.value),
                    isIncome
                )
            }.onSuccess {
                _isSuccess.value = true
                if (isIncome) _totalIncomeAmount.value = it
                else _totalExpenditureAmount.value = it
                Timber.tag("ㅎㅇ").i(it.toString())
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }

    fun onClickIncomeButton() {
        _isIncomeSelected.value = !requireNotNull(isIncomeSelected.value)
    }

    fun onClickExpenditure() {
        _isExpenditureSelected.value = !requireNotNull(isExpenditureSelected.value)
    }
}