package cn.cslg.snowymoney.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.DBDao;

public class AccountAdapter extends CursorAdapter {
    private DBDao dbDao;

    public AccountAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        dbDao = new DBDao(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_account,null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.accountText = view.findViewById(R.id.account_text);
        viewHolder.accountDelete = view.findViewById(R.id.account_del);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.accountText.setText(cursor.getString(0));
        holder.accountDelete.setImageResource(R.drawable.record_delete);
        holder.accountDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int recordId = cursor.getInt(0);
                dbDao.deleteRecord(recordId);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(0);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if(constraint != null) {
            return dbDao.getAccountByConstraint(constraint.toString());
        }
        else
            return null;
    }

    class ViewHolder{
        TextView accountText;
        ImageView accountDelete;
    }
}
