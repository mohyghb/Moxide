package com.wixsite.motrixofficial.moxide;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //periodic table stored inside an array list
    static ArrayList<Element> PERIODIC_TABLE;


    //get input from the user. either a formula or the compound's name
    private EditText Formula_editText;


    //found elements in a listview
    private ArrayList<Element> Elements_Found;
    private ArrayAdapter<Element> elementArrayAdapter;
    private ListView listView;
    private Button About;

    private ClipboardManager clipboard;



    //molar mass buttons
    private Button MolarMass;
    static double curMolarMass = 0.0;

    private SearchView actionSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initClass();
    }


    //init all the variables and objects
    private void initClass()
    {
        loadPeriodicTable();
//        initEditText();
        initElementsFound();
        initArrayAdapter();
        initListView();
        initButtons();
        initClipBoardManager();
    }

    //loading the periodic table from a text file
    private void loadPeriodicTable()
    {
        PERIODIC_TABLE = new ArrayList<>();
        InputStream inputStream = this.getResources().openRawResource(R.raw.pt);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-16LE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(bufferedReader!=null){
            try{
                String line;
                while((line = bufferedReader.readLine())!=null){
                    Element element = new Element(line);
                    PERIODIC_TABLE.add(element);
                }
                inputStream.close();
            }catch(Exception e)
            {

            }
        }
    }

    //init edit text for user input
//    private void initEditText()
//    {
//        this.Formula_editText = findViewById(R.id.Formula_editText);
//        this.Formula_editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }




    //init found elements from the formula
    private void initElementsFound()
    {
        this.Elements_Found = new ArrayList<>();
    }



    //init array adapter
    private void initArrayAdapter()
    {
        this.elementArrayAdapter = new ElementArrayAdapter(MainActivity.this,0,this.Elements_Found);
    }

    //initListView
    private void initListView()
    {
        this.listView = findViewById(R.id.Elements_Found_ListView);
        this.listView.setAdapter(this.elementArrayAdapter);
        this.listView.setEmptyView(findViewById(R.id.EmptyLayout));
    }


    //updating what we found
    private void updateList(String text)
    {
        if(text.isEmpty())
        {
            this.elementArrayAdapter.clear();
            this.elementArrayAdapter.notifyDataSetChanged();
            updateButtons();
        }
        else{
            this.Elements_Found.clear();
            try{
                curMolarMass = MolarMassCalculator.calculateMolarMass(text,MainActivity.PERIODIC_TABLE,this.Elements_Found);
            }catch(Exception e)
            {
                Toast.makeText(this,"Your number is too large",Toast.LENGTH_SHORT).show();
            }
            this.elementArrayAdapter.notifyDataSetChanged();
            updateButtons();
        }
    }

    //updating the molar mass
    private void updateButtons()
    {
        int element_size = this.Elements_Found.size();
        if(element_size>0)
        {
            if(element_size<=5)
            {
                String text = getFormattedText();
                this.MolarMass.setText(text +"--------------------------\n"+ curMolarMass + " g/mol");
            }
            else
            {
                this.MolarMass.setText(curMolarMass+" g/mol");
            }
            this.MolarMass.setVisibility(View.VISIBLE);

        }
        else{
            this.MolarMass.setVisibility(View.GONE);
        }
    }

    //init buttons
    private void initButtons()
    {
        this.MolarMass = findViewById(R.id.MolarMassButton);
        this.MolarMass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("s",curMolarMass+"");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this,"Molar mass was copied to your clipboard!",Toast.LENGTH_SHORT).show();
            }
        });

        this.About = findViewById(R.id.About);
        this.About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://motrixofficial.wixsite.com/moxide"));
                startActivity(browserIntent);
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        this.actionSearch = (SearchView) mSearch.getActionView();
        initSearchView();
        return true;
    }


    private String getFormattedText()
    {
        String formatted = "";
        for(Element e :this.Elements_Found)
        {
            formatted += e.getFormattedMolarMass()+"\n";
        }
        return formatted;
    }

    private void initClipBoardManager()
    {
        this.clipboard = (ClipboardManager)
            getSystemService(Context.CLIPBOARD_SERVICE);
    }


    private void initSearchView()
    {
        this.actionSearch.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        this.actionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                updateList(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                updateList(s);
                return false;
            }
        });
        this.actionSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
//                searchedPatients = MainActivity.patientArrayList;
//                patientArrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }




}
