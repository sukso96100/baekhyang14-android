package kr.hs.zion.baekhyang14;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by youngbin on 14. 10. 22.
 */
public class DrawerListAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> title;
    ArrayList<Drawable> icon;

    public DrawerListAdapter(Activity context, ArrayList<String> title,
                           ArrayList<Drawable> icon) {
        super();
        this.context = context;
        this.title = title;
        this.icon = icon;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return title.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    private class ViewHolder {
        TextView txtViewTitle;
        ImageView imgViewIcon;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_drawer, null);
            holder = new ViewHolder();
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imgViewIcon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewTitle.setText(title.get(position));
        holder.imgViewIcon.setImageDrawable(icon.get(position));
        return convertView;
    }

}