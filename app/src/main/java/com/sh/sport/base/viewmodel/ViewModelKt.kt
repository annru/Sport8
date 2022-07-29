package com.sh.sport.base.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun <reified VM : ViewModel> FragmentActivity.createViewModel(clazz: Class<VM>): VM {
    return ViewModelProviders.of(this).get(clazz)
}

inline fun <reified VM : ViewModel> FragmentActivity.createViewModel(
    clazz: Class<VM>,
    factory: ViewModelProvider.Factory
): VM {
    return ViewModelProviders.of(this, factory).get(clazz)
}

inline fun <reified VM : ViewModel> Fragment.createViewModel(clazz: Class<VM>): VM {
    return ViewModelProviders.of(this).get(clazz)
}

inline fun <reified VM : ViewModel> FragmentActivity.createViewModel(
    key: String,
    clazz: Class<VM>
): VM {
    return ViewModelProviders.of(this).get(key, clazz)
}

inline fun <reified VM : ViewModel> Fragment.createViewModel(key: String, clazz: Class<VM>): VM {
    return ViewModelProviders.of(this.requireActivity()).get(key, clazz)
}

inline fun <reified VM : ViewModel> Fragment.createViewModel(
    clazz: Class<VM>,
    factory: ViewModelProvider.Factory
): VM {
    return ViewModelProviders.of(this, factory).get(clazz)
}


fun <T> LiveData<T>.observe2(
    lifecycleOwner: LifecycleOwner,
    action: (message: T) -> Unit
) {
    observe(lifecycleOwner, Observer {
        action(it)
    })
}

@JvmOverloads
fun ViewModel.launch(delay: Long = 0, block: suspend () -> Unit): Job {
    return viewModelScope.launch {
        kotlinx.coroutines.delay(delay)
        block()
    }
}
