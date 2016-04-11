
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


public class TimeStatistic extends HttpServlet {


	public TimeStatistic() {
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

		List<Record> list=new ArrayList<Record>();

		list=MonitorControl.getTimeStatistic();
		JSONArray jarray=new JSONArray();
		for(int i=0;i<list.size();i++)
		{
			JSONObject t=new JSONObject();
			t.put("times", list.get(i).getTimeslot());
			t.put("nums", list.get(i).getNum());
			jarray.add(t);
			t.clear();
		}
		try{
			out.println(jarray.toString());

		}catch(Exception ex){
			ex.printStackTrace();

		}
		out.flush();
		out.close();  
	}
	public void init() throws ServletException {

	}

}

