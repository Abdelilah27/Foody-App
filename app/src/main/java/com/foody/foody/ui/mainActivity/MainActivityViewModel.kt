package com.foody.foody.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.foody.foody.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {
}

