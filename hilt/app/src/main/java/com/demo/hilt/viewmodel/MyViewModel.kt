package com.demo.hilt.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.demo.hilt.model.Repository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

//v1
//@ActivityRetainedScoped
//class MyViewModel @Inject constructor(val repository: Repository): ViewModel(){
//
//
//}

//v2
class MyViewModel @ViewModelInject constructor(val repository: Repository): ViewModel(){


}

