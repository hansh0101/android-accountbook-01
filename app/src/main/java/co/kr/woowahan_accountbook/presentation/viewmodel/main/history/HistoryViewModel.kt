package co.kr.woowahan_accountbook.presentation.viewmodel.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.usecase.history.HistoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historiesUseCase: HistoriesUseCase
) : ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private val _histories = MutableLiveData<List<HistoryItem>>()
    val histories: LiveData<List<HistoryItem>> get() = _histories

    fun getHistories(year: Int, month: Int) {
        viewModelScope.launch {
            runCatching {
                historiesUseCase(year, month)
            }.onSuccess {
                Timber.tag("zzz").i(it.toString())
                _histories.value = it
                _isSuccess.value = true
            }.onFailure {
                _isSuccess.value = false
                Timber.e(it)
            }
        }
    }
}