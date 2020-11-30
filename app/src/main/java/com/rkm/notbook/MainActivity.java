package com.rkm.notbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rkm.notbook.adapter.NoteListAdapter;
import com.rkm.notbook.db.model.NoteModel;
import com.rkm.notbook.db.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_note_list)
    RecyclerView rvNoteList;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.chkswitch)
    CheckBox chkswitch;
    private NoteListAdapter listAdapter;

    private NoteViewModel noteViewModel;
    private boolean isChkList = true;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NoteEditor.class));
            }
        });

        initrecycleview();

        noteViewModel.getLiveList().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> noteModels) {
                listAdapter.setValue((ArrayList<NoteModel>) noteModels);
            }
        });
        chkswitch.setChecked(isChkList);
        chkswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isChkList=isChecked;
                manager.setSpanCount(isChkList?1:2);
            }
        });


    }

    private void initrecycleview() {
        //LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager = new GridLayoutManager(this, isChkList?1:2);
        listAdapter = new NoteListAdapter(this, new ArrayList<NoteModel>());
        listAdapter.setItemOnClickListener(new NoteListAdapter.ItemOnClickListener() {
            @Override
            public void onClickItem(View v, int position, NoteModel item, ArrayList<NoteModel> listOfItem) {

                Intent i = new Intent(getApplicationContext(), NoteEditor.class);

                Bundle bundle = new Bundle();
                bundle.putString("noteid", item.getNotId());

                i.putExtras(bundle);
                startActivity(i);
            }
        });


        rvNoteList.setLayoutManager(manager);
        rvNoteList.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}