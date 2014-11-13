package kr.hs.zion.baekhyang14;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


/**
 * Helper class for showing and canceling data download task
 * notifications.
 * <p/>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class DataDownloadTaskNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static String NOTIFICATION_TAG = "DataDownloadTask";

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     * <p/>
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     * <p/>
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of data download task notifications. Make
     * sure to follow the
     * <a href="https://developer.android.com/design/patterns/notifications.html">
     * Notification design guidelines</a> when doing so.
     *
     * @see #cancel(Context)
     */
    public static void notify(final Context context,int ReqCode) {
        final Resources res = context.getResources();

        String TickerText = res.getString(R.string.data_download_task_ticker);
        String TitleText = res.getString(
                R.string.data_download_task_notification_title_template);
        String DescText = res.getString(
                R.string.data_download_task_notification_placeholder_text_template);
        switch (ReqCode){
            case 0:
                TickerText = res.getString(R.string.data_download_task_ticker);
                TitleText = res.getString(R.string.data_download_task_notification_title_template);
                DescText = res.getString(R.string.data_download_task_notification_placeholder_text_template);
                NOTIFICATION_TAG = "DataDownloadTask";
                break;
            case 1:
                TickerText = res.getString(R.string.booth_data_done_title_ticker);
                TitleText = res.getString(R.string.booth_data_done_title_ticker);
                DescText = res.getString(R.string.booth_data_done_desc);
                NOTIFICATION_TAG = "BOOTH_DONE";
                break;
            case 2:
                TickerText = res.getString(R.string.performance_data_done_title_ticker);
                TitleText = res.getString(R.string.performance_data_done_title_ticker);
                DescText = res.getString(R.string.performance_data_done_desc);
                NOTIFICATION_TAG = "PERFORMANCE_DONE";
                break;
            case 3:
                TickerText = res.getString(R.string.download_failed);
                TitleText = res.getString(R.string.download_failed);
                DescText = res.getString(R.string.booth_data_failed);
                NOTIFICATION_TAG = "BOOTH_FAILED";
                break;
            case 4:
                TickerText = res.getString(R.string.download_failed);
                TitleText = res.getString(R.string.download_failed);
                DescText = res.getString(R.string.performance_data_failed);
                NOTIFICATION_TAG = "PERFORMANCE_FAILED";
                break;

        }

        String tickerString = TickerText;
        final String ticker = tickerString;
        final String title = TitleText;
        final String text = DescText;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_SOUND)

                        // Set required fields, including the small icon, the
                        // notification title, and text.


                .setSmallIcon(R.drawable.ic_stat_data_download_task)
                .setContentTitle(title)
                .setContentText(text)

                        // All fields below this line are optional.

                        // Use a default priority (recognized on devices running Android
                        // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                        // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);
                switch(ReqCode){
                    case 0:
                        builder.setSmallIcon(R.drawable.ic_stat_data_download_task);
                        break;
                    case 1:
                        builder.setSmallIcon(R.drawable.ic_stat_done);
                        break;
                    case 2:
                        builder.setSmallIcon(R.drawable.ic_stat_done);
                        break;
                    case 3:
                        builder.setSmallIcon(R.drawable.ic_stat_error);
                        break;
                    case 4:
                        builder.setSmallIcon(R.drawable.ic_stat_error);
                        break;
                 }

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    /**
     * Cancels any notifications of this type previously shown using
     * {@link #notify(Context, String, int)}.
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}