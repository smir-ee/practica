package com.example.bezmetnyybank;

import com.example.bezmetnyybank.entities.ValuteEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class xml_parser {private boolean status = true;
    private ArrayList<ValuteEntity> entityValutes;

    public xml_parser(){
        entityValutes = new ArrayList<>();
    }

    public ArrayList<ValuteEntity> getEntityValutes() {
        return entityValutes;
    }

    public boolean Parsing(String XmlDoc){
        boolean isEntity = false;
        ValuteEntity entityValute = new ValuteEntity();
        String text = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(XmlDoc));

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT){
                switch (parser.getEventType()){
                    case XmlPullParser.START_TAG:
                        if ("Valute".equalsIgnoreCase(parser.getName())){
                            isEntity = true;
                            entityValute = new ValuteEntity();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (isEntity){
                            if ("Valute".equalsIgnoreCase(parser.getName())){
                                isEntity = false;
                                entityValutes.add(entityValute);
                            }
                            if ("NumCode".equalsIgnoreCase(parser.getName()))
                                entityValute.setNumCode(Integer.parseInt(text));
                            if ("CharCode".equalsIgnoreCase(parser.getName()))
                                entityValute.setCharCode(text);
                            if ("Nominal".equalsIgnoreCase(parser.getName()))
                                entityValute.setNominal(Integer.parseInt(text));
                            if ("Name".equalsIgnoreCase(parser.getName()))
                                entityValute.setName(text);
                            if ("Value".equalsIgnoreCase(parser.getName()))
                                entityValute.setValue(Double.parseDouble(text.replace(',','.')));
                            break;
                        }
                }
                parser.next();
            }
        }
        catch (XmlPullParserException | IOException e) { status = false; }
        return status;
    }
}
