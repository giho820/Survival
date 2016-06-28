package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.CheckListAllRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

public class CheckListAct extends ParentAct implements View.OnClickListener {

    LinearLayout linearLayoutCheckListBack;
    LinearLayout linearLayoutCheckListNew;
    LinearLayout linearLayoutCheckListAll;
    LinearLayout linearLayoutCheckListImport;
    LinearLayout linearLayoutCheckListAdd;

    private RecyclerView checkListRecyclerView;
    private CheckListAllRecyclerAdapter categoryAllRecyclerAdapter;

    private ArrayList<CheckList> checkLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        linearLayoutCheckListBack = (LinearLayout) findViewById(R.id.linearlayout_check_list_back);
        linearLayoutCheckListNew = (LinearLayout) findViewById(R.id.linearlayout_checklist_new);
        linearLayoutCheckListAll = (LinearLayout) findViewById(R.id.linearlayout_check_list_all);
        linearLayoutCheckListImport = (LinearLayout) findViewById(R.id.linearlayout_check_list_import);
        linearLayoutCheckListAdd = (LinearLayout) findViewById(R.id.linearlayout_check_list_add);

        linearLayoutCheckListBack.setOnClickListener(this);
        linearLayoutCheckListNew.setOnClickListener(this);
        linearLayoutCheckListAll.setOnClickListener(this);
        linearLayoutCheckListImport.setOnClickListener(this);
        linearLayoutCheckListAdd.setOnClickListener(this);


        checkListRecyclerView = (RecyclerView) findViewById(R.id.checklist_all_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        checkListRecyclerView.setLayoutManager(linearLayoutManager);
        categoryAllRecyclerAdapter = new CheckListAllRecyclerAdapter(this);
        categoryAllRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, int position) {
                DebugUtil.showDebug("체크리스트랑 지우기가 눌려야해오 :: " + checkLists.get(position).title);

            }
        });
        checkListRecyclerView.setAdapter(categoryAllRecyclerAdapter);

        checkLists = new ArrayList<>();
        //Todo 1. 디비 복사할 것(어플 실행 후 최초 1회만 이루어져야함)
        //insert 쿼리를 리턴하는 함수를 만들고나서 동적으로 생성한 checkedlist db에 insert 쿼리를 날린다
        if (!DatabaseCRUD.doesCheckedListTableExist()){
            DebugUtil.showDebug("User CheckedList Table is exist");
            String insertQuery = DatabaseCRUD.getInsertQueryFromCheckListTable();
            DebugUtil.showDebug("insertQuery :: " + insertQuery);
            DatabaseCRUD.execRawQuery(insertQuery);
        }

//        checkLists = DatabaseCRUD.getOriginCheckListFromDb();
        checkLists = DatabaseCRUD.getUserCheckedListFromDb();

        if (checkLists != null) {
            categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
            categoryAllRecyclerAdapter.notifyDataSetChanged();
            DebugUtil.showDebug("categoryAllRecyclerAdapter.setAdapterArrayList() 진행 함 :: " + categoryAllRecyclerAdapter.getItemCount());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linearlayout_check_list_back:
                onBackPressed();
                break;

            case R.id.linearlayout_checklist_new:
                //Todo 디비에서도 isInMyList값 없애기
                checkLists.clear();
                categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                categoryAllRecyclerAdapter.notifyDataSetChanged();
                break;

            case R.id.linearlayout_check_list_all:
                break;

            case R.id.linearlayout_check_list_import:
                DebugUtil.showDebug(DatabaseCRUD.selectCheckedListDBQuery());
                //Todo 2. 복사한 디비를 새로운 화면에 체크박스와 함께 띄워준다

                break;

            case R.id.linearlayout_check_list_add:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Todo 3. import 액티비티가 종료되었을 때 all activity 화면 refresh를 통해서 디비 내용 다시 뿌려주기
    }
}
