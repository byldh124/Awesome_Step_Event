package com.moondroid.awesome_step_event.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.common.OnViewHolderItemClickListener;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    String[] questions;
    RecyclerView recyclerView;

    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questions = getResources().getStringArray(R.array.questions);
        recyclerView = findViewById(R.id.question_recycler);

        adapter = new RecyclerAdapter(this, questions);
        recyclerView.setAdapter(adapter);
    }

    public void clickQuestion(View view) {
    }

    public void clickClose(View view) {
        finish();
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderQuestion> {

        private Context context;
        private String[] questions;
        private String[] answers;

        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        private int prePosition = -1;

        public RecyclerAdapter(Context context, String[] questions) {
            this.context = context;
            this.questions = questions;
            answers = context.getResources().getStringArray(R.array.answers);
        }

        @NonNull
        @Override
        public ViewHolderQuestion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderQuestion(LayoutInflater.from(context).inflate(R.layout.question_recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderQuestion holder, int position) {
            holder.onBind(questions[position],answers[position], position, selectedItems);
            holder.setOnViewHolderItemClickListener(new OnViewHolderItemClickListener() {
                @Override
                public void onViewHolderItemClick() {
                    if (selectedItems.get(position)) {
                        selectedItems.delete(position);
                    } else {
                        selectedItems.delete(prePosition);
                        selectedItems.put(position, true);
                    }
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    prePosition = position;
                }
            });
        }

        @Override
        public int getItemCount() {
            return questions.length;
        }

        class ViewHolderQuestion extends RecyclerView.ViewHolder {

            TextView tvQuestion;
            TextView tvQuestionDetail;
            LinearLayout linearLayout;
            ImageView ivDownImage;
            OnViewHolderItemClickListener onViewHolderItemClickListener;

            public ViewHolderQuestion(@NonNull View itemView) {
                super(itemView);
                tvQuestion = itemView.findViewById(R.id.tv_question_title);
                tvQuestionDetail = itemView.findViewById(R.id.tv_question_detail);
                linearLayout = itemView.findViewById(R.id.question_container);
                ivDownImage = itemView.findViewById(R.id.question_recycler_down_image);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onViewHolderItemClickListener.onViewHolderItemClick();
                    }
                });
            }

            public void onBind(String question, String answer, int position, SparseBooleanArray selectedItems) {
                tvQuestion.setText(question);
                tvQuestionDetail.setText(answer);
                changeVisibility(selectedItems.get(position));
            }

            public void changeVisibility(final boolean isExpanded) {
                ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
                va.setDuration(500);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
//                        tvQuestionDetail.getLayoutParams().height = (int) animation.getAnimatedValue();
                        animation.setIntValues(tvQuestionDetail.getLayoutParams().height);
                        tvQuestionDetail.requestLayout();
                        tvQuestionDetail.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    }
                });

                va.start();
            }

            public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
                this.onViewHolderItemClickListener = onViewHolderItemClickListener;
            }
        }
    }
}