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

import com.github.watanabear.learn_rxjava2_android.MyApplication;
import com.github.watanabear.learn_rxjava2_android.R;
import com.github.watanabear.learn_rxjava2_android.databinding.ActivityMainBinding;
import com.github.watanabear.learn_rxjava2_android.databinding.ListItemClassBinding;
import com.github.watanabear.learn_rxjava2_android.presentation.pagination.PaginationActivity;
import com.github.watanabear.learn_rxjava2_android.presentation.rxbus.RxBusActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new ActivityAdapter(this, Arrays.asList(ActItem.values()),
                new OnRecyclerListener() {
                    @Override
                    public void onItemClick(ActItem item) {
                        if (item.aClass.equals(RxBusActivity.class)) {
                            ((MyApplication) getApplication()).sendAutoEvent();
                        }
                        startActivity(new Intent(MainActivity.this, item.aClass));
                    }
                }));
    }

    public enum ActItem {

        MAIN(R.string.main_title, R.string.main_desc, MainActivity.class),
        RX_BUS(R.string.rxbus_title, R.string.rxbus_desc, RxBusActivity.class),
        PAGINATION(R.string.pagination_title, R.string.pagination_desc, PaginationActivity.class),
        OPERATOR(R.string.operator_title, R.string.operator_desc, OperatorsActivity.class),
        ;

        public final int title;
        public final int description;
        public final Class<? extends Activity> aClass;

        ActItem(int title, int description, Class<? extends Activity> aClass) {
            this.title = title;
            this.description = description;
            this.aClass = aClass;
        }
    }

    interface OnRecyclerListener {
        void onItemClick(ActItem item);
    }

    class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

        private LayoutInflater inflater;
        private List<ActItem> items;
        private OnRecyclerListener listener;

        ActivityAdapter(Context context, List<ActItem> items, OnRecyclerListener listener) {
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
            final ActItem a = items.get(position);
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
