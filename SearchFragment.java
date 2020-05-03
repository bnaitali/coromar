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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {



    private static String[] icons = new String[] {"سنة رسول الله صلى الله عليه وسلم 1","سنة رسول الله صلى الله عليه وسلم 2","اسباب مادية","  \n  "};
    List<String> notices =  new ArrayList<>();


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        GridView gv = (GridView) getView().findViewById(R.id.advise);


        //gv.setAdapter(null);
        gv.setAdapter(new SearchFragment.EfficientAdapter(getContext()));
        final int total = gv.getCount();
        if(total!=0) {
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    for(int i=0;i<total;i++)
                    {
                        if(position==i){
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle(""+icons[i]);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        notices.add("* عن عُقْبَةَ بْنِ عَامِرٍ : قُلْتُ : يَارَسُولَ اللهِ ، مَا النَّجَاةُ ؟ قَالَ : امْلِكْ عَلَيْكَ لِسَانَكَ ، وَلْيَسَعْكَ بَيْتُكَ ، وَابْكِ عَلَى خَطِيئَتِكَ.");

        notices.add("قراءة الفاتحة و المعوذتين و أذكار الصباح و المساء دائما. بكورونا او بدونه");

        notices.add("غسل البدبن جيدا حتى مع الوضوء،تجنب الزحام  و الحفاظ على مسافة مترين على الاقل بين الافراد و ليسعك بيتك، يعني الزم بيتك و تجنب الشائعات فقد تكون مميتة و أنت لا تدري");
        /*
        * عن عُقْبَةَ بْنِ عَامِرٍ : قُلْتُ : يَارَسُولَ اللهِ ، مَا النَّجَاةُ ؟ قَالَ : امْلِكْ عَلَيْكَ لِسَانَكَ ، وَلْيَسَعْكَ بَيْتُكَ ، وَابْكِ عَلَى خَطِيئَتِكَ.
        * */


        return inflater.inflate(R.layout.fragment_search, container, false);
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

            return icons.length;

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
            mVHolder.mTextView.setText(icons[position]);
            return converView;
        }
    }

    static class ViewHolder {
        // ImageView mImageView;
        TextView mTextView;

    }



}
