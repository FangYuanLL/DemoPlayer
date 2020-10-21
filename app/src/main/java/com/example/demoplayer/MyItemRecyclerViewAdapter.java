package com.example.demoplayer;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoplayer.Utils.ApplicationContext;
import com.example.demoplayer.Utils.ImageUtils;
import com.example.demoplayer.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static String TAG = "MainActivity";
    private final List<DummyItem> mValues;
    private static Context mContext = ApplicationContext.getContext();
    private int GridPosition = 0;
    public MyItemRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GridPosition = position;
        holder.mItem = mValues.get(position);
        holder.mIdView.setImageBitmap(ImageUtils.firstmap.get(position));
        holder.mContentView.setText(mValues.get(position).content);
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        holder.mIdView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Build.VERSION.SDK_INT > 21){
                        ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
                    }
                    if (hasFocus){
                        int count = 0;
                        //ImageUtils.map.get(GridPosition).get(count)
                        holder.mIdView.setImageBitmap(ImageUtils.firstmap.get(GridPosition));
                        count++;
                        if (count>=3){
                            count = 0;
                        }
                    }
                }else{
                    holder.mIdView.setImageBitmap(ImageUtils.firstmap.get(GridPosition));
                    ViewCompat.animate(v).scaleX(1.0f).scaleY(1.0f).start();
                }
            }
        });

        holder.mIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick start");
                VideoViewFragment newFragment = VideoViewFragment.newInstance();
                SwitchFragment(newFragment);
                Log.d(TAG,"onClick over");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (ImageView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void SwitchFragment(Fragment targetFragment){
        FragmentTransaction transaction = ApplicationContext.getManager().beginTransaction();
        transaction.replace(R.id.fragment_item_list,targetFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

class TimeTask extends Thread{

    public TimeTask(MyItemRecyclerViewAdapter.ViewHolder holder){

    }

    @Override
    public void run() {

    }
}