package com.github.watanabear.learn_rxjava2_android.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.watanabear.learn_rxjava2_android.R;
import com.github.watanabear.learn_rxjava2_android.databinding.ActivityMainBinding;
import com.github.watanabear.learn_rxjava2_android.databinding.ListItemClassBinding;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.DisposableActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.IntervalActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.MapActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.SimpleActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.TakeActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.TimerActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.operators.ZipActivity;

import java.util.Arrays;
import java.util.List;

public class OperatorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new ActivityAdapter(this, Arrays.asList(OperatorItem.values()),
                new OnRecyclerListener() {
                    @Override
                    public void onItemClick(OperatorItem item) {
                        startActivity(new Intent(OperatorsActivity.this, item.aClass));
                    }
                }));
    }

    interface OnRecyclerListener {
        void onItemClick(OperatorItem item);
    }

    public enum OperatorItem {

        SIMPLE(R.string.simple, R.string.simple_d, SimpleActivity.class),
        MAP(R.string.map, R.string.map_d, MapActivity.class),
        ZIP(R.string.zip, R.string.zip_d, ZipActivity.class),
        DISPOSABLE(R.string.disposable, R.string.disposable, DisposableActivity.class),
        TAKE(R.string.take, R.string.take_d, TakeActivity.class),
        TIMER(R.string.timer, R.string.timer, TimerActivity.class),
        INTERVAL(R.string.interval, R.string.interval, IntervalActivity.class),
        SINGLE_OBSERVER(R.string.SingleObserver, R.string.SingleObserver, IntervalActivity.class),

        ;

        public final int title;
        public final int description;
        public final Class<? extends Activity> aClass;

        OperatorItem(int title, int desc, Class<? extends Activity> aClass) {
            this.title = title;
            this.aClass = aClass;
            this.description = desc;
        }
    }

    class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

        private LayoutInflater inflater;
        private List<OperatorItem> items;
        private OnRecyclerListener listener;

        ActivityAdapter(Context context, List<OperatorItem> items, OnRecyclerListener listener) {
            this.inflater = LayoutInflater.from(context);
            this.items = items;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(ListItemClassBinding.inflate(inflater, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final OperatorItem a = items.get(position);
            holder.binding.textTitle.setText(a.title);
            holder.binding.textDescription.setText(a.description);
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(a);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ListItemClassBinding binding;

            ViewHolder(ListItemClassBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
