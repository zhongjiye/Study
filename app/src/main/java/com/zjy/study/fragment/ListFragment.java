package com.zjy.study.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.zjy.study.R;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {


    @BindView(R.id.lv_listview)
    ListView lvListview;

    public ListFragment() {
    }


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        String[] mDatas = {"斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我",
                "室带肥厚", "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复",
                "斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚",
                "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复",
                "斯柯达福克斯的积分", "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚",
                "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复", "斯柯达福克斯的积分",
                "s接收到回复可接受的", "欧洲剑偶家我", "室带肥厚", "撒地方快乐环岛是", "史蒂芬凯勒", "圣诞节回复", "收到回复"};
        QuickAdapter<String> adapter = new QuickAdapter<String>(getActivity(), R.layout.item_fragment_list, Arrays.asList(mDatas)) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.id, new Random().nextInt() + "");
                helper.setText(R.id.content, item);
            }
        };
        lvListview.setAdapter(adapter);
        return view;
    }

}
