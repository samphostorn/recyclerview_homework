package com.example.recyclerviewhomework;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewhomework.adapter.ListItemAdapter;
import com.example.recyclerviewhomework.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity

        implements ListItemAdapter.OnAdapterItemLongClickListener{


    private RecyclerView rvListItem;
    private List<ListItem> listItems=new ArrayList<>();
    private ListItemAdapter listItemAdapter;
    private int itemPosition;
    private Button btnOption;
    public static  final int CODE_REQUEST=99;
    public static  final int EDIT_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     initUI();
     getListItems();
    }


    private  void initUI(){
        rvListItem=findViewById(R.id.rvListItem);
        rvListItem.setLayoutManager(new LinearLayoutManager(this));
        listItemAdapter=new ListItemAdapter(listItems,this);
        rvListItem.setAdapter(listItemAdapter);
//        btnOption=findViewById(R.id.btnOption);


    }

    private  void getListItems(){
       for (int i=0; i<=100;i++){
        listItems.add(new ListItem(i+" Spring-Dependency Injection",
                "CoverLayout","100k",R.drawable.ic_more_horiz_black_24dp));

       }
       listItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(CODE_REQUEST==requestCode && resultCode==RESULT_OK){
            //get new Inbox from AddItemActivity

            ListItem listItem=data.getParcelableExtra("data");
            //add new Inbox to recycler View
            this.listItems.add(0,listItem);
            listItemAdapter.notifyItemInserted(0);
//            scroll(0);
        }

        if(EDIT_REQUEST_CODE==requestCode && resultCode==RESULT_OK){
            ListItem listItem=data.getParcelableExtra("data");
            this.listItems.set(this.itemPosition,listItem);
            listItemAdapter.notifyItemChanged(this.itemPosition);
        }
    }
    public void onAdapterItemLongClicked(int position) {
        this.itemPosition=position;
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }

@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    getMenuInflater().inflate(R.menu.popup_menu,menu);
}

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                Intent intent=new Intent(this,EditItemActivity.class);
                ListItem listItem=listItems.get(this.itemPosition);
                Bundle bundle=new Bundle();
                bundle.putParcelable("data",listItem);
                intent.putExtras(bundle);
                startActivityForResult(intent,EDIT_REQUEST_CODE);
                return  true;
            case R.id.remove:
                removeRecyclerViewItem();

                return true;
            default:  return false;
        }

    }


    private void removeRecyclerViewItem(){
        this.listItems.remove(this.itemPosition);
        listItemAdapter.notifyItemRemoved(this.itemPosition);
        Toast.makeText(this, "removed", Toast.LENGTH_SHORT).show();
    }





    private void scroll(int position){
        rvListItem.smoothScrollToPosition(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                doSearch(s);

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listItems.clear();
                listItems.addAll(oldListItems);
                listItemAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    List<ListItem> oldListItems=new ArrayList<>();

    private void doSearch(String s){
        if(s.isEmpty()){
            return;
        }

        listItems.clear();
        for (ListItem listItem: oldListItems){
            if(listItem.getDescription().contains(s)){
                listItems.add(listItem);
            }
        }
        if(listItems.size()==0)
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        listItemAdapter.notifyDataSetChanged();
        Log.e("Activity",s);
    }

}