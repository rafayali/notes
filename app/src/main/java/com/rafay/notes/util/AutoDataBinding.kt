package com.rafay.notes.util

import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AutoDataBinding<T : ViewDataBinding>(
    private val activity: ComponentActivity,
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<ComponentActivity, T> {

    private var _value: T? = null

    private val lifeCycleObserver = object : LifecycleObserver {

        @Suppress("unused")
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            activity.lifecycle.removeObserver(this)
            _value = null
        }
    }

    init {
        activity.lifecycle.addObserver(lifeCycleObserver)
    }

    override fun getValue(thisRef: ComponentActivity, property: KProperty<*>): T {
        if (_value == null) {
            _value = DataBindingUtil.setContentView(thisRef, layoutRes)
        }
        return _value ?: throw IllegalStateException("cannot access null binding value")
    }
}

/**
 * Lazy kotlin property which creates [ViewDataBinding] instance of layout resource.
 */
fun <T : ViewDataBinding> ComponentActivity.dataBinding(@LayoutRes layoutRes: Int) =
    AutoDataBinding<T>(this, layoutRes)