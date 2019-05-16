package com.example.cuatroenraya.activities

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.preference.RingtonePreference
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.example.cuatroenraya.R




/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        fragmentManager.beginTransaction().replace(android.R.id.content,
            GeneralPreferenceFragment()).commit()
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * {@inheritDoc}
     */
    override fun onIsMultiPane(): Boolean {
        return isXLargeTablet(this)
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        //loadHeadersFromResource(R.xml.pref_headers, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || GeneralPreferenceFragment::class.java.name == fragmentName
                || DataSyncPreferenceFragment::class.java.name == fragmentName
                || NotificationPreferenceFragment::class.java.name == fragmentName
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("playerName"))
            bindPreferenceSummaryToValue(findPreference("themes_list"))
            bindPreferenceSummaryToValue(findPreference(COLOR1))
            bindPreferenceSummaryToValue(findPreference(COLOR2))

            val preference = findPreference("prueba")
            preference.onPreferenceClickListener = Preference.OnPreferenceClickListener{
                Toast.makeText(activity, "Preferencia pulsada", Toast.LENGTH_LONG).show()
                true
            }

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val song = MediaPlayer.create(getActivity(),R.raw.backgroud)
            val isChecked = sharedPreferences.getBoolean("music", false)

            if(isChecked){
                song.start()
            }else if(!isChecked){
                song.release()
            }


        }


        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }

    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class NotificationPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_notification)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"))
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class DataSyncPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_data_sync)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"))
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    companion object {

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val listPreference = preference
                val index = listPreference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                    if (index >= 0)
                        listPreference.entries[index]
                    else
                        null
                )

            } else if (preference is RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent)

                } else {
                    val ringtone = RingtoneManager.getRingtone(
                        preference.getContext(), Uri.parse(stringValue)
                    )

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null)
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        val name = ringtone.getTitle(preference.getContext())
                        preference.setSummary(name)
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        val PLAYERUUID = "playerUUID"
        val PLAYER_NAME = "playerName"
        var ONLINE = "OnlineMode"
        var COLOR1 = "color1"
        var COLOR2 = "color2"

        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         */
        private fun isXLargeTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }



        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.

         * @see .sBindPreferenceSummaryToValueListener
         */
        private fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager.getDefaultSharedPreferences(preference.context).getString(preference.key, "")
            )
        }

        /**
         * @brief Funcion para activar el modo online o no
         * @param context contexto
         * @param mode valor de la variable
         */
        fun setOnlineMode(context: Context, mode: Boolean) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putBoolean(SettingsActivity.ONLINE, mode)
            editor.commit()
        }

        /**
         * @brief FUncion para obtener el valor de la preferencia de modo online
         * @param context contexto
         * @return true o false
         */
        fun getOnlineMode(context: Context?): Boolean {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(ONLINE, false)
        }

        /**
         * @brief Funcion para settear el player ID
         * @param context contexto
         * @param playerUuid ID del player
         */
        fun setPlayerUUID(context: Context, playerUuid: String) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.PLAYERUUID, playerUuid)
            editor.commit()
        }

        /**
         * @brief Funcion para settear el nombre del jugador
         * @param context contexto
         * @param playerName ID del player
         */
        fun setPlayerName(context: Context, playerName: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.PLAYER_NAME, playerName)
            editor.commit()
        }

        /**
         * @brief Funcion para obetner el ID del jugador
         * @param context contexto
         * @retrurn ID del player
         */
        fun getPlayerUUID(context: Context?): String {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PLAYERUUID, "")
        }

        /**
         * @brief Funcion para obetner el nombre del jugador
         * @param context contexto
         * @retrurn nombre del player
         */
        fun getPlayerName(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PLAYER_NAME, "")
        }

        fun getColorP1(context: Context): String {
            return converColorCoin(PreferenceManager.getDefaultSharedPreferences(context).getString(COLOR1, ""))
        }

        fun setColorP1(context: Context, color: Int) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.COLOR1, color)
            editor.commit()
        }

        fun getColorP2(context: Context): String {
            return converColorCoin(PreferenceManager.getDefaultSharedPreferences(context).getString(COLOR2, ""))
        }

        fun setColorP2(context: Context, color: Int) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.COLOR1, color)
            editor.commit()
        }

        fun converColorCoin(option: String): String {
            if (option == "1") {
                return "#FF0000"
            } else if (option == "2") {
                return "#3F51B5"
            } else if (option == "3") {
                return "#39C93F"
            } else if (option == "4") {
                return "#FFF000"
            }
            return "#000000"
        }

    }
    /**
     * @brief carga el tema correcto
     */
    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()

        /*Preferencias*/
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themeVal = sharedPreferences.getString("themes_list", "1")
        if(themeVal.toInt() == 1){
            theme.applyStyle(R.style.AppTheme, true)
        }else if(themeVal.toInt() == 2){
            theme.applyStyle(R.style.AppTheme_Blue, true)
        }else if (themeVal.toInt() == 3){
            theme.applyStyle(R.style.AppTheme_Green,true )
        }
        /*Preferencias*/

        return theme
    }
}