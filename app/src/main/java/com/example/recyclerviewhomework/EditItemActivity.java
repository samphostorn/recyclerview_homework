package com.example.recyclerviewhomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recyclerviewhomework.model.ListItem;

public class EditItemActivity extends AppCompatActivity {
private EditText description,title,viewer;
private Button btnsave;
private ListItem listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initUI();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChange();
            }
        });
    }

    private void initUI(){
        description=findViewById(R.id.editDescription);
        title=findViewById(R.id.editTitle);
        viewer=findViewById(R.id.editViewer);
        btnsave=findViewById(R.id.btnSave);

        //get data from intent
        if(getIntent()!=null){
            listItem= getIntent().getParcelableExtra("data");
            description.setText(listItem.getDescription());
            title.setText(listItem.getTitle());
            viewer.setText(listItem.getViewer());
        }
    }



    private void saveChange(){
        listItem.setDescription(description.getText().toString());
        listItem.setTitle(title.getText().toString());
        listItem.setViewer(viewer.getText().toString());

        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putParcelable("data",listItem);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);
        finish();

    }
}
