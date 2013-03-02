package cse.bgu.finalandroidproject.theamazingrace;


import android.content.Context;
import android.database.Cursor;
import android.net.NetworkInfo.DetailedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_layout, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView textViewGameName = (TextView) view.findViewById(R.id.text_game_name);
        textViewGameName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))).replace("_", " "));

        TextView textViewArea = (TextView) view.findViewById(R.id.text_area);
        textViewArea.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        
        TextView textViewCreator = (TextView) view.findViewById(R.id.text_creator);
        textViewCreator.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
    }
}
