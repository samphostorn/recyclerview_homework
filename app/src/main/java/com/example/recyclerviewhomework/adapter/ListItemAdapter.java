
package com.example.recyclerviewhomework.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recyclerviewhomework.EditItemActivity;
import com.example.recyclerviewhomework.MainActivity;
import com.example.recyclerviewhomework.R;
import com.example.recyclerviewhomework.model.ListItem;

import java.util.List;

public class ListItemAdapter  extends RecyclerView.Adapter<ListItemAdapter.ViewHolder>{


    private List<ListItem> listItems;
    private AppCompatActivity context;
    private OnAdapterItemLongClickListener listener;

public ListItemAdapter(List<ListItem> listItems,AppCompatActivity context){
     this.listItems=listItems;
     this.context=context;
    this.listener=(OnAdapterItemLongClickListener) context;
}
    @Override
    public int getItemCount() {
        return this.listItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View view= LayoutInflater.from(context).inflate(R.layout.layout_listitem_item,viewGroup,false);

        return new ListItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        context.registerForContextMenu(viewHolder.itemView);
        final ListItem listItem=listItems.get(position);
        viewHolder.description.setText(listItem.getDescription());
        viewHolder.title.setText(listItem.getTitle());
        viewHolder.viewer.setText(listItem.getViewer());
        viewHolder.btnMoreOption.setImageResource(listItem.getMoreIcon());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, EditItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("data",listItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        viewHolder.btnMoreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context,viewHolder.btnMoreOption);

                popup.inflate(R.menu.popup_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.edit:
                                Intent intent=new Intent(context, EditItemActivity.class);
                                ListItem listItem=listItems.get(viewHolder.getAdapterPosition());
                                Bundle bundle=new Bundle();
                                bundle.putParcelable("data",listItem);
                                intent.putExtras(bundle);
                                context.startActivityForResult(intent,100);
                                return  true;

                            case R.id.remove:

                                listItems.remove(viewHolder.getAdapterPosition());
                                notifyItemRemoved(viewHolder.getAdapterPosition());
                                return true;

                        }

                        return false;
                    }
                });

                popup.show();

            }
        });


}
/*
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener!=null)
                    listener.onAdapterItemLongClicked(viewHolder.getAdapterPosition());
                return false;
            }
        });
*/



    static  class  ViewHolder extends RecyclerView.ViewHolder{


       private  TextView description,title,viewer;
       private ImageView btnMoreOption;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            description=itemView.findViewById(R.id.description);
            title=itemView.findViewById(R.id.title);
            viewer=itemView.findViewById(R.id.viewer);
            btnMoreOption=itemView.findViewById(R.id.btnOption);

        }
    }

    public interface OnAdapterItemLongClickListener{

        void onAdapterItemLongClicked(int position);
    }

    public void setListener(OnAdapterItemLongClickListener listener)
    {
        this.listener=listener;
    }


}
