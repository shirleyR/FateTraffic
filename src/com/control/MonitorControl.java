package com.control;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import com.db.DBConnect;
import com.model.*;
/**
 * Created by rsl_pc on 2016/4/11.
 */
public class MonitorControl {
    static final double EARTH_RADIUS = 6378.137;
    public static double rad(double  d){
        return d*Math.PI/180.0;
    }
    /**
     * distance between two monitors
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public static double[][] getDis()throws IOException{
        File inFile = new File("F:/MyEclipse Professional/Traffic/WebRoot/resource/cellsnew.csv");
        InputStreamReader isr = new InputStreamReader(new FileInputStream(inFile), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        // lon lat
        double monitor[][]=new double[57][2];
        double distance[][]=new double[57][57];
        int i=0;
        while(br.ready()){
            String line=br.readLine();
            String temp[]=line.split(",");
            if(i==0){i++;continue;}
            monitor[i-1][0]=Double.parseDouble(temp[1]);
            monitor[i-1][1]=Double.parseDouble(temp[2]);
            i++;
        }
        for(int j=0;j<56;j++){
            for(int k=j+1;k<56;k++){
                distance[j][k]=GetDistance(monitor[j][1],monitor[j][0],monitor[k][1],monitor[k][0]);
                distance[k][j]=distance[j][k];
            }
        }
        return distance;
    }
    public static List<Monitor> getMonitor(){
        DBConnect dbc=new DBConnect();
        try{

            CallableStatement cs = dbc.getConnection().prepareCall("{call proc_getMonitor(?)}");

            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet)cs.getObject(1);
            List<Monitor> list=new ArrayList<Monitor>();
            if(rs == null){
                System.out.println("null");
            }

            while(rs.next())
            {
                Monitor t=new Monitor();
                t.setMonitorid(rs.getString("monitor_id"));
                list.add(t);
            }

            cs.close();
            rs.close();
            return list;

        }catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }finally
        {
            dbc.close();
        }
    }
    public static List<Record> getExchange(){

        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();

        List<Record>record_list = new ArrayList<Record>();
        try {
            CallableStatement cs = conn.prepareCall("{ call proc_getCells(?) }");
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet)cs.getObject(1);

            if(rs == null){
                System.out.println("null");
            }
            while(rs.next())
            {
                Record record = new Record();
                record.setMonitorid(rs.getString("FROMMONITOR"));
                String begin=rs.getString("fromtime");
                String end=rs.getString("totime");
                long num=NextTime.getMinuTime(end,begin);
                record.setNum(num);
                record.settoMonitorid(rs.getString("TOMONITOR"));
                record_list.add(record);
            }

            cs.close();
            rs.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }finally
        {
            dbc.close();

        }

        return record_list;

    }
    
    public static List<Record> getTimeStatistic(){

        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();

        List<Record>record_list = new ArrayList<Record>();
        try {
            CallableStatement cs = conn.prepareCall("{ call proc_getCellsTime(?) }");
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet)cs.getObject(1);

            if(rs == null){
                System.out.println("null");
            }
            while(rs.next())
            {
                Record record = new Record();
                record.setNum(rs.getInt("nums"));
                record.setTimeslot(rs.getString("times"));
                record_list.add(record);
            }

            cs.close();
            rs.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }finally
        {
            dbc.close();

        }

        return record_list;

    }
    
    

    public  static void main(String args[]) throws  IOException{
        Map<String,Integer> monitormap=new  HashMap<String,Integer>();
        List<Monitor> list=new ArrayList<Monitor>();

        list=getMonitor();

        for(int i=0;i<list.size();i++)
        {
            monitormap.put(list.get(i).getMonitorid(), i);
        }
        List<Record> records=getExchange();
        double [][]distance=new double[57][57];
        distance=getDis();
        int count[][]=new int[57][57];
        for(int i=0;i<records.size();i++){
            int fromid=monitormap.get(records.get(i).getMonitorid());
            int toid=monitormap.get(records.get(i).gettoMonitorid());
            long num=records.get(i).getNum();
            if(num!=0){
                if(distance[fromid][toid]/(num/60.0)>120)
                count[fromid][toid]++;
            }
        }
        int n=0;
        for(int i=0;i<56;i++){
            for(int j=i+1;j<56;j++)
            n+=count[i][j];
            
        }
        System.out.println(n);
    }

}
