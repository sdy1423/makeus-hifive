package com.song.makeushifive.src.main.home.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.song.makeushifive.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.song.makeushifive.src.ApplicationClass.DATE_FORMAT;

public class CalendarAdapter extends RecyclerView.Adapter {
    private final int HEADER_TYPE = 0;
    private final int EMPTY_TYPE = 1;
    private final int DAY_TYPE = 2;
    Context context;
    int rowCount=0;
    private ArrayList<DATA> datas;
    private ArrayList<TileItem> tileItems;
    private List<Object> mCalendarList;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos,int year,int month,int day) throws ParseException;
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public CalendarAdapter(ArrayList<TileItem> tileItem, ArrayList<DATA> datas, List<Object> calendarList, Context context) {
        this.tileItems=tileItem;
        this.mCalendarList = calendarList;
        this.context = context;
        this.datas =datas;
    }

    public void setCalendarList(List<Object> calendarList) {
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) { //뷰타입 나누기
        Object item = mCalendarList.get(position);
        if (item instanceof Long) {
            return HEADER_TYPE; //날짜 타입
        } else if (item instanceof String) {
            return EMPTY_TYPE; // 비어있는 일자 타입
        } else {
            return DAY_TYPE; // 일자 타입

        }
    }


    // viewHolder 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // 날짜 타입
//        if (viewType == HEADER_TYPE) {

//            HeaderViewHolder viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.item_calendar_header, parent, false));

//            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)viewHolder.itemView.getLayoutParams();
//            params.setFullSpan(true); //Span을 하나로 통합하기
//            viewHolder.itemView.setLayoutParams(params);

//            return viewHolder;

            //비어있는 일자 타입
//        }
        if (viewType == EMPTY_TYPE) {
            return new EmptyViewHolder(inflater.inflate(R.layout.item_day_empty, parent, false));

        }
        // 일자 타입
        else {
            return new DayViewHolder(inflater.inflate(R.layout.item_day, parent, false));

        }

    }

    // 데이터 넣어서 완성시키는것
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

//        /**날짜 타입 꾸미기*/
//        /** EX : 2018년 8월*/
//        if (viewType == HEADER_TYPE) {
//            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
//            Object item = mCalendarList.get(position);
//            CalendarHeader model = new CalendarHeader();
//
//            // long type의 현재시간
//            if (item instanceof Long) {
//                // 현재시간 넣으면, 2017년 7월 같이 패턴에 맞게 model에 데이터들어감.
//                model.setHeader((Long) item);
//            }
//            // view에 표시하기
//            holder.bind(model);
//        }
//        /** 비어있는 날짜 타입 꾸미기 */
//        /** EX : empty */


        if (viewType == EMPTY_TYPE) {
            EmptyViewHolder holder = (EmptyViewHolder) viewHolder;
            EmptyDay model = new EmptyDay();
            holder.bind(model);

            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            //if you need three fix imageview in width
            holder.itemView.getLayoutParams().width = displaymetrics.widthPixels / 7;

        }
        /** 일자 타입 꾸미기 */
        /** EX : 22 */
        else if (viewType == DAY_TYPE) {
            DayViewHolder holder = (DayViewHolder) viewHolder;

            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            holder.itemView.getLayoutParams().width = displaymetrics.widthPixels / 7;


            Object item = mCalendarList.get(position);
            Day model = new Day();
            if (item instanceof Calendar) {

                // Model에 Calendar값을 넣어서 몇일인지 데이터 넣기
                model.setCalendar((Calendar) item);
            }
            // Model의 데이터를 View에 표현하기
            holder.bind(model);
            int year = datas.get(position).getYear();
            int month = datas.get(position).getMonth();
            int day = datas.get(position).getDay();

            ArrayList<TileItem> items = new ArrayList<>();
            items.clear();
            try {
                if (!tileItems.isEmpty()) {
                    for (int i = 0; i < tileItems.size(); i++) {
                        int TileYear = tileItems.get(i).getYear();
                        int TileMonth = tileItems.get(i).getMonth();
                        int TileDay = tileItems.get(i).getDay();
                        if (year == TileYear && (month + 1) == TileMonth && day == TileDay) {
                            TileItem tileItem = new TileItem(TileYear, TileMonth, TileDay, tileItems.get(i).getColor(), tileItems.get(i).getTitle());
                            boolean flag = false;
                            try{
                                if(!items.isEmpty()){
                                    for(int j=0;j<items.size();j++){
                                        if(tileItem.getTitle().equals(items.get(j).getTitle())){
                                            flag=true;
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(!flag){
                                items.add(tileItem);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.TileRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            TileRecyclerAdapter adapter = new TileRecyclerAdapter(items, context);
            holder.TileRecycler.setAdapter(adapter);
            adapter.setOnItemClickListener(new TileRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos, int year, int month, int day) throws ParseException {
//                                Log.e("캘린더",""+year+" "+month+" "+day);
                    mListener.onItemClick(v, pos, year, month, day);
                }
            });
            GregorianCalendar cal = new GregorianCalendar();
            if(year==cal.get(Calendar.YEAR) && month==cal.get(Calendar.MONTH) && day==cal.get(Calendar.DATE)){
                holder.itemView.setBackgroundColor(Color.parseColor("#F4F4F4"));
            }
        }
    }


    // 개수구하기
    @Override
    public int getItemCount() {
        if (mCalendarList != null) {
            return mCalendarList.size();
        }
        return 0;
    }
    /** viewHolder */
    private class HeaderViewHolder extends RecyclerView.ViewHolder { //날짜 타입 ViewHolder

        TextView itemHeaderTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            initView(itemView);
        }


        public void initView(View v){

//            itemHeaderTitle = (TextView)v.findViewById(R.id.item_header_title);

        }

        public void bind(CalendarHeader model){

            // 일자 값 가져오기
            String header = ((CalendarHeader)model).getHeader();

            // header에 표시하기, ex : 2018년 8월
            itemHeaderTitle.setText(header);


        };
    }


    private class EmptyViewHolder extends RecyclerView.ViewHolder { // 비어있는 요일 타입 ViewHolder


        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);



            initView(itemView);
        }

        public void initView(View v){

        }

        public void bind(EmptyDay model){


        };

    }

    // TODO : item_day와 매칭
    private class DayViewHolder extends RecyclerView.ViewHolder {

        TextView itemDay;
        RecyclerView TileRecycler;
        LinearLayout item_layout;

        public DayViewHolder(@NonNull final View itemView) {
            super(itemView);


            initView(itemView);

            View.OnClickListener addScheduleListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        int month = datas.get(pos).getMonth()+1;
                        try {
                            mListener.onItemClick(v, pos,datas.get(pos).getYear(),datas.get(pos).getMonth(),datas.get(pos).getDay());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            itemView.setOnClickListener(addScheduleListener);
            item_layout.setOnClickListener(addScheduleListener);
            TileRecycler.setOnClickListener(addScheduleListener);
        }


        public void initView(View v){
            TileRecycler=v.findViewById(R.id.tile_recycler);
            itemDay = (TextView)v.findViewById(R.id.item_day);
            item_layout=v.findViewById(R.id.item_layout);
        }

        public void bind(Day model){

            // 일자 값 가져오기
            String day = ((Day)model).getDay();

            // 일자 값 View에 보이게하기
            itemDay.setText(day);

        };
    }


    public String MakeStringForm(int year,int month,int day) throws ParseException {
//        month+=1;
        String stringDate="";
        stringDate+=String.valueOf(year);
        stringDate+="-";
        stringDate+=String.valueOf(month);
        stringDate+="-";
        stringDate+=String.valueOf(day);
        return stringDate;
    }
    public Date MakeDateForm(int year,int month,int day) throws ParseException {
//        month+=1;
        String stringDate="";
        stringDate+=String.valueOf(year);
        stringDate+="-";
        stringDate+=String.valueOf(month);
        stringDate+="-";
        stringDate+=String.valueOf(day);
        Date date = DATE_FORMAT.parse(stringDate);
        return date;
    }

}
