package com.deepakb.app.schedulewidget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class TimeTableWidget extends AppWidgetProvider {

    private static AppWidgetManager appWidgetManager;
    private static RemoteViews widgetViews;

    @SuppressLint("StaticFieldLeak")
    private static Context widgetContext;

    private static int[] widgetTextIDs;
    private static String[] values;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

        appWidgetManager = AppWidgetManager.getInstance(context);
        widgetContext = context;
        try {

            onUpdate(
                    context,
                    appWidgetManager,
                    appWidgetManager.getAppWidgetIds(
                            new ComponentName(
                                    widgetContext.getPackageName(),
                                    TimeTableWidget.class.getName()
                            )
                    )
            );

        } catch (NullPointerException e){   //If widget is not found in the homescreen

            Toast.makeText(widgetContext, "Please add a widget to the homescreen", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onDisabled(Context context) { }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        widgetTextIDs = new int[]{  //IDs of Widget TextViews
                R.id.r00, R.id.r01, R.id.r02, R.id.r03, R.id.r04, R.id.r05, R.id.r06, R.id.r07,
                R.id.r10, R.id.r11, R.id.r12, R.id.r13, R.id.r14, R.id.r15, R.id.r16, R.id.r17,
                R.id.r20, R.id.r21, R.id.r22, R.id.r23, R.id.r24, R.id.r25, R.id.r26, R.id.r27,
                R.id.r30, R.id.r31, R.id.r32, R.id.r33, R.id.r34, R.id.r35, R.id.r36, R.id.r37,
                R.id.r40, R.id.r41, R.id.r42, R.id.r43, R.id.r44, R.id.r45, R.id.r46, R.id.r47,
                R.id.r50, R.id.r51, R.id.r52, R.id.r53, R.id.r54, R.id.r55, R.id.r56, R.id.r57
        };

        // Construct the RemoteViews object
        widgetViews = new RemoteViews(context.getPackageName(), R.layout.time_table_widget);

        //Initialize text to all widget elements
        for (int i=0; i<48; i++)
            widgetViews.setTextViewText(widgetTextIDs[i], values[i]);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widgetViews);
    }

    public static void changeWidgetContent(Context context, int i, String value){

        try{

            //Update local element value, which is to be used when a new widget is added after making changes
            values[i] = value;

            //Replace text content of the given TextView element
            widgetViews.setTextViewText(widgetTextIDs[i], value);

            //Partially update the required TextView in all the widget instances
            appWidgetManager.partiallyUpdateAppWidget(
                    appWidgetManager.getAppWidgetIds(
                            new ComponentName(
                                    widgetContext.getPackageName(),
                                    TimeTableWidget.class.getName()
                            )
                    ),
                    widgetViews
            );

        }catch (Exception e){

            Toast.makeText(context, "Please add a widget to the homescreen", Toast.LENGTH_SHORT).show();

        }
    }

    public static void setInitValues(String[] editTextVals){ values = editTextVals; }
}