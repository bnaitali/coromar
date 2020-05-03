package com.bnaitali.marocoro;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

public class TrendFragment extends Fragment {

    //Constructor for fragment to get data from activity
   public TrendFragment(){    }

    private static final String TAG = "TrendFragment";
 //   private static List<String> jsondoc;
    private static String[] icons2 =  {""};
   // private static  Integer[] pics2 = {R.drawable.sick,R.drawable.trans,R.drawable.die,R.drawable.trsp,R.drawable.reco,R.drawable.trsp};
    List<String> notices;




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        GridView gv = (GridView) getView().findViewById(R.id.notice);
        //gv.setAdapter(null);
        gv.setAdapter(new TrendFragment.EfficientAdapter(getContext()));
        final int total = gv.getCount();
        if(total!=0) {
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    for(int i=0;i<total;i++)
                    {
                        if(position==i){
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle(""+icons2[i]);
                            alertDialog.setMessage(""+notices.get(i));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                            // Toast.makeText(getContext(), ""+icons2[0]+"\n "+notices.get(0), Toast.LENGTH_LONG).show();
                        }
                    }


                  //  if(position==1){                    Toast.makeText(getContext(), ""+icons2[1]+"\n "+notices.get(1), Toast.LENGTH_LONG).show(); }
                    //if(position==2){                    Toast.makeText(getContext(), ""+icons2[2]+"\n "+notices.get(2), Toast.LENGTH_LONG).show(); }

                }
            });
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        int k = getArguments().getInt("kk", 0);
        Bundle b3 = getArguments();
        if(b3==null)
        {

           // Toast.makeText(getContext(), "null " , Toast.LENGTH_SHORT).show();
        }else{
            //Arrays.fill(icons2,null);

            notices = (List<String>) b3.getSerializable("k");
           // Toast.makeText(getContext(), "notices : "+ notices.get(0) , Toast.LENGTH_LONG).show();
            int ns=notices.size();
            icons2 = new String[ns];
            for(int i=1;i<ns+1;i++){//to avoid balagh 0
                icons2[i-1] = "بلاغ رقم "+i;
            }
        }



        return inflater.inflate(R.layout.fragment_trend, container, false);

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

                return icons2.length;

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

                TrendFragment.ViewHolder mVHolder;
                if(converView == null){
                    converView=mLayoutInflater.inflate(R.layout.customgrid, parent, false);
                    mVHolder=new TrendFragment.ViewHolder();
                    //   mVHolder.mImageView=(ImageView)converView.findViewById(R.id.imgview);
                    mVHolder.mTextView=(TextView)converView.findViewById(R.id.text);
                    //    mVHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //  mVHolder.mImageView.setPadding(8,8,8,8);
                    converView.setTag(mVHolder);
                }else{
                    mVHolder=(TrendFragment.ViewHolder)converView.getTag();
                }
                // mVHolder.mImageView.setImageResource(pics2[position]);
                mVHolder.mTextView.setText(icons2[position]);
                return converView;
            }
        }

        static class ViewHolder {
            // ImageView mImageView;
            TextView mTextView;

    }




}

