package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.climbachiya.retrofitdemo_2.R;

import java.util.List;

import modals.Contact;

/**
 * Created by C.limbachiya on 6/1/2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    List<Contact> result = null;
    Context mContext;

    public MyRecyclerAdapter(Context context, List<Contact> _result) {
        this.result = _result;
        this.mContext = context;
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, final int position) {
        Contact item = result.get(position);

        holder.textName.setText(item.getName());
        holder.textEmail.setText(item.getEmail());
        holder.textMobile.setText(item.getPhone().getMobile());

        /*Glide
            .with(mContext)
            .load(item.getOwner().getProfile_image())
            .centerCrop()
            .placeholder(android.R.drawable.ic_dialog_alert)
            .crossFade()
            .into(holder.textMobile);

        Log.v("Tags : position :: "+position, ""+item.getTags().size());*/

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textEmail;
        public TextView textMobile;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            textEmail = (TextView) itemView.findViewById(R.id.text_email);
            textMobile = (TextView) itemView.findViewById(R.id.text_mobile);

        }
    }

    /**
     * Edit record in database
     * @param position - row position in array list
     */
    /*private void editRow(int position) {
        String rowId = items.get(position).getId();
        Intent intentEdit = new Intent(mContext, MainActivity.class);
        intentEdit.putExtra("ROW_ID", rowId);
        mContext.startActivity(intentEdit);
        ((Activity)mContext).finish();
    }*/

    //Remove item
   /* public void removeItem(int position) {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance(mContext);
        try {
            String rowId = items.get(position).getId();

            String whereClause = DatabaseHandler.KEY_ID+" = ?";
            String[] whereArgs = new String[]{rowId};
            int result = dbHandler.deleteRecord(DatabaseHandler.TABLE_CONTACTS, whereClause, whereArgs);

            if(result > 0){
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                items.remove(position);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != dbHandler){
                dbHandler.close();
            }
        }

    }*/
}