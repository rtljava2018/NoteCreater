package com.rkm.notbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rkm.notbook.db.model.NoteModel;
import com.rkm.notbook.db.viewmodel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import petrov.kristiyan.colorpicker.ColorPicker;

public class NoteEditor extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.txt_reminder)
    TextView textreminder;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.imgnotification)
    ImageView imgnotification;
    @BindView(R.id.imgswitch)
    CheckBox imgswitch;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;

    private NoteViewModel noteViewModel;
    private NoteModel noteModel;
    String noteId = "";
    String selctedcolor = "";
    boolean isPin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        ButterKnife.bind(this);
        /*Toolbar toolbar = findViewById(R.id.toolbar);*/
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            noteId = bundle.getString("noteid");
            if (noteId.equalsIgnoreCase("")) {
                noteId = System.currentTimeMillis() + "";
                noteModel = new NoteModel();
            }

        } else {
            noteId = System.currentTimeMillis() + "";
            noteModel = new NoteModel();
            noteModel.setNotId(noteId);
        }
        //String venName = bundle.getString("VENUE_NAME");

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        noteViewModel.getMOdelItem(noteId).observe(this, new Observer<NoteModel>() {
            @Override
            public void onChanged(NoteModel data) {
                if (data != null) {
                    noteModel = new NoteModel();
                    noteModel = data;
                    setvalue(noteModel);
                }/*else {
                    noteModel=new NoteModel();
                }*/
            }
        });

        /*FloatingActionButton fab = findViewById(R.id.fab);*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initColor();
                //mainLayout.setBackgroundColor(Color.parseColor(selctedcolor));
            }
        });
        imgswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imgswitch.setChecked(isChecked);
                isPin = imgswitch.isChecked();
                Toast.makeText(getApplicationContext(), isPin ? "Note set pin" : "Note set pin deselect", Toast.LENGTH_LONG).show();
            }
        });

        textreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog();
            }
        });

    }
    private void showDateTimeDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        textreminder.setText("Remind me-"+simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(NoteEditor.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(NoteEditor.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void initColor() {
        final ColorPicker colorPicker = new ColorPicker(NoteEditor.this);
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("#82B926");
        colors.add("#a276eb");
        colors.add("#6a3ab2");
        colors.add("#666666");
        colors.add("#FFFF00");
        colors.add("#3C8D2F");
        colors.add("#FA9F00");
        colors.add("#FF0000");
        colors.add("#82B926");
        colors.add("#a276eb");
        colors.add("#6a3ab2");
        colors.add("#666666");
        colors.add("#FFFF00");
        colors.add("#3C8D2F");
        colors.add("#FA9F00");
        colors.add("#FF0000");
        colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                Log.e("selected color", colors.get(position) + "");
                selctedcolor = colors.get(position);
                mainLayout.setBackgroundColor(Color.parseColor(selctedcolor));
            }

            @Override
            public void onCancel() {
                mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        })
                .setColors(colors)
                .setColumns(4)
                .setRoundColorButton(true)
                .show();
    }

    private void setvalue(NoteModel noteModel) {
        etTitle.setText("" + noteModel.getNoteTitle());
        etMsg.setText("" + noteModel.getNoteDes());
        isPin = noteModel.isPinNote();
        selctedcolor = noteModel.getNoteTagcolorcode();
        if (!selctedcolor.equalsIgnoreCase("")) {
            mainLayout.setBackgroundColor(Color.parseColor(selctedcolor));
        }
        imgswitch.setChecked(isPin);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!etMsg.getText().toString().isEmpty() || !etTitle.getText().toString().isEmpty()) {
            saveNote();
        }
    }

    private void saveNote() {

        noteModel.setNoteDes(etMsg.getText().toString());
        noteModel.setNoteTitle(etTitle.getText().toString());
        noteModel.setPinNote(isPin);
        noteModel.setNoteTagcolorcode(selctedcolor);
        noteModel.setTimeForaddNote(System.currentTimeMillis());
        noteModel.setRemindertime(System.currentTimeMillis());
        noteViewModel.inserData(noteModel);
    }

   /* @OnClick({R.id.tv_page, R.id.imgnotification, R.id.imgswitch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_page:
                break;
            case R.id.imgnotification:
                break;
          *//*  case R.id.imgswitch:

                break;*//*
        }
    }*/
}