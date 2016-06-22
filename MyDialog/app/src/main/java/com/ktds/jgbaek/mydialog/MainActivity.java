package com.ktds.jgbaek.mydialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnDialog;
    private Button btnDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDialog = (Button) findViewById(R.id.btnDialog);
        btnDialog2 = (Button) findViewById(R.id.btnDialog2);

        final List<String> selectedItems = new ArrayList<String>();

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"It/Computer", "Game", "Fassion", "VR", "Kidult", "Sports", "Music", "Movie"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("관심분야를 선택하세요.")
                        .setMultiChoiceItems(
                                items,
                                new boolean[]{false, false, false, false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(isChecked){
                                            Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                                            selectedItems.add(items[which]);
                                        }
                                        else {
                                            selectedItems.remove(items[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(selectedItems.size()==0){
                                    Toast.makeText(MainActivity.this, "선택된 관심 분야가 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    String items = "";
                                    for(String seletedItem : selectedItems){
                                        items += (seletedItem + ", ");
                                    }
                                    selectedItems.clear();

                                    items = items.substring(0, items.length()-2);
                                    Toast.makeText(MainActivity.this, items, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create().show();
            }
        });
        btnDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"It/Computer", "Game", "Fassion", "VR", "Kidult", "Sports", "Music", "Movie"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("관심분야를 선택하세요.")
                        .setSingleChoiceItems(
                                items,
                               0,
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       selectedIndex[0] = which;
                                   }
                               }
                        )
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, items[selectedIndex[0]], Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }
        });
    }
}
