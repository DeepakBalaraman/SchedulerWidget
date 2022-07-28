package com.deepakb.app.schedulewidget;

import static com.deepakb.app.schedulewidget.TimeTableWidget.changeWidgetContent;
import static com.deepakb.app.schedulewidget.TimeTableWidget.setInitValues;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private final EditText[] editText = new EditText[48];
    private final String[] editTextVals = new String[48];
    protected static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(     //Refer to the saved (or default for the first time) values
                this,
                R.xml.def_schedule,
                false
        );

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        for(int i=0; i<48; i++) {      //Set Initial Values of Widget Elements (6x8 matrix)

            editText[i] = findViewById( //Find all EditText elements in the homepage
                    getApplicationContext().getResources().getIdentifier(
                            "t" + i,
                            "id",
                            getPackageName())
            );

            editText[i].setText(    //Set previously stored text as default values in EditTexts
                    editTextVals[i]=sharedPreferences.getString("d"+i, "")
            );

            editText[i].addTextChangedListener(new MultiTextWatcher(this, i));  //Listen to text change event in all EditTexts
        }

        setInitValues(editTextVals);    //send Initial values of EditTexts to Widget Activity

    }
}

class MultiTextWatcher extends MainActivity implements TextWatcher{ //Custom TextWatcher implementation

    private final int i;
    private final Context context;

    public MultiTextWatcher(Context context, int i){
        this.i = i;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void afterTextChanged(Editable editable) {

        SharedPreferences.Editor editor = sharedPreferences.edit(); //Enable editing of shared preferences

        editor.putString(   //edit the corresponding string value
                "d"+i,
                editable.toString()
        );
        editor.apply(); //save changes

        changeWidgetContent(context, i, editable.toString());    //Apply changes to Widgets
    }

}