package cn.cslg.snowymoney.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.DBDao;

public class RecordAdapter extends CursorAdapter {
    private DBDao dbDao;
    private TextView recordText;

    public RecordAdapter(Context context, Cursor c,boolean autoRequery) {
        super(context, c, autoRequery);
        dbDao = new DBDao(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_record,null);
        recordText = view.findViewById(R.id.record_text);
        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        recordText.setText(cursor.getString(cursor.getColumnIndex("content")));
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if(constraint != null)
            return dbDao.getRecordByConstraint(constraint.toString());
        else
            return null;
    }

}
