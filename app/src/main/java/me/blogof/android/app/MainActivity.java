package me.blogof.android.app;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineView lineView;
    private BarView barView;
    private PieView pieView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView(){
        lineView = (LineView)findViewById(R.id.line_view);
        lineView.setBackgroundColor(Color.parseColor("#88BCB6"));
        barView = (BarView)findViewById(R.id.bar_view);
        barView.setBackgroundColor(Color.parseColor("#8FC59B"));
        pieView = (PieView)findViewById(R.id.pie_view);
        pieView.setBackgroundColor(Color.parseColor("#9096BD2C"));
    }

    private void initData(){
        List<LinePoint> points = new ArrayList<LinePoint>();
        points.add(new LinePoint(2,"xx"));
        points.add(new LinePoint(15,"xx"));
        points.add(new LinePoint(18,"xx"));
        points.add(new LinePoint(2,"xx"));
        points.add(new LinePoint(88,"xx"));
        points.add(new LinePoint(9,"xx"));
        points.add(new LinePoint(10,"xx"));
        points.add(new LinePoint(2,"xx"));
        points.add(new LinePoint(8,"xx"));
        points.add(new LinePoint(32,"xx"));
        points.add(new LinePoint(98,"xx"));
        lineView.setPoints(points);

        List<Pillar> pillars = new ArrayList<Pillar>();
        pillars.add(new Pillar(100f,"hello","#90F45443","#90E20904"));
        pillars.add(new Pillar(5f,"hello","#90FFCC33","#90FBA30B"));
        pillars.add(new Pillar(19f,"hello","#90235B66","#9005202A"));
        pillars.add(new Pillar(78f,"hello","#90016B88","#90235B66"));
        pillars.add(new Pillar(11f,"hello","#9096BD2C","#90E20904"));
        pillars.add(new Pillar(78f,"hello","#90FFCC33","#90235B66"));
        pillars.add(new Pillar(61f,"hello","#90ffffff","#90E20904"));
        barView.setPillars(pillars);

        List<Pie> pies = new ArrayList<Pie>();
        pies.add(new Pie(100f,"#90F45443"));
        pies.add(new Pie(60f,"#90016B88"));
        pies.add(new Pie(10f,"#90FBA30B"));
        pies.add(new Pie(88f,"#90E20904"));
        pies.add(new Pie(72f,"#90ffffff"));
        pieView.setPies(pies);
    }

    public void addBarValue(View v){
        barView.getPillars().get(0).value -= 10;
        barView.getPillars().get(1).value += 10;
        barView.getPillars().get(2).value += 1;
        barView.setLongClickable(true);
        barView.reloadData();
    }

    public void addLineValue(View v){
        LinePoint p = lineView.getPoint(7);
        p.setValue(p.getValue()+10);
        lineView.reloadData();
    }

    public void addPieValue(View v){
        Pie p = pieView.getPie(1);
        p.setValue(p.getValue() + 50);
        pieView.reloadData();
        ArrayList s = new ArrayList();
    }
}


