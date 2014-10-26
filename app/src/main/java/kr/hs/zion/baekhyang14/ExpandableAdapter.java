package kr.hs.zion.baekhyang14;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by youngbin on 14. 10. 26.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<ExpandableListData> mExpandableListData;

    public ExpandableAdapter(Context context, ArrayList<ExpandableListData> Data) {
        mContext = context;
        mExpandableListData = Data;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return mExpandableListData.get(groupPosition).Child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    //ChildView에 데이터 뿌리기
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = getChildGenericView();
        } else {
            view = convertView;
        }

        TextView text = (TextView)view.findViewById(android.R.id.text1);
        text.setText(mExpandableListData.get(groupPosition).Child.get(childPosition));
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mExpandableListData.get(groupPosition).Child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mExpandableListData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mExpandableListData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //GroupView에 데이터 뿌리
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View view;
        if(convertView == null) {
            view = getParentGenericView();
        } else {
            view = convertView;
        }


        TextView text = (TextView)view.findViewById(R.id.text);
        text.setText(mExpandableListData.get(groupPosition).Parent);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

    //Child의 View의 XML을 생성
    public View getChildGenericView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
        return view;
    }

    //Parent(Group)의 View의 XML을 생성
    public View getParentGenericView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.simple_expandable_list_item_1, null);
        return view;
    }
}
