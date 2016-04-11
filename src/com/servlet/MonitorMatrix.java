package com.servlet;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.control.*;
import com.model.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class MonitorMatrix extends HttpServlet {


	public MonitorMatrix() {
		super();
	}


	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

	
		JSONObject jsonObj  = new JSONObject();
		 Map<String,Integer> monitormap=new  HashMap<String,Integer>();
	        List<Monitor> list=new ArrayList<Monitor>();

	        list=MonitorControl.getMonitor();

	        for(int i=0;i<list.size();i++)
	        {
	            monitormap.put(list.get(i).getMonitorid(), i);
	        }
	        List<Record> records=MonitorControl.getExchange();
	        double [][]distance=new double[57][57];
	        distance=MonitorControl.getDis();
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
	        
		JSONArray jsonarray=new JSONArray();
		int counts[]=new int[56];
		for(int i=0;i<56;i++){
			for(int j=0;j<56;j++){
				//sources ,target ,value
				JSONObject obj=new JSONObject();
				obj.put("source", i);
				obj.put("target", j);
				obj.put("value", count[i][j]);
				counts[i]+=count[i][j];
				jsonarray.add(obj);
			}
		}
		jsonObj.put("nodes", counts);
		jsonObj.put("links", jsonarray);
		try{
			out.println(jsonObj.toString());

		}catch(Exception ex){
			ex.printStackTrace();

		}
		out.flush();
		out.close();  
	}
	public void init() throws ServletException {
	
	}

}

