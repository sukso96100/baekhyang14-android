package kr.hs.zion.baekhyang14;

import java.util.ArrayList;

/**
 * Created by youngbin on 14. 10. 26.
 */
public class ExpandableListData  {
    String Parent;
    ArrayList<String> Child;

    public ExpandableListData(String parent, ArrayList<String> child) {
        Parent = parent;
        Child = child;
    }
}