package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ParentAct;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.CheckListAllRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

public class CheckListAct extends ParentAct implements View.OnClickListener {

    private static final int IMPORT_ACT = 0;
    private static final int ADD_ACT = 1;

    LinearLayout linearLayoutCheckListBack;
    LinearLayout linearLayoutCheckListNew;
    LinearLayout linearLayoutCheckListAll;
    LinearLayout linearLayoutCheckListImport;
    LinearLayout linearLayoutCheckListAdd;

    private RecyclerView checkListRecyclerView;
    private CheckListAllRecyclerAdapter categoryAllRecyclerAdapter;
    private ArrayList<CheckList> checkLists;

    private boolean isAll = true;

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
                switch (view.getId()) {
                    case R.id.item_checklist_all_checkbox_click_area:

                        if (checkLists != null) {
                            if (checkLists.get(position).isChecked == Definitions.CHECK_BOX_CHECKED.CHECKED) {
                                checkLists.get(position).isChecked = Definitions.CHECK_BOX_CHECKED.UNCHECKED;

                                String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                        DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED +
                                        " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                                DebugUtil.showDebug("updateQuery :: " + updateQuery);
                                DatabaseCRUD.execRawQuery(updateQuery);
                            } else {
                                checkLists.get(position).isChecked = Definitions.CHECK_BOX_CHECKED.CHECKED;

                                String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                        DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.CHECKED +
                                        " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                                DebugUtil.showDebug("updateQuery :: " + updateQuery);
                                DatabaseCRUD.execRawQuery(updateQuery);
                            }
                        }
                        break;

                    case R.id.linearlayout_checklist_all_trash:
                        if (checkLists.get(position).isInMyList == Definitions.CHECK_BOX_IMPORTED.IMPORTED) {
                            checkLists.get(position).isInMyList = Definitions.CHECK_BOX_IMPORTED.UNIMPORTED;

                            String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                    DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.UNIMPORTED +
                                    " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                            DebugUtil.showDebug("updateQuery :: " + updateQuery);
                            DatabaseCRUD.execRawQuery(updateQuery);
                        } else {
                            checkLists.get(position).isInMyList = Definitions.CHECK_BOX_IMPORTED.IMPORTED;

                            String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                    DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED +
                                    " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                            DebugUtil.showDebug("updateQuery :: " + updateQuery);
                            DatabaseCRUD.execRawQuery(updateQuery);
                        }

                        //체크 내역 지우기
                        String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED +
                                " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                        DebugUtil.showDebug("insertQuery :: " + updateQuery);
                        DatabaseCRUD.execRawQuery(updateQuery);

                        break;
                }
                checkLists = DatabaseCRUD.getUserCheckedListFromDb(); //Todo 디비 refresh하는 부분
                if (checkLists != null) {
                    categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                    categoryAllRecyclerAdapter.notifyDataSetChanged();
                    DebugUtil.showDebug("categoryAllRecyclerAdapter.setAdapterArrayList() 진행 함 :: " + categoryAllRecyclerAdapter.getItemCount());
                }
            }
        });

        checkListRecyclerView.setAdapter(categoryAllRecyclerAdapter);

        checkLists = new ArrayList<>();
        //Todo 1. 디비 복사할 것(어플 실행 후 최초 1회만 이루어져야함)
        //insert 쿼리를 리턴하는 함수를 만들고나서 동적으로 생성한 checkedlist db에 insert 쿼리를 날린다
        if (!DatabaseCRUD.doesCheckedListTableExist()) {
            DebugUtil.showDebug("User CheckedList Table is not exist");
            String insertQuery = DatabaseCRUD.getInsertQueryFromCheckListTable();
            DebugUtil.showDebug("insertQuery :: " + insertQuery);
            DatabaseCRUD.execRawQuery(insertQuery);
        }
        checkLists = DatabaseCRUD.getUserCheckedListFromDb(); //Todo 디비 refresh하는 부분
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

//                DebugUtil.showDebug(DatabaseCRUD.selectCheckedListDBQuery(DatabaseConstantUtil.TABLE_USER_CHECKED_LIST));
//                DebugUtil.showDebug(DatabaseCRUD.selectCheckedListDBQuery("CHECK_LIST"));

                //Todo 디비에서도 isInMyList값 없애기
                String makeInitQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST +
                        " set " + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED
                        + ", " + DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.UNIMPORTED;
                DebugUtil.showDebug(makeInitQuery);

                String deleteUserMadeCLQuery = "delete from " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST +
                        " where " + DatabaseConstantUtil.COLUMN_CATEGORY_ID_CHECKED_LIST + " = 8";
                DatabaseCRUD.execRawQuery(deleteUserMadeCLQuery);

                checkLists.clear();
                categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                categoryAllRecyclerAdapter.notifyDataSetChanged();
                break;

            case R.id.linearlayout_check_list_all:

                DebugUtil.showDebug(" DatabaseCRUD.getUserCheckedListFromDb().size() :: " + DatabaseCRUD.getUserCheckedListFromDb().size());
                DebugUtil.showDebug(" DatabaseCRUD.getUserCheckedAndImportListFromDb().size() :: " + DatabaseCRUD.getUserCheckedAndImportListFromDb().size());
                //Todo 컬럼에서 import된 개수와 체크된 개수를 비교해서 같으면
                //import 된 수 == import 된 수  && 체크되고 import된 수가 0이 아님
                if (DatabaseCRUD.getUserCheckedListFromDb().size() == DatabaseCRUD.getUserCheckedAndImportListFromDb().size() &&
                        DatabaseCRUD.getUserCheckedAndImportListFromDb().size() != 0) {
                    isAll = true;//import된 모든 항목에 대해서 체크는 안되도록 하고, import 되도록 조치한다
                } else if (DatabaseCRUD.getUserCheckedListFromDb().size() > DatabaseCRUD.getUserCheckedAndImportListFromDb().size() ||
                        DatabaseCRUD.getUserCheckedAndImportListFromDb().size() == 0) {//0이거나 import된 것이 체크된 컬럼의 개수보다 많으면
                    isAll = false;
                } else {
                    isAll = !isAll;
                }

                DebugUtil.showDebug("isAll ::" + isAll);
                if (isAll) {
                    String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                            DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED
                            + " where " + DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED;
                    DebugUtil.showDebug("updateQuery :: " + updateQuery);
                    DatabaseCRUD.execRawQuery(updateQuery);
                } else {
                    String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                            DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.CHECKED
                            + " where " + DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED;
                    DebugUtil.showDebug("updateQuery :: " + updateQuery);
                    DatabaseCRUD.execRawQuery(updateQuery);
                }
                checkLists = DatabaseCRUD.getUserCheckedListFromDb(); //Todo 디비 refresh하는 부분
                categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                categoryAllRecyclerAdapter.notifyDataSetChanged();

                break;

            case R.id.linearlayout_check_list_import:
                //Todo 2. 복사한 디비를 새로운 화면에 체크박스와 함께 띄워준다

                Intent moveToImportCheckListActIntent = new Intent(this, ImportToCheckListAct.class);
                startActivityForResult(moveToImportCheckListActIntent, IMPORT_ACT);

                break;

            case R.id.linearlayout_check_list_add:
                Intent moveToAddCheckListActIntent = new Intent(this, AddCheckListAct.class);
                startActivityForResult(moveToAddCheckListActIntent, ADD_ACT);
//                MoveActUtil.moveActivity(this, moveToAddCheckListActIntent, -1, -1, false, false);


                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Todo 3. import 액티비티가 종료되었을 때 all activity 화면 refresh를 통해서 디비 내용 다시 뿌려주기
        if (resultCode == RESULT_OK) {
            if (requestCode == IMPORT_ACT) {
                int test = data.getExtras().getInt("sizeOfCheckList");
                DebugUtil.showDebug("onActivityResult :: 호출됨 " + test);

                if (checkLists != null) {
                    checkLists = DatabaseCRUD.getUserCheckedListFromDb(); //Todo 디비 refresh하는 부분
                    categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                    categoryAllRecyclerAdapter.notifyDataSetChanged();
                    DebugUtil.showDebug("categoryAllRecyclerAdapter.setAdapterArrayList() 진행 함 :: " + categoryAllRecyclerAdapter.getItemCount());
                }

            }
            if (requestCode == ADD_ACT) {
                String input = data.getExtras().getString("userInputString");

                if (!TextUtil.isNull(input)) {
                    String insertQuery = DatabaseCRUD.getInsertNewCheckListToUserCheckListTable(input);
                    DebugUtil.showDebug("insertQuery  ::" + insertQuery);
                    DatabaseCRUD.execRawQuery(insertQuery);

                    checkLists = DatabaseCRUD.getUserCheckedListFromDb(); //Todo 디비 refresh하는 부분
                    categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                    categoryAllRecyclerAdapter.notifyDataSetChanged();
                }
            }
        }


    }
}
