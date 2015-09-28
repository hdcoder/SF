package hiteshdua1.codescripter.sf.NavigationDrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import hiteshdua1.codescripter.sf.R;

public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {

    Context mcontext;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;

    public DrawerItemCustomAdapter(Context context, int layoutResourceId, ObjectDrawerItem[] data) {
        super(context, layoutResourceId, data);
        this.mcontext = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mcontext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageView = (ImageView) listItem.findViewById(R.id.drawer_icon);
        TextView textView = (TextView) listItem.findViewById(R.id.drawer_itemName);

        ObjectDrawerItem folder = data[position];

        imageView.setImageResource(folder.icon);
        textView.setText(folder.name);

        return listItem;
    }
}
