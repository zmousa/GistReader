package com.zenus.gistreader.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenus.gistreader.R;
import com.zenus.gistreader.dao.Gist;

import java.util.List;

public class GistListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Gist> data;
    private static LayoutInflater inflater=null;

    public GistListAdapter(Activity a, List<Gist> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.gist_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.gist_title);
        TextView id = (TextView)vi.findViewById(R.id.gist_id);
        TextView files = (TextView)vi.findViewById(R.id.gist_files);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.gist_list_image);

        Gist gist = data.get(position);
        title.setText(gist.getDescription());
        id.setText(gist.getId());
        files.setText(gist.getFiles() != null ? "" + gist.getFiles().size() : "0");
        thumb_image.setImageResource(R.drawable.gist_icon);
        return vi;
    }
}