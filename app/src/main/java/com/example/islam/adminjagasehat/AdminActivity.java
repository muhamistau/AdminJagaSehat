package com.example.islam.adminjagasehat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    CardView dataIndividu;
    int size, nLaki, nPerem, nPns, nTni, nPolri, nPegawai, nWiras, nMaha, nPelajar, nLain, nSd, nSmp, nSma, nPerguruan;
    PieChart pieChart;
    PieChart pieChartPekerjaan;
    PieChart pieChartPendidikan;
    String email, jk, pekerjaan, pendidikan, umur;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dataIndividu = (CardView) findViewById(R.id.data_individu);
        pieChart = (PieChart) findViewById(R.id.pieChartJk);
        pieChartPekerjaan = (PieChart) findViewById(R.id.pieChartPekerjaan);
        pieChartPendidikan = (PieChart) findViewById(R.id.pieChartPendidikan);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://jagasehat-fd156.firebaseio.com/users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();

                ArrayList<String> emailList = new ArrayList<>();
                ArrayList<String> jkList = new ArrayList<>();
                ArrayList<String> pekerjaanList = new ArrayList<>();
                ArrayList<String> pendidikanList = new ArrayList<>();
                ArrayList<String> umurList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    email = dataSnapshot1.child("email").getValue(String.class);
                    emailList.add(email);
                    jk = dataSnapshot1.child("jk").getValue(String.class);
                    jkList.add(jk);
                    pekerjaan = dataSnapshot1.child("pekerjaan").getValue(String.class);
                    pekerjaanList.add(pekerjaan);
                    pendidikan = dataSnapshot1.child("pendidikan").getValue(String.class);
                    pendidikanList.add(pendidikan);
                }

                for (String jk : jkList) {
                    if (jk.contains("aki")) {
                        nLaki += 1;
                    } else {
                        nPerem += 1;
                    }
                }

                for (String pekerjaan : pekerjaanList) {
                    if (pekerjaan.contains("PNS")) {
                        nPns += 1;
                    } else if (pekerjaan.contains("TNI")) {
                        nTni += 1;
                    } else if (pekerjaan.contains("olr")) {
                        nPolri += 1;
                    } else if (pekerjaan.contains("awai")) {
                        nPegawai += 1;
                    } else if (pekerjaan.contains("irasw")) {
                        nWiras += 1;
                    } else if (pekerjaan.contains("hasis")) {
                        nMaha += 1;
                    } else if (pekerjaan.contains("laja")) {
                        nPelajar += 1;
                    } else {
                        nLain += 1;
                    }
                }

                for (String pendidikan : pendidikanList) {
                    if (pendidikan.contains("SD")) {
                        nSd += 1;
                    } else if (pendidikan.contains("MP")) {
                        nSmp += 1;
                    } else if (pendidikan.contains("MA")) {
                        nSma += 1;
                    } else {
                        nPerguruan += 1;
                    }
                }

                // Pie Chart untuk jenis kelamin
//                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);
//                pieChart.setExtraOffsets(5, 10, 5, 5);
                pieChart.setCenterText("Total data\n" + size);

                pieChart.setDragDecelerationFrictionCoef(0.95f);

                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);

                ArrayList<PieEntry> yValues = new ArrayList<>();
                yValues.add(new PieEntry(nLaki, "Laki-laki"));
                yValues.add(new PieEntry(nPerem, "Perempuan"));

                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet dataSet = new PieDataSet(yValues, "Jenis Kelamin");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData data = new PieData(dataSet);
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);

                // Pie Chart untuk jenis pekerjaan
//                pieChart.setUsePercentValues(true);
                pieChartPekerjaan.getDescription().setEnabled(false);
                pieChartPekerjaan.setExtraOffsets(5, 10, 5, 5);
                pieChartPekerjaan.setCenterText("Total data\n" + size);

                pieChartPekerjaan.setDragDecelerationFrictionCoef(0.95f);

                pieChartPekerjaan.setDrawHoleEnabled(true);
                pieChartPekerjaan.setHoleColor(Color.WHITE);
                pieChartPekerjaan.setTransparentCircleRadius(61f);

                ArrayList<PieEntry> yValuesPekerjaan = new ArrayList<>();
                yValuesPekerjaan.add(new PieEntry(nPns, "PNS"));
                yValuesPekerjaan.add(new PieEntry(nTni, "TNI"));
                yValuesPekerjaan.add(new PieEntry(nPolri, "Polri"));
                yValuesPekerjaan.add(new PieEntry(nPegawai, "Pegawai Swasta"));
                yValuesPekerjaan.add(new PieEntry(nWiras, "Wiraswasta"));
                yValuesPekerjaan.add(new PieEntry(nMaha, "Mahasiswa"));
                yValuesPekerjaan.add(new PieEntry(nPelajar, "Pelajar"));
                yValuesPekerjaan.add(new PieEntry(nLain, "Lainnya"));

                pieChartPekerjaan.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet dataSetPekerjaan = new PieDataSet(yValuesPekerjaan, "Pekerjaan");
                dataSetPekerjaan.setSliceSpace(3f);
                dataSetPekerjaan.setSelectionShift(5f);
                dataSetPekerjaan.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData dataPekerjaan = new PieData(dataSetPekerjaan);
                dataPekerjaan.setValueTextSize(10f);
                dataPekerjaan.setValueTextColor(Color.BLACK);

                pieChartPekerjaan.setData(dataPekerjaan);

                // Pie Chart untuk jenis pendidikan
//                pieChart.setUsePercentValues(true);
                pieChartPendidikan.getDescription().setEnabled(false);
                pieChartPendidikan.setExtraOffsets(5, 10, 5, 5);
                pieChartPendidikan.setCenterText("Total data\n" + size);

                pieChartPendidikan.setDragDecelerationFrictionCoef(0.95f);

                pieChartPendidikan.setDrawHoleEnabled(true);
                pieChartPendidikan.setHoleColor(Color.WHITE);
                pieChartPendidikan.setTransparentCircleRadius(61f);

                ArrayList<PieEntry> yValuesPendidikan = new ArrayList<>();
                yValuesPendidikan.add(new PieEntry(nSd, "SD"));
                yValuesPendidikan.add(new PieEntry(nSmp, "SMP"));
                yValuesPendidikan.add(new PieEntry(nSma, "SMA"));
                yValuesPendidikan.add(new PieEntry(nPerguruan, "Perguruan Tinggi"));

                pieChartPendidikan.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet dataSetPendidikan = new PieDataSet(yValuesPendidikan, "Pendidikan");
                dataSetPendidikan.setSliceSpace(3f);
                dataSetPendidikan.setSelectionShift(5f);
                dataSetPendidikan.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData dataPendidikan = new PieData(dataSetPendidikan);
                dataPendidikan.setValueTextSize(10f);
                dataPendidikan.setValueTextColor(Color.BLACK);

                pieChartPendidikan.setData(dataPendidikan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dataIndividu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });


    }
}
