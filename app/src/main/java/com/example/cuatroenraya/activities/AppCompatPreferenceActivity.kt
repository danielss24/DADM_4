package com.example.cuatroenraya.activities

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup

/**
 * A [android.preference.PreferenceActivity] which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
abstract class AppCompatPreferenceActivity : PreferenceActivity() {

    /**
     * @brief funcion creadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.installViewFactory()
        delegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    /**
     * @brief funcion postcreadora de vista/controlador
     * @param savedInstanceState vista
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate.onPostCreate(savedInstanceState)
    }

    val supportActionBar: ActionBar?
        get() = delegate.supportActionBar


    /**
     * @brief Funcion para habilitar la barra de acciones
     * @param toolbar la barra de acciones
     */
    fun setSupportActionBar(toolbar: Toolbar?) {
        delegate.setSupportActionBar(toolbar)
    }

    /**
     * @brief getter del menu inflater
     * @return el menu inflater
     */
    override fun getMenuInflater(): MenuInflater {
        return delegate.menuInflater
    }

    /**
     * @brief setter del content view
     * @param layoutResID layout
     */
    override fun setContentView(@LayoutRes layoutResID: Int) {
        delegate.setContentView(layoutResID)
    }

    /**
     * @brief setter del content view
     * @param view vista
     */
    override fun setContentView(view: View) {
        delegate.setContentView(view)
    }

    /**
     * @brief setter del content view
     * @param view vista
     * @param params parametros
     */
    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate.setContentView(view, params)
    }

    /**
     * @brief setter del content view
     * @param view vista
     * @param params parametros
     */
    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate.addContentView(view, params)
    }

    /**
     * @brief on post resume
     */
    override fun onPostResume() {
        super.onPostResume()
        delegate.onPostResume()
    }

    /**
     * @brief funcion para actualizar cuando se cambia el titulo
     * @param title nuevo titulo
     * @param color nuevo color
     */
    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        delegate.setTitle(title)
    }

    /**
     * @brief funcion para actualizar cuando se cambia la configuracion
     * @param newConfig nuevo titulo
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        delegate.onConfigurationChanged(newConfig)
    }

    /**
     * @brief funcion para cuando se para la vista
     */
    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }

    /**
     * @brief funcion para cuando se destruye la vista
     */
    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    /**
     * @brief invalidate del menu de opciones
     */
    override fun invalidateOptionsMenu() {
        delegate.invalidateOptionsMenu()
    }

    private val delegate: AppCompatDelegate by lazy {
        AppCompatDelegate.create(this, null)
    }
}
