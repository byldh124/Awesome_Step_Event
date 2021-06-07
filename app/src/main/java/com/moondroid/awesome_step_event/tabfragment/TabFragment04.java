package com.moondroid.awesome_step_event.tabfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.valueobject.StepRankingVO;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabFragment04 extends Fragment {

    TextView tvRating;
    RecyclerView ratingRecycler;

    ArrayList<StepRankingVO> rankingList;
    RatingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_tab_04, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvRating = view.findViewById(R.id.tab4_my_ranking);
        ratingRecycler = view.findViewById(R.id.tab4_ranking_recycler);
        rankingList = new ArrayList<>();
        setRankingListDummy();
        adapter = new RatingAdapter(getContext(), rankingList);
        ratingRecycler.setAdapter(adapter);
    }

    public void setRankingListDummy(){
        rankingList.add(new StepRankingVO("1", "김네이버스", R.drawable.profile01, "50,254"));
        rankingList.add(new StepRankingVO("2", "열심히 걷자", R.drawable.profile02, "50,200"));
        rankingList.add(new StepRankingVO("3", "기부할래요", R.drawable.profile03, "40,922"));
        rankingList.add(new StepRankingVO("4", "김기부", R.drawable.profile04, "40,910"));
        rankingList.add(new StepRankingVO("5", "김걸음", R.drawable.profile05, "39,541"));
        rankingList.add(new StepRankingVO("6", "워킹맨", R.drawable.profile06, "39,240"));
        rankingList.add(new StepRankingVO("7", "워킹우먼", R.drawable.profile07, "39,010"));
        rankingList.add(new StepRankingVO("8", "워킹궐", R.drawable.profile08, "38,225"));
        rankingList.add(new StepRankingVO("9", "박슬아", R.drawable.profile09, "38,195"));
        rankingList.add(new StepRankingVO("10", "영차영차", R.drawable.profile10, "36,564"));
        rankingList.add(new StepRankingVO("11", "엉금엉금", R.drawable.profile11, "36,128"));
        rankingList.add(new StepRankingVO("12", "김뜀박", R.drawable.profile12, "35,254"));
        rankingList.add(new StepRankingVO("13", "김조깅", R.drawable.profile13, "35,156"));
        rankingList.add(new StepRankingVO("14", "최경보", R.drawable.profile14, "33,895"));
        rankingList.add(new StepRankingVO("15", "이봉주", R.drawable.profile15, "32,167"));
    }

    public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.VH>{


        Context context;
        ArrayList<StepRankingVO> rankingList;

        public RatingAdapter(Context context, ArrayList<StepRankingVO> rankingList) {
            this.context = context;
            this.rankingList = rankingList;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(context).inflate(R.layout.ranking_recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            StepRankingVO item = rankingList.get(position);

            holder.tvNo.setText(item.getNo());
            holder.tvName.setText(item.getName());
            holder.tvStepCount.setText(item.getStepCount());
            Glide.with(context).load(item.getImgRes()).into(holder.ivProfile);
        }

        @Override
        public int getItemCount() {
            return rankingList.size();
        }

        class VH extends RecyclerView.ViewHolder{

            TextView tvNo;
            TextView tvName;
            TextView tvStepCount;
            CircleImageView ivProfile;

            public VH(@NonNull View itemView) {
                super(itemView);

                tvNo = itemView.findViewById(R.id.ranking_item_no);
                tvName = itemView.findViewById(R.id.ranking_item_name);
                tvStepCount = itemView.findViewById(R.id.ranking_item_step_count);
                ivProfile = itemView.findViewById(R.id.ranking_item_profile);
            }
        }
    }
}
