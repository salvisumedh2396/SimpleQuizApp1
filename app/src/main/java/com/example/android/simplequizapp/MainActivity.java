package com.example.android.simplequizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int i;
    private Context context;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("questionBank");

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = (Map) dataSnapshot.getValue();

                for (i = 1; i <= 5; i++) {
                    //Will be changing this condition later to check if the question is a radiobox or checkbox or text for eg. if i=='checkbox' ||....so on
                    String quote1 = "question" + Integer.toString(i);
                    String option1 = "option" + Integer.toString(i) + "a";
                    String option2 = "option" + Integer.toString(i) + "b";
                    String option3 = "option" + Integer.toString(i) + "c";
                    String option4 = "option" + Integer.toString(i) + "d";
                    String answer = "answer" + Integer.toString(i);
                    String type = "type" + Integer.toString(i);
                    String questionno = "Ques " + Integer.toString(i);

                    String question = map.get(quote1);
                    String op1 = map.get(option1);
                    String op2 = map.get(option2);
                    String op3 = map.get(option3);
                    String op4 = map.get(option4);
                    final String ans = map.get(answer);
                    String typ = map.get(type);

                    System.out.println("+++++++++++++++" + typ);

                    //Context context = getApplicationContext();

                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    p.setMargins(50, 0, 0, 0);

                    LinearLayout.LayoutParams pc = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    pc.setMargins(100, 20, 100, 20);

                    TextView textView1 = new TextView(context);
                    textView1.setText(questionno);
                    textView1.setTextColor(Color.WHITE);
                    textView1.setTextSize(15);
                    textView1.setPadding(0, 50, 0, 0);

                    Log.d("View", "Start");
                    try {
                        linearLayout.addView(textView1, p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    TextView textView = new TextView(context);
                    textView.setText(question);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(20);
                    textView.setPadding(0, 0, 0, 100);
                    Log.d("View", "Start");
                    try {
                        linearLayout.addView(textView, p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TextView textView2 = new TextView(context);
                    textView2.setText("Answer");
                    textView2.setTextColor(Color.WHITE);
                    textView2.setTextSize(20);
                    textView2.setPadding(0, 50, 0, 0);
                    Log.d("View", "Start");
                    try {
                        linearLayout.addView(textView2, p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (typ.equals("radio")) {
                        System.out.println("@@@@@@@@@@@@@2@@@@RadioBtn" + question + op1 + op2 + op3 + op4 + ans);
                        final RadioGroup radioGroup = new RadioGroup(context);


                        final RadioButton rdBtn1 = new RadioButton(context);
                        rdBtn1.setId(i);
                        rdBtn1.setText(op1);
                        rdBtn1.setTextColor(Color.WHITE);
                        radioGroup.addView(rdBtn1, pc);

                        final RadioButton rdBtn2 = new RadioButton(context);
                        rdBtn2.setId(i * 2);
                        rdBtn2.setText(op2);
                        rdBtn2.setTextColor(Color.WHITE);
                        radioGroup.addView(rdBtn2, pc);

                        final RadioButton rdBtn3 = new RadioButton(context);
                        rdBtn3.setId(i * 3);
                        rdBtn3.setText(op3);
                        rdBtn3.setTextColor(Color.WHITE);
                        radioGroup.addView(rdBtn3, pc);

                        final RadioButton rdBtn4 = new RadioButton(context);
                        rdBtn4.setText(op4);
                        rdBtn3.setId(i * 4);
                        rdBtn4.setTextColor(Color.WHITE);
                        radioGroup.addView(rdBtn4, pc);

                        linearLayout.addView(radioGroup, p);

                        Button submit = new Button(context);
                        submit.setId(i * 5);
                        submit.setText("Submit");
                        submit.setGravity(View.FOCUS_LEFT);
                        submit.setBackgroundResource(R.color.colorAccent);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        params.setMargins(100, 20, 20, 50);


                        submit.setTextColor(Color.WHITE);

                        linearLayout.addView(submit, params);
                        //System.out.println("ANSWER OF THE DAY 1: "+question);

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rdBtn1.setEnabled(false);
                                rdBtn2.setEnabled(false);
                                rdBtn3.setEnabled(false);
                                rdBtn4.setEnabled(false);
                                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                    RadioButton btn = (RadioButton) radioGroup.getChildAt(i);
                                    String selectedOption = (String) btn.getText();
                                    if (selectedOption.equals(ans)) {
                                        count++;
                                    }
                                }


                            }
                        });


                    } else if (typ.equals("checkbox")) {
                        String answer1 = "answer" + Integer.toString(i) + "a";
                        String answer2 = "answer" + Integer.toString(i) + "b";
                        final String ans1 = map.get(answer1);
                        final String ans2 = map.get(answer2);
                        System.out.println("_-__-___-----CheckboxBtn" + question + op1 + op2 + op3 + op4 + ans1 + ans2);


                        final CheckBox chbx1 = new CheckBox(context);
                        chbx1.setText(op1);
                        chbx1.setTextColor(Color.WHITE);
                        linearLayout.addView(chbx1, pc);

                        final CheckBox chbx2 = new CheckBox(context);
                        chbx2.setText(op2);
                        chbx2.setTextColor(Color.WHITE);
                        linearLayout.addView(chbx2, pc);

                        final CheckBox chbx3 = new CheckBox(context);
                        chbx3.setText(op3);
                        chbx3.setTextColor(Color.WHITE);
                        linearLayout.addView(chbx3, pc);

                        final CheckBox chbx4 = new CheckBox(context);
                        chbx4.setText(op4);
                        chbx4.setTextColor(Color.WHITE);
                        linearLayout.addView(chbx4, pc);

                        Button submit = new Button(context);
                        submit.setId(i * 5);
                        submit.setText("Submit");
                        submit.setGravity(View.FOCUS_LEFT);
                        submit.setBackgroundResource(R.color.colorAccent);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        params.setMargins(100, 20, 20, 50);


                        submit.setTextColor(Color.WHITE);

                        linearLayout.addView(submit, params);
                        //System.out.println("ANSWER OF THE DAY 1: "+question);

                        submit.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                chbx1.setEnabled(false);
                                chbx2.setEnabled(false);
                                chbx3.setEnabled(false);
                                chbx4.setEnabled(false);
                                if (chbx1.isChecked()) {
                                    String text1 = chbx1.getText().toString();
                                    if (text1.equals(ans1) || text1.equals(ans2)) {
                                        count++;
                                    }

                                }
                                if (chbx2.isChecked()) {
                                    String text1 = chbx2.getText().toString();
                                    if (text1.equals(ans1) || text1.equals(ans2)) {
                                        count++;
                                    }

                                }
                                if (chbx3.isChecked()) {
                                    String text1 = chbx3.getText().toString();
                                    if (text1.equals(ans1) || text1.equals(ans2)) {
                                        count++;
                                    }

                                }
                                if (chbx4.isChecked()) {
                                    String text1 = chbx4.getText().toString();
                                    if (text1.equals(ans1) || text1.equals(ans2)) {
                                        count++;
                                    }

                                }
                            }
                        });

                    } else if (typ.equals("textinput")) {
                        final EditText edtxt1 = new EditText(context);
                        edtxt1.setTextColor(Color.WHITE);
                        edtxt1.setTextSize(20);
                        Log.d("View", "Start");
                        try {
                            linearLayout.addView(edtxt1, pc);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Button submit = new Button(context);
                        submit.setId(i * 5);
                        submit.setText("Submit");
                        submit.setGravity(View.FOCUS_LEFT);
                        submit.setBackgroundResource(R.color.colorAccent);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        params.setMargins(100, 20, 20, 50);


                        submit.setTextColor(Color.WHITE);
                        linearLayout.addView(submit, params);
                        //System.out.println("ANSWER OF THE DAY 1: "+question);

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                edtxt1.setEnabled(false);
                                String text = edtxt1.getText().toString();
                                if (text.equals(ans)) {
                                    count++;
                                }
                            }
                        });

                    }


                }

                final int ab = 5;
                Button submit = new Button(context);
                submit.setId(ab);
                submit.setText("GET SCORE");
                submit.setBackgroundResource(R.color.colorAccent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.topMargin = 100;


                submit.setTextColor(Color.WHITE);

                linearLayout.addView(submit, params);
                //System.out.println("ANSWER OF THE DAY 1: "+question);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence text = "Your score is (" + Integer.toString(count) + "/6) !!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
