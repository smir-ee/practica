package com.example.bank.utils;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank.CourseParams;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NetworkUtilsCourse {
    private static final String COURSE_API_BASE_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";


    public static ArrayList courseDate(String date) {
        ArrayList<CourseParams> arrCourses = new ArrayList<>();
        URL cbrf = null;
        try {
            cbrf = new URL(COURSE_API_BASE_URL + date);
            HttpURLConnection urlConnection = (HttpURLConnection)cbrf.openConnection();
            urlConnection.setRequestMethod("GET");
            if(urlConnection.getResponseCode()==200) {
                InputStream response_body = urlConnection.getInputStream();
                InputStreamReader response_reader = new InputStreamReader(response_body);
                XmlPullParserFactory f = XmlPullParserFactory.newInstance();
                f.setNamespaceAware(false);
                XmlPullParser xpp = f.newPullParser();
                xpp.setInput(response_reader);


                String pom = "";
                CourseParams course = null;
                while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
                    if(xpp.getName()!=null)
                        pom = xpp.getName();
                    switch(xpp.getEventType()) {
                        case XmlPullParser.START_TAG:
                            if(pom.equals("Valute"))
                                course = new CourseParams();
                            break;
                        case XmlPullParser.TEXT:
                            if(pom.equals("Name"))
                                course.Name=xpp.getText();
                            if(pom.equals("CharCode"))
                                course.CharCode=xpp.getText();
                            if(pom.equals("Nominal"))
                                course.Nominal=Integer.parseInt(xpp.getText());
                            if(pom.equals("Value"))
                                course.Price = Float.parseFloat(xpp.getText().replace(",", "."));
                            break;
                        case XmlPullParser.END_TAG:
                            if(pom.equals("Valute"))
                                arrCourses.add(course);
                            break;
                    }
                    xpp.next();
                }
                response_body.close();
                response_reader.close();
            }

        } catch (Exception e) { }
        return arrCourses;
    }

    static ArrayList<CourseParams> courses = new ArrayList<>();

    public static void courseDateParse(final Calendar caldata, final Context context, final CourseRunnable courseRunnable) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                ArrayList<CourseParams> today = courseDate(sdf.format(caldata));
                caldata.add(Calendar.DATE, -1);
                ArrayList<CourseParams> yesterday = courseDate(sdf.format(caldata));
                for (int i = 0; i < today.size(); i++) {
                    CourseParams c1 = today.get(i);
                    CourseParams c2 = yesterday.get(i);
                    c1.isSellUp = c1.Price > c2.Price;
                    c1.isBuyUp = c1.Price * c1.k > c2.Price * c2.k;
                }

                courses = today;
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        courseRunnable.run(courses);
                    }
                });
            } catch (Exception e) { }
            }
        });
    }

}
