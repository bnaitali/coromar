package com.bnaitali.marocoro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    GridView gridView,gv;
    String di;
    int tb=1;
    private static String[] icons = new String[] {"الإصابات","  \n ","الوفيات","  \n \n \n  ","المتعافون","  \n  "};
    private static  Integer[] pics = {R.drawable.sick,R.drawable.trans,R.drawable.die,R.drawable.trsp,R.drawable.reco,R.drawable.trsp};
    private static List<String> jsondoc;
    int r;
    TextView tv;

    public  List<String> list = new ArrayList<>();
    public  List<String> notes = new ArrayList<>();
    public  List<String> trie = new ArrayList<>();
    List<String> aftertr = new ArrayList<>();
    int[]tr;

    //DatabaseAccess data2 ; //= DatabaseAccess.getInstance(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       new AsyncHttpTask().execute("www.google.com");
   //     Toast.makeText(getApplicationContext(), "r =  "+r, Toast.LENGTH_SHORT).show();

        gridView = (GridView) findViewById(R.id.gridview);
        gv = (GridView) findViewById(R.id.notice);
        gridView.setAdapter(new EfficientAdapter(getApplicationContext()));
        BottomNavigationView btm = findViewById(R.id.bottom_navigation);
        btm.setOnNavigationItemSelectedListener(navListener);
        tv= (TextView)findViewById(R.id.textView2);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_browse:
                            selectedFragment = new HomeFragment();
                            gridView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.nav_search:
                            gridView.setVisibility(View.INVISIBLE);
                            selectedFragment = new SearchFragment();

                            break;
                        case R.id.nav_trend:
                            if(r==0){
                                Toast.makeText(getApplicationContext(), "جهازك غير متصل بالانترنت", Toast.LENGTH_SHORT).show();
                            }
                            else //internet check
                            {
                                jsondoc.clear();
                                GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask(2);
                                getAllDevicesTask.execute();
                            }
                            gridView.setVisibility(View.INVISIBLE);
                            selectedFragment = new TrendFragment();

                            break;
                        case R.id.nav_list:
                            if(r==0){
                                Toast.makeText(getApplicationContext(), "جهازك غير متصل بالانترنت", Toast.LENGTH_SHORT).show();
                            }else {
                                jsondoc.clear();
                                tb = 3;
                                GetAllItemsAsyncTask getAllDevicesTask3 = new GetAllItemsAsyncTask(1);
                                getAllDevicesTask3.execute();
                            }
                            gridView.setVisibility(View.INVISIBLE);
                            selectedFragment = new ListFragment();

                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };


    private class GetAllItemsAsyncTask extends AsyncTask<Void, Void, List<String>> {
        int dbchoice;
        public  GetAllItemsAsyncTask (int dbchoice){
            this.dbchoice= dbchoice;
        }
       // private Callback callback;

       /* GetAllItemsAsyncTask(final Callback callback) {
            this.callback = callback;
        }*/

        @Override
        protected List<String> doInBackground(Void... params) {
            DatabaseAccess databaseAccess;
            DatabaseAccess2 databaseAccess2;
            //access first table coromar for mainactivity
           if(dbchoice==1){
               //Log.d(TAG, "tb =" + tb);
//               Toast.makeText(getApplicationContext(), "access Global tb = :" +tb, Toast.LENGTH_LONG).show();
               databaseAccess= DatabaseAccess.getInstance(MainActivity.this,tb);
               jsondoc=databaseAccess.getAllItems();

//               Log.d(TAG, "databases content" + databaseAccess.getAllItems().toString());
                  return databaseAccess.getAllItems();

            }else { //download table 2 content announcement for fragment 2
                  databaseAccess2 = DatabaseAccess2.getInstance(MainActivity.this);
                //Log.d(TAG, "databases content" + databaseAccess2.getAllItems().toString());
                    jsondoc=databaseAccess2.getAllItems();

                   return databaseAccess2.getAllItems();
            }


        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onPostExecute(List<String> documents) {
            ListDrwaer();
        }



        @RequiresApi(api = Build.VERSION_CODES.N)
        public void ListDrwaer() {


        if(dbchoice==1) {

            if(tb==3){//query table global(world)
               // Toast.makeText(getApplicationContext(), "full jsondoc" + jsondoc.get(0)+" - size ="+jsondoc.size(), Toast.LENGTH_LONG).show();
                String difix = "";
                di ="";
                list.clear();
                for (int i = 0; i < jsondoc.size(); i++) {
                    difix = jsondoc.get(i);
                    di = difix.replaceAll("[^0-9]", "");
                   // Toast.makeText(getApplicationContext(), "list " +di, Toast.LENGTH_LONG).show();
                    list.add(di);
                }

                FragmentManager manager = getSupportFragmentManager();
                final FragmentTransaction t = manager.beginTransaction();
                final ListFragment m4 = new ListFragment();
                Bundle b2 = new Bundle();
                b2.putSerializable("k", (Serializable) list);
                m4.setArguments(b2);
                t.replace(R.id.fragment_container,m4);
                t.commit();

            }else {
                //Log.d(TAG, "databases content" + jsondoc.get(0));
                //Toast.makeText(getApplicationContext(), "full jsondoc" + jsondoc.get(0), Toast.LENGTH_LONG).show();
                String difix = "";
                for (int i = 0; i < jsondoc.size(); i++) {
                    difix = jsondoc.get(i);
                    di = difix.replaceAll("[^0-9]", "");
                    // Toast.makeText(getApplicationContext(), "list " +di, Toast.LENGTH_LONG).show();
                    list.add(di);

                }
                String date = list.get(3);
                date = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4, date.length());
                //  Toast.makeText(getApplicationContext(), "date :" + date, Toast.LENGTH_LONG).show();

                icons[1] = "\n" + list.get(0);
                icons[3] = "\n" + list.get(1);
                icons[5] = "\n" + list.get(2);
                tv.setText("آخر تحديث : " + date);


                gridView.setAdapter(new EfficientAdapter(getApplicationContext()));
            }
//            gv.setAdapter(new EfficientAdapter(getApplicationContext()));

        } else if(dbchoice==2) {
           // Log.d(TAG, "databases content" + jsondoc.get(0));


            notes.clear();
            aftertr.clear();
            trie.clear();
            String difix = "";
           // String t="";
            int startIndex ;
            String link ;
           // Toast.makeText(getApplicationContext(), "number  " + link, Toast.LENGTH_LONG).show();

            for (int i = 0; i < jsondoc.size(); i++) {
                difix = jsondoc.get(i);
                startIndex = jsondoc.get(i).indexOf("id={N:");
                 link = jsondoc.get(i).substring(startIndex, startIndex+10);
                //Log.d(TAG, "number is  = " + link);
                link = link.replaceAll("[^0-9]", "");
                //Toast.makeText(getApplicationContext(), "number  " + link, Toast.LENGTH_SHORT).show();
                 trie.add(link);//we got the id of each entry, we must organise them
                  //  t=difix.substring(11,difix.length() - 14);//for order the table, dynamodb send data not in order

                di = difix.substring(11,difix.length()-3);
                di=di.replace(", notice={S","");
                di=di.replace("id={N: "+link,"");
              //  Log.d(TAG, "di is  = " +di);
               di=di.replace(",{,","");
                di=di.replace(",},","");
                di=di.replace(":","");
               // Toast.makeText(getApplicationContext(), "" + jsondoc.get(i).substring(11,jsondoc.get(i).length() - 14), Toast.LENGTH_LONG).show();
                notes.add(di);
            }


                tr=new int[0];
                tr = new int[trie.size()];
          //  Toast.makeText(getApplicationContext(), "" +tr.length, Toast.LENGTH_SHORT).show();
          //  Log.d(TAG, "tr size = " + tr.length);



            for(int i=0;i<trie.size();i++)
            {
                tr[i] = Integer.parseInt(trie.get(i));

                try {
                    tr[i] = Integer.parseInt(trie.get(i));
                }catch (NumberFormatException e){

                }


            }
            for(int i=0;i<tr.length;i++)
            {
                for(int j=0;j<tr.length;j++){

                    if(i==tr[j]){
                       // Toast.makeText(getApplicationContext(), "tr(j)= "+tr[j]+", i=  "+i+" ,j= "+j, Toast.LENGTH_LONG).show();
                        aftertr.add(notes.get(j));//after trie of notes by id
                    }
                }
            }


            //Toast.makeText(getApplicationContext(), ""+aftertr.get(1), Toast.LENGTH_LONG).show();

            FragmentManager manager = getSupportFragmentManager();
            final FragmentTransaction t = manager.beginTransaction();
            final TrendFragment m4 = new TrendFragment();
            Bundle b2 = new Bundle();
            b2.putSerializable("k", (Serializable) aftertr);
            m4.setArguments(b2);
            t.add(R.id.fragment_container,m4);
            t.commit();


}

        }



    }



    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {


            Integer result = 0;

            try {
                Socket s = new Socket(params[0],80);
                s.close();
                result = 1;
            } catch (Exception e) {
                result = 0;
            }


            return result;
        }


        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                r=result;
                GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask(1);
                getAllDevicesTask.execute();
               //Toast.makeText(getApplicationContext(), "connected !"+r, Toast.LENGTH_SHORT).show();
               // update();
            } else {
                r=result;
                Toast.makeText(getApplicationContext(), "جهازك غير متصل بالانترنت، المرجوا الربط بالشبكة ثم إعادة التشغيل", Toast.LENGTH_LONG).show();

            }
        }


    }



    public class EfficientAdapter extends BaseAdapter {

        private LayoutInflater mLayoutInflater;
        public EfficientAdapter(Context context){

            //icons[1]=list.get(0);
            mLayoutInflater=LayoutInflater.from(context);
        }



        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {

            ViewHolder mVHolder;
            if(converView == null){
                converView=mLayoutInflater.inflate(R.layout.customgrid, parent, false);
                mVHolder=new ViewHolder();
                mVHolder.mImageView=(ImageView)converView.findViewById(R.id.imgview);
                mVHolder.mTextView=(TextView)converView.findViewById(R.id.text);
                mVHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mVHolder.mImageView.setPadding(8,8,8,8);
                converView.setTag(mVHolder);
            }else{
                mVHolder=(ViewHolder)converView.getTag();
            }
                    mVHolder.mImageView.setImageResource(pics[position]);
                    mVHolder.mTextView.setText(icons[position]);

            return converView;
        }

    }


    static class ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public void updateicon() {

        }
    }




}