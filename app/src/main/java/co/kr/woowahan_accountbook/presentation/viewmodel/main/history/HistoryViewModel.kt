package co.kr.woowahan_accountbook.presentation.viewmodel.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.usecase.history.HistoriesDeleteUseCase
import co.kr.woowahan_accountbook.domain.usecase.history.HistoriesUseCase
import co.kr.woowahan_accountbook.domain.usecase.history.HistoryTotalAmountByTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historiesUseCase: HistoriesUseCase,
    private val historyTotalAmountByTypeUseCase: HistoryTotalAmountByTypeUseCase,
    private val historyDeleteUseCase: HistoriesDeleteUseCase
) : ViewModel() {
    sealed class HistoryUiState {
        object Init : HistoryUiState()
        data class Success(val histories: List<HistoryItem>) : HistoryUiState()
        data class Error(val message: String?) : HistoryUiState()
    }

    sealed class IncomeAmountUiState {
        object Init : IncomeAmountUiState()
        data class Success(val totalIncomeAmount: Int) : IncomeAmountUiState()
        data class Error(val message: String?) : IncomeAmountUiState()
    }

    sealed class ExpenditureAmountUiState {
        object Init : ExpenditureAmountUiState()
        data class Success(val totalExpenditureAmount: Int) : ExpenditureAmountUiState()
        data class Error(val message: String?) : ExpenditureAmountUiState()
    }

    private val _historyUiState = MutableLiveData<HistoryUiState>(HistoryUiState.Init)
    val historyUiState: LiveData<HistoryUiState> get() = _historyUiState

    private val _totalIncomeAmountUiState =
        MutableLiveData<IncomeAmountUiState>(IncomeAmountUiState.Init)
    val totalIncomeAmountUiState: LiveData<IncomeAmountUiState> get() = _totalIncomeAmountUiState

    private val _totalExpenditureAmountUiState =
        MutableLiveData<ExpenditureAmountUiState>(ExpenditureAmountUiState.Init)
    val totalExpenditureAmountUiState: LiveData<ExpenditureAmountUiState> get() = _totalExpenditureAmountUiState

    private val _year = MutableLiveData<Int>()
    val year: LiveData<Int> get() = _year

    private val _month = MutableLiveData<Int>()
    val month: LiveData<Int> get() = _month

    private val _historiesSelected = MutableLiveData<List<HistoryItem>>(listOf())
    val historiesSelected: LiveData<List<HistoryItem>> get() = _historiesSelected

    private val _isIncomeSelected = MutableLiveData<Boolean>(true)
    val isIncomeSelected: LiveData<Boolean> get() = _isIncomeSelected

    private val _isExpenditureSelected = MutableLiveData<Boolean>(true)
    val isExpenditureSelected: LiveData<Boolean> get() = _isExpenditureSelected

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
                _historyUiState.value = HistoryUiState.Init
                historiesUseCase(
                    requireNotNull(year.value),
                    requireNotNull(month.value),
                    requireNotNull(isIncomeSelected.value),
                    requireNotNull(isExpenditureSelected.value)
                )
            }.onSuccess {
                _historyUiState.value = HistoryUiState.Success(it)
            }.onFailure {
                _historyUiState.value = HistoryUiState.Error(it.message)
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
                if (isIncome) _totalIncomeAmountUiState.value = IncomeAmountUiState.Success(it)
                else _totalExpenditureAmountUiState.value = ExpenditureAmountUiState.Success(it)
            }.onFailure {
                if (isIncome) _totalIncomeAmountUiState.value =
                    IncomeAmountUiState.Error(it.message)
                else _totalExpenditureAmountUiState.value =
                    ExpenditureAmountUiState.Error(it.message)
                Timber.e(it)
            }
        }
    }

    fun onClickIncomeButton() {
        _isIncomeSelected.value = !requireNotNull(isIncomeSelected.value)
    }

    fun onClickExpenditureButton() {
        _isExpenditureSelected.value = !requireNotNull(isExpenditureSelected.value)
    }

    fun updateSelectedItems(item: HistoryItem) {
        val list = requireNotNull(historiesSelected.value).toMutableList()
        if (list.find { it == item } == null) list.add(item)
        else list.remove(item)
        _historiesSelected.value = list
    }

    fun clearSelectedItems() {
        _historiesSelected.value = listOf()
    }

    fun deleteSelectedItems() {
        val ids = requireNotNull(historiesSelected.value).map { it.id }
        viewModelScope.launch {
            runCatching {
                historyDeleteUseCase(ids)
            }.onSuccess {
                _historiesSelected.value = listOf()
                getHistories()
                getTotalAmountByType(true)
                getTotalAmountByType(false)
                Timber.i("success")
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}