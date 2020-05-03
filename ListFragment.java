package com.bnaitali.marocoro;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ListFragment extends Fragment {


    List<String> list;
    private static String[] icons = new String[] {"الإصابات","  \n ","الوفيات","  \n \n \n  "};
    private static  Integer[] pics = {R.drawable.sick,R.drawable.trans,R.drawable.die,R.drawable.trsp};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,
                             @Nullable Bundle savedInstanceState) {

        Bundle b3 = getArguments();
        if(b3==null)
        {

           // Toast.makeText(getContext(), "null " , Toast.LENGTH_SHORT).show();
        }else{
            //Arrays.fill(icons2,null);

            list = (List<String>) b3.getSerializable("k");
           // Toast.makeText(getContext(), "list : "+ list.get(0) , Toast.LENGTH_LONG).show();
            int ns=list.size();
            //icons = new String[ns];
            icons[1] = list.get(0);
            icons[3] = list.get(1);

        }
        return inflater.inflate(R.layout.fragment_list, viewGroup, false);
    }



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        GridView gv = (GridView) getView().findViewById(R.id.globe);


        //gv.setAdapter(null);
        gv.setAdapter(new ListFragment.EfficientAdapter(getContext()));

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

            MainActivity.ViewHolder mVHolder;
            if(converView == null){
                converView=mLayoutInflater.inflate(R.layout.customgrid, parent, false);
                mVHolder=new MainActivity.ViewHolder();
                mVHolder.mImageView=(ImageView)converView.findViewById(R.id.imgview);
                mVHolder.mTextView=(TextView)converView.findViewById(R.id.text);
                mVHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mVHolder.mImageView.setPadding(8,8,8,8);
                converView.setTag(mVHolder);
            }else{
                mVHolder=(MainActivity.ViewHolder)converView.getTag();
            }
            mVHolder.mImageView.setImageResource(pics[position]);
            mVHolder.mTextView.setText(icons[position]);

            return converView;
        }

    }


    static class ViewHolder{
        ImageView mImageView;
        TextView mTextView;

    }
}
