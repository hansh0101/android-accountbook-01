package co.kr.woowahan_accountbook.presentation.viewmodel.main.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.entity.calendar.CalendarItem
import co.kr.woowahan_accountbook.domain.usecase.calendar.CalendarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCase: CalendarUseCase
) : ViewModel() {
    private val _year = MutableLiveData<Int>()
    val year: LiveData<Int> get() = _year
    private val _month = MutableLiveData<Int>()
    val month: LiveData<Int> get() = _month
    private val _income = MutableLiveData<Int>()
    val income: LiveData<Int> get() = _income
    private val _expenditure = MutableLiveData<Int>()
    val expenditure: LiveData<Int> get() = _expenditure
    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> get() = _total

    private val _calendarItems = MutableLiveData<List<CalendarItem>>()
    val calendarItems: LiveData<List<CalendarItem>> get() = _calendarItems
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

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

    fun getCalendarItems() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                calendarUseCase(
                    requireNotNull(year.value),
                    requireNotNull(month.value)
                )
            }.onSuccess { list ->
                _calendarItems.value = list
                _income.value = list.sumOf { it.income }
                _expenditure.value = list.sumOf { it.expenditure }
                _total.value = requireNotNull(income.value) + requireNotNull(expenditure.value)
            }.onFailure {
                Timber.e(it)
            }.also {
                _isLoading.value = false
            }
        }
    }
}