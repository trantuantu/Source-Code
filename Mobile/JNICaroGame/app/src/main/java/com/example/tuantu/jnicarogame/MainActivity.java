package com.example.tuantu.jnicarogame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    GridLayout grid;
    int[][] arr;
    int bout = 1;
    int n = 50;
    int m;
    int width, height;
    ImageButton replay, undo;
    TextView x_point, o_point;
    int xpoint, opoint;
    boolean isWin;
    private float mPosX;
    private float mPosY;
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private float mLastTouchX;
    private float mLastTouchY;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private HorizontalScrollView hScroll;
    LinearLayout mainView;
    private BroadcastReceiver mMessageReceiver;
    ChessboardView v;
    ArrayList<Step> win1 = new ArrayList<Step>();
    ArrayList<Step> win2 = new ArrayList<Step>();

    Stack<Step> stepStack = new Stack<Step>();

    static {
        System.loadLibrary("hello-android-jni");
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public native String getMsgFromJni();

    public native String HelloWorld(String a);

    public native int checkWin(int y, int x, int[][] arr, int n);

    public native int test(int[][] arr, int x, int y, int n);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //grid = (GridLayout) findViewById(R.id.mainView);
        replay = (ImageButton) findViewById(R.id.replay);
        undo = (ImageButton) findViewById(R.id.undo);
        v = new ChessboardView(getApplicationContext());
        //hScroll = (HorizontalScrollView)findViewById(R.id.hScroll);
        mainView = (LinearLayout)findViewById(R.id.chess);

        x_point = (TextView) findViewById(R.id.xpoint);
        o_point = (TextView) findViewById(R.id.opoint);

        mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());

        DisplayMetrics d = getResources().getDisplayMetrics();
        width = d.widthPixels;
        height = d.heightPixels - dpToPx(100);
        m = 50;
        //grid.setRowCount(m);
        //grid.setColumnCount(n);


        arr = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                arr[i][j] = 0;

        //initChessBoard();
        initChessBoardBitmap();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        /*ScrollView temp = (ScrollView)findViewById(R.id.hScroll);
        temp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Toast.makeText(getApplicationContext(), "Zoom", Toast.LENGTH_LONG).show();
                mScaleDetector.onTouchEvent(motionEvent);
                return false;
            }
        });*/

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                int i = Integer.parseInt(intent.getStringExtra("i"));
                int j = Integer.parseInt(intent.getStringExtra("j"));

                if (bout == 1 && isWin == false) {
                    //v.setImageResource(R.drawable.square_x);
                    arr[i][j] = 1;
                    bout = 2;
                    v.bout = 2;
                    Step step = new Step();
                    step.x = i;
                    step.y = j;
                    step.bout = 1;
                    stepStack.push(step);
                    int res = test(arr, step.y, step.x, n);

                    if (res == 1) {
                        Toast.makeText(getApplicationContext(), "X Win!", Toast.LENGTH_LONG).show();
                        xpoint++;
                        x_point.setText(String.valueOf(xpoint));
                        isWin = true;
                        v.isWin = true;
                        drawWin(step.y, step.x);
                    } /*else if (res == 2) {
                        Toast.makeText(getApplicationContext(), "O Win!", Toast.LENGTH_LONG).show();
                        opoint++;
                        o_point.setText(String.valueOf(opoint));
                        isWin = true;
                        v.isWin = true;
                        //drawWin(step.y, step.x);
                    }*/ else if (res == 3 || res == 4) {
                        drawWin(step.y, step.x);
                    }
                } else if (bout == 2 && isWin == false) {
                    bout = 1;
                    v.bout = 1;
                    arr[i][j] = 2;
                    Step step = new Step();
                    step.x = i;
                    step.y = j;
                    step.bout = 2;
                    stepStack.push(step);
                    int res = test(arr, step.y, step.x, n);
                    /*if (res == 1) {
                        Toast.makeText(getApplicationContext(), "X Win!", Toast.LENGTH_LONG).show();
                        xpoint++;
                        x_point.setText(String.valueOf(xpoint));
                        isWin = true;
                        v.isWin = true;
                        //drawWin(step.y, step.x);
                    } else */if (res == 2) {
                        Toast.makeText(getApplicationContext(), "O Win!", Toast.LENGTH_LONG).show();
                        opoint++;
                        o_point.setText(String.valueOf(opoint));
                        isWin = true;
                        v.isWin = true;
                        drawWin(step.y, step.x);
                    } else if (res == 3 || res == 4) {
                        drawWin(step.y, step.x);
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
    }

    void initChessBoardBitmap() {

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(param);
        //v.setImageResource(R.drawable.square_o);
        v.drawChess(1);
        v.c = getApplicationContext();
        mainView.addView(v);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepStack.size() > 0) {
                    Step step = stepStack.pop();
                    if (test(arr, step.y, step.x, n) == 1) {
                        xpoint--;
                        x_point.setText(String.valueOf(xpoint));
                        if (win1.size() > 0 && win1.size() < 4) {
                            //unDraw(win1, 1);
                        }
                    } else if (test(arr, step.y, step.x, n) == 2) {
                        opoint--;
                        o_point.setText(String.valueOf(opoint));
                        if (win2.size() > 0 && win2.size() < 4) {
                            // unDraw(win2, 2);
                        }
                    }

                    isWin = false;
                    //ImageView img = (ImageView) grid.findViewById(n * step.x + step.y);
                    //img.setImageResource(R.drawable.square);
                    v.isWin = false;
                    v.px = step.x;
                    v.py = step.y;
                    v.drawChess(2);
                    arr[step.x][step.y] = 0;
                    v.arr[step.x][step.y] = 0;
                    if (bout == 1) {
                        bout = 2;
                        v.bout = 2;
                        //if (win2.size() > 0) remove(win2, step);
                        //if (win2.size() < 4) unDraw(win2, 2);
                    } else {
                        bout = 1;
                        v.bout = 1;
                        //if (win1.size() > 0) remove(win1, step);
                        //if (win1.size() < 4) unDraw(win1, 1);
                    }
                }
            }
        });

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < m; i++)
                    for (int j = 0; j < n; j++) {
                        arr[i][j] = 0;
                        v.arr[i][j] = 0;
                        //ImageView img = (ImageView) grid.findViewById(i * n + j);
                        //img.setImageResource(R.drawable.square);
                    }
                isWin = false;
                v.isWin = false;
                v.bout = 1;
                bout = 1;
                //v.win1.clear();
                //v.win2.clear();
                stepStack.clear();
                Toast.makeText(getApplicationContext(), HelloWorld("New game"), Toast.LENGTH_LONG).show();
                v.drawChess(1);

            }
        });
    }

    void initChessBoard() {
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                ImageView img = new ImageView(getApplicationContext());
                img.setImageResource(R.drawable.square);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.width = dpToPx(50);
                param.height = dpToPx(50);
                param.rowSpec = GridLayout.spec(i);
                param.columnSpec = GridLayout.spec(j);
                img.setLayoutParams(param);
                img.setId(j + i * n);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageView v = (ImageView) view;

                        if (arr[view.getId() / n][view.getId() % n] == 0 && bout == 1 && isWin == false) {
                            v.setImageResource(R.drawable.square_x);
                            arr[view.getId() / n][view.getId() % n] = 1;
                            bout = 2;

                            Step step = new Step();
                            step.x = view.getId() / n;
                            step.y = view.getId() % n;
                            step.bout = 1;
                            stepStack.push(step);
                            int res = test(arr, step.y, step.x, n);

                            if (res == 1) {
                                Toast.makeText(getApplicationContext(), "X Win!", Toast.LENGTH_LONG).show();
                                xpoint++;
                                x_point.setText(String.valueOf(xpoint));
                                isWin = true;
                                drawWin(step.y, step.x);
                            } else if (res == 2) {
                                Toast.makeText(getApplicationContext(), "O Win!", Toast.LENGTH_LONG).show();
                                opoint++;
                                o_point.setText(String.valueOf(opoint));
                                isWin = true;
                                drawWin(step.y, step.x);
                            } else if (res == 3 || res == 4) {
                                drawWin(step.y, step.x);
                            }
                        } else if (arr[view.getId() / n][view.getId() % n] == 0 && bout == 2 && isWin == false) {
                            v.setImageResource(R.drawable.square_o);
                            arr[view.getId() / n][view.getId() % n] = 2;
                            bout = 1;

                            Step step = new Step();
                            step.x = view.getId() / n;
                            step.y = view.getId() % n;
                            step.bout = 2;
                            stepStack.push(step);
                            int res = test(arr, step.y, step.x, n);
                            if (res == 1) {
                                Toast.makeText(getApplicationContext(), "X Win!", Toast.LENGTH_LONG).show();
                                xpoint++;
                                x_point.setText(String.valueOf(xpoint));
                                isWin = true;
                                drawWin(step.y, step.x);
                            } else if (res == 2) {
                                Toast.makeText(getApplicationContext(), "O Win!", Toast.LENGTH_LONG).show();
                                opoint++;
                                o_point.setText(String.valueOf(opoint));
                                isWin = true;
                                drawWin(step.y, step.x);
                            } else if (res == 3 || res == 4) {
                                drawWin(step.y, step.x);
                            }
                        }
                    }
                });
                grid.addView(img);
            }

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < m; i++)
                    for (int j = 0; j < n; j++) {
                        arr[i][j] = 0;
                        ImageView img = (ImageView) grid.findViewById(i * n + j);
                        img.setImageResource(R.drawable.square);
                    }
                isWin = false;
                Toast.makeText(getApplicationContext(), HelloWorld("New game"), Toast.LENGTH_LONG).show();
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepStack.size() > 0) {
                    Step step = stepStack.pop();
                    if (test(arr, step.y, step.x, n) == 1) {
                        xpoint--;
                        x_point.setText(String.valueOf(xpoint));
                        if (win1.size() > 0 && win1.size() < 4) {
                            unDraw(win1, 1);
                        }
                    } else if (test(arr, step.y, step.x, n) == 2) {
                        opoint--;
                        o_point.setText(String.valueOf(opoint));
                        if (win2.size() > 0 && win2.size() < 4) {
                            unDraw(win2, 2);
                        }
                    }

                    isWin = false;
                    //ImageView img = (ImageView) grid.findViewById(n * step.x + step.y);
                    //img.setImageResource(R.drawable.square);
                    v.isWin = false;
                    v.px = step.x;
                    v.py = step.y;
                    v.drawChess(2);
                    arr[(n * step.x + step.y) / n][(n * step.x + step.y) % n] = 0;
                    if (bout == 1) {
                        bout = 2;
                        if (win2.size() > 0) remove(win2, step);
                        if (win2.size() < 4) unDraw(win2, 2);
                    } else {
                        bout = 1;
                        if (win1.size() > 0) remove(win1, step);
                        if (win1.size() < 4) unDraw(win1, 1);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public void drawWin(int x, int y) {
        int countRow1 = 0;
        int countRow2 = 0;
        int space = 0;
        win1.clear();
        win2.clear();

        for (int i = 0; i < n; i++) {
            if (arr[y][i] == 1) {
                countRow2 = 0;
                countRow1++;
                Step t = new Step();
                t.x = y;
                t.y = i;
                t.bout = 1;
                win1.add(t);
                win2.clear();
                if (countRow1 >= 5) {
                    //draw(win1, 1);
                    v.win1 = win1;
                    v.winner = 1;
                    v.drawChess(3);
                    return;
                }
            } else if (arr[y][i] == 2) {
                countRow2++;
                countRow1 = 0;
                Step t = new Step();
                t.x = y;
                t.y = i;
                t.bout = 2;
                win2.add(t);
                win1.clear();
                if (countRow2 >= 5) {
                    //draw(win2, 2);
                    v.win2 = win2;
                    v.winner = 2;
                    v.drawChess(3);
                    return;
                }
            } else {
                if (countRow1 == 4 && space == 1) {
                   // draw(win1, 1);
                    v.win1 = win1;
                    v.winner = 1;
                    v.drawChess(3);
                    return;
                }
                if (countRow2 == 4 && space == 1) {
                    //draw(win2, 2);
                    v.win2 = win2;
                    v.winner = 2;
                    v.drawChess(3);
                    return;
                }

                countRow1 = 0;
                countRow2 = 0;
                win1.clear();
                win2.clear();

                if (i + 1 < n && arr[y][i + 1] != 0) space++;
                else space = 0;

            }
        }

        int countCol1 = 0;
        int countCol2 = 0;
        win1.clear();
        win2.clear();
        space = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i][x] == 1) {
                countCol1++;
                countCol2 = 0;
                Step t = new Step();
                t.x = i;
                t.y = x;
                t.bout = 1;
                win1.add(t);
                win2.clear();
                if (countCol1 >= 5) {
                    v.win1 = win1;
                    v.winner = 1;
                    v.drawChess(3);
                    return;
                }
            } else if (arr[i][x] == 2) {
                countCol1 = 0;
                countCol2++;
                Step t = new Step();
                t.x = i;
                t.y = x;
                t.bout = 2;
                win2.add(t);
                win1.clear();
                if (countCol2 >= 5) {
                    v.win2 = win2;
                    v.winner = 2;
                    v.drawChess(3);
                    return;
                }
            } else {
                if (countCol1 == 4 && space == 1) {
                    v.win1 = win1;
                    v.winner = 1;
                    v.drawChess(3);
                    return;
                }
                if (countCol2 == 4 && space == 1) {
                    v.win2 = win2;
                    v.winner = 2;
                    v.drawChess(3);
                    return;
                }

                countCol1 = 0;
                countCol2 = 0;
                win1.clear();
                win2.clear();

                if (space == 0) {
                    if (i + 1 < n && arr[i + 1][x] != 0) {
                        if (space == 0) space++;
                    } else space = 0;
                }
            }
        }

        int countDiagX1 = 0;
        int countDiagX2 = 0;
        int temp;
        win1.clear();
        win2.clear();

        if (y + x >= n) {
            temp = x - (n - 1 - y);
            for (int i = temp; i < n; i++)
                if (arr[n - (i - temp) - 1][i] == 1) {
                    countDiagX1++;
                    countDiagX2 = 0;
                    Step t = new Step();
                    t.x = n - (i - temp) - 1;
                    t.y = i;
                    t.bout = 1;
                    win1.add(t);
                    win2.clear();
                    if (countDiagX1 >= 5) {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                        return;
                    }
                } else if (arr[n - (i - temp) - 1][i] == 2) {
                    countDiagX2++;
                    countDiagX1 = 0;
                    Step t = new Step();
                    t.x = n - (i - temp) - 1;
                    t.y = i;
                    t.bout = 2;
                    win2.add(t);
                    win1.clear();
                    if (countDiagX2 >= 5) {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                        return;
                    }
                } else {
                    if (countDiagX1 == 4 && space == 1)
                    {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                    }
                    if (countDiagX2 == 4 && space == 1)
                    {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                    }
                    countDiagX1 = 0;
                    countDiagX2 = 0;
                    win1.clear();
                    win2.clear();
                    if (space == 0) {
                        if (arr[n - ((i + 1) - temp) - 1][i + 1] != 0) {
                            space++;
                        } else space = 0;
                    }
                }
        } else {
            win1.clear();
            win2.clear();
            temp = x + y;
            for (int i = 0; i <= temp; i++)
                if (arr[temp - i][i] == 1) {
                    countDiagX1++;
                    countDiagX2 = 0;

                    Step t = new Step();
                    t.x = temp - i;
                    t.y = i;
                    t.bout = 1;
                    win1.add(t);
                    win2.clear();
                    if (countDiagX1 >= 5) {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                        return;
                    }
                } else if (arr[temp - i][i] == 2) {
                    countDiagX2++;
                    countDiagX1 = 0;
                    Step t = new Step();
                    t.x = temp - i;
                    t.y = i;
                    t.bout = 2;
                    win2.add(t);
                    win1.clear();
                    if (countDiagX2 >= 5) {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                        return;
                    }
                } else {
                    if (countDiagX1 == 4 && space == 1)
                    {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                    }
                    if (countDiagX2 == 4 && space == 1)
                    {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                    }
                    countDiagX1 = 0;
                    countDiagX2 = 0;
                    win1.clear();
                    win2.clear();
                    if (space == 0) {
                        if (arr[temp - (i + 1)][i + 1] != 0) {
                            space++;
                        } else space = 0;
                    }

                }

        }

        int countDiagY1 = 0;
        int countDiagY2 = 0;
        win1.clear();
        win2.clear();
        if (x - y >= 0) {
            temp = x - y;
            for (int i = temp; i < n; i++)
                if (arr[i - temp][i] == 1) {
                    countDiagY1++;
                    countDiagY2 = 0;
                    Step t = new Step();
                    t.x = i - temp;
                    t.y = i;
                    t.bout = 1;
                    win1.add(t);
                    win2.clear();
                    if (countDiagY1 >= 5) {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                        return;
                    }
                } else if (arr[i - temp][i] == 2) {
                    countDiagY2++;
                    countDiagY1 = 0;
                    Step t = new Step();
                    t.x = i - temp;
                    t.y = i;
                    t.bout = 2;
                    win2.add(t);
                    win1.clear();
                    if (countDiagY2 >= 5) {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                        return;
                    }
                } else {
                    if (countDiagY1 == 4 && space == 1)
                    {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                    }
                    if (countDiagY2 == 4 && space == 1)
                    {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                    }
                    countDiagY1 = 0;
                    countDiagY2 = 0;
                    win1.clear();
                    win2.clear();
                    if (space == 0) {
                        if (arr[(i + 1) - temp][i + 1] != 0) {
                           space++;
                        } else space = 0;
                    }

                }
        } else {
            win1.clear();
            win2.clear();
            temp = x + (n - 1 - y);
            for (int i = 0; i <= temp; i++)
                if (arr[(n - temp) + i - 1][i] == 1) {
                    countDiagY1++;
                    countDiagY2 = 0;
                    Step t = new Step();
                    t.x = (n - temp) + i - 1;
                    t.y = i;
                    t.bout = 1;
                    win1.add(t);
                    win2.clear();
                    if (countDiagY1 >= 5) {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                        return;
                    }
                } else if (arr[(n - temp) + i - 1][i] == 2) {
                    countDiagY2++;
                    countDiagY1 = 0;
                    Step t = new Step();
                    t.x = (n - temp) + i - 1;
                    t.y = i;
                    t.bout = 2;
                    win2.add(t);
                    win1.clear();
                    if (countDiagY2 >= 5) {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                        return;
                    }
                } else {
                    if (countDiagY1 == 4 && space == 1)
                    {
                        v.win1 = win1;
                        v.winner = 1;
                        v.drawChess(3);
                    }
                    if (countDiagY2 == 4 && space == 1)
                    {
                        v.win2 = win2;
                        v.winner = 2;
                        v.drawChess(3);
                    }
                    countDiagY1 = 0;
                    countDiagY2 = 0;
                    win1.clear();
                    win2.clear();
                    if (space == 0) {
                        if (arr[(n - temp) + i][i + 1] != 0) {
                            space++;
                        } else space = 0;
                    }
                }
        }
        return;
    }

    void draw(ArrayList<Step> sWin, int bout) {
        for (int k = 0; k < sWin.size(); k++) {
            Step step = sWin.get(k);
            ImageView img = (ImageView) grid.findViewById(n * step.x + step.y);
            if (bout == 1) img.setImageResource(R.drawable.x_win);
            else img.setImageResource(R.drawable.o_win);
        }
    }

    void unDraw(ArrayList<Step> sWin, int bout) {
        for (int k = 0; k < sWin.size(); k++) {
            Step step = sWin.get(k);
            ImageView img = (ImageView) grid.findViewById(n * step.x + step.y);
            if (bout == 1) img.setImageResource(R.drawable.square_x);
            else img.setImageResource(R.drawable.square_o);
        }
    }

    void remove(ArrayList<Step> sWin, Step obj) {
        for (int i = 0; i < sWin.size(); i++) {
            if (obj.x == sWin.get(i).x && obj.y == sWin.get(i).y && obj.bout == sWin.get(i).bout) {
                sWin.remove(i);
                break;
            }
        }
    }




    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        //mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                }

                mLastTouchX = x;
                mLastTouchY = y;
                //Toast.makeText(getApplicationContext(), "Zoom", Toast.LENGTH_LONG).show();
                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            //invalidate();
            //Toast.makeText(getApplicationContext(), "Zoom", Toast.LENGTH_LONG).show();
            for (int i = 0; i < 400; i++)
            {
                ImageView img = (ImageView)grid.findViewById(i);
                if (mScaleFactor < 1 && img.getLayoutParams().width - dpToPx(5)>= 0)
                {
                    img.getLayoutParams().width -= dpToPx(5);
                    img.getLayoutParams().height -= dpToPx(5);
                }else if (mScaleFactor >= 1 && img.getLayoutParams().width + dpToPx(5) < dpToPx(50)) {
                    img.getLayoutParams().width += dpToPx(5);
                    img.getLayoutParams().height += dpToPx(5);
                }
                img.postInvalidate();
            }
            grid.postInvalidate();
            return true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tuantu.jnicarogame/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tuantu.jnicarogame/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}