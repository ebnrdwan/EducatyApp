package fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.asi.educatyapp.R;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class TimeLine extends Fragment {
    //Create Timeline Rows List
    private ArrayList<TimelineRow> TimelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    public TimeLine() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Add Random Rows to the List
        for (int i=0; i<15; i++) {
            TimelineRowsList.add(
                    new TimelineRow(
                            //Row Id
                            i
                            //Row Date
                            ,new Date()
                            //Row Title or null
                            ,"Title "+i
                            //Row Description or null
                            ,"Description " +i
                            //Row bitmap Image or null
                            , BitmapFactory.decodeResource(getResources(), R.drawable.clock + getRandomNumber(0,10))
                            //Row Bellow Line Color
                            , getRandomColor()
                            //Row Bellow Line Size in dp
                            , getRandomNumber(2,2)
                            //Row Image Size in dp
                            , getRandomNumber(15,17)
                            //Row image Background color or -1
                            , -1
                            //Row Background Size in dp or -1
                            , getRandomNumber(25,30)
                    )
            );
        }

        //Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(getActivity(), 0, TimelineRowsList,
                //if true, list will be arranged by date
                true);



        //Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) getActivity().findViewById(R.id.timelineListView);
        myListView.setAdapter(myAdapter);



        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /** To do code here*/

//                    for (int i=0; i<15; i++) {
//                        myAdapter.add(
//                                new TimelineRow(
//                                        //Row Id
//                                        i
//                                        //Row Date
//                                        ,new Date()
//                                        //Row Title or null
//                                        ,"Title "+i
//                                        //Row Description or null
//                                        ,"Description " +i
//                                        //Row bitmap Image or null
//                                        ,BitmapFactory.decodeResource(getResources(), R.drawable.circle_shape + getRandomNumber(0,10))
//                                        //Row Bellow Line Color
//                                        , getRandomColor()
//                                        //Row Bellow Line Size in dp
//                                        , getRandomNumber(2,3)
//                                        //Row Image Size in dp
//                                        , getRandomNumber(25,40)
//                                        //Row Background color or -1
//                                        , getRandomColor()
//                                        //Row Background Size in dp or -1
//                                        , getRandomNumber(1,4)
//                                )
//                        );
//                    }

                }
            }


        });

        //if you wish to handle the clicks on the rows
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = TimelineRowsList.get(position);
                Toast.makeText(getActivity(), row.getTitle(), Toast.LENGTH_SHORT).show();
//                myAdapter.insert(new TimelineRow(
//                                //Row Id
//                                TimelineRowsList.size()
//                                //Row Date
//                                ,new Date()
//                                //Row Title or null
//                                ,"Title "+TimelineRowsList.size()
//                                //Row Description or null
//                                ,"Description " +TimelineRowsList.size()
//                                //Row Image
//                                ,null
//                                //Row Bellow Line Color
//                                , getRandomColor()
//                                //Row Bellow Line Size in dp
//                                , getRandomNumber(2,3)
//                                //Row Image Size in dp
//                                , getRandomNumber(25,40)
//                                //Row Background color or -1
//                                , getRandomColor()
//                                //Row Background Size in dp or -1
//                                , getRandomNumber(25,40)
//                        )
//                        //insert position
//                        ,0);

            }
        };
        myListView.setOnItemClickListener(adapterListener);

    }


    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public int getRandomNumber(int min, int max){
        return  min + (int)(Math.random() * max);
    }
}
