package by.st.generatorforms.recyclerpayload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var sec = 0

    private var _updateData: MutableLiveData<Int> = MutableLiveData<Int>()
    val updateData: LiveData<Int> = _updateData

    fun startToUpdate() {
        viewModelScope.launch {
            while (sec < 1000) {
                delay(500)
                _updateData.value = sec
                sec++
            }
        }
    }
}