package co.kr.woowahan_accountbook.presentation.viewmodel.main.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import co.kr.woowahan_accountbook.domain.usecase.statistics.StatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsUseCase: StatisticsUseCase
) : ViewModel() {
    private val _year = MutableLiveData<Int>()
    val year: LiveData<Int> get() = _year
    private val _month = MutableLiveData<Int>()
    val month: LiveData<Int> get() = _month

    private val _statistics = MutableLiveData<List<StatisticsItem>>()
    val statistics: LiveData<List<StatisticsItem>> get() = _statistics

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

    fun getStatistics() {
        viewModelScope.launch {
            runCatching {
                statisticsUseCase(
                    requireNotNull(year.value),
                    requireNotNull(month.value)
                )
            }.onSuccess {
                _statistics.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}