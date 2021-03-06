package net.somethingdreadful.MAL;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import net.somethingdreadful.MAL.account.AccountService;

import java.util.Locale;

public class Theme extends Application {

    public static boolean darkTheme;
    Locale locale;
    Configuration config;

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics.start(this);
        PrefManager.create(getApplicationContext());
        AccountService.create(getApplicationContext());

        locale = PrefManager.getLocale();
        darkTheme = PrefManager.getDarkTheme();
        config = new Configuration();
        config.locale = locale;
        setLanguage(); //Change language when it is started
        Crashlytics.setString("Language", locale.toString());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLanguage(); //Change language after orientation.
    }

    public void setLanguage() {
        Resources res = getBaseContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    /**
     * This will apply the right theme and background.
     *
     * @param activity The activity which should be themed
     * @param view     The main view id
     * @param card     If the contents contains a card
     */
    public static void setTheme(Activity activity, int view, boolean card) {
        if (view != 0)
            activity.setContentView(view);
        if (darkTheme) {
            activity.setTheme(R.style.AtarashiiDarkBg);
            activity.getWindow().getDecorView().setBackgroundColor(activity.getResources().getColor(card ? R.color.bg_dark_card : R.color.bg_dark));
        } else {
            activity.getWindow().getDecorView().setBackgroundColor(activity.getResources().getColor(card ? R.color.bg_light_card : R.color.bg_light));
        }
    }

    /**
     * Set a background with the default card theme.
     *
     * @param c    The context
     * @param view The view which should use this drawable
     */
    public static void setBackground(Context c, View view) {
        setBackground(c, view, getDrawable());
    }

    /**
     * Set the background of a view.
     *
     * @param c The context
     * @param view The view which should use this drawable
     * @param id The drawable/color id of the wanted color/drawable
     */
    public static void setBackground(Context c, View view, int id) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(c.getResources().getDrawable(id));
        } else {
            view.setBackground(c.getResources().getDrawable(id));
        }
    }

    /**
     * Get the default drawable.
     *
     * @return int The id of the default card drawable
     */
    private static int getDrawable() {
        return darkTheme ? R.drawable.highlite_details_dark : R.drawable.highlite_details;
    }
}
