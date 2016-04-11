/**
 * Created by admin on 2016/4/10.
 */

$.ajax({
   	 	type:"get",
        url: "TimeStatistic",
        data :{},
        dataType :'json',
        success : function(data) {
        	drawTimeline(data);
        }
	});
function drawTimeline(data){
	
    var margin = {top: 10, right: 10, bottom: 30, left: 50},
        areaChartWidth = $(".timeline").width()- margin.left - margin.right,
        areaChartHeight =$(".timeline").height()- margin.top - margin.bottom;

    var x = d3.time.scale().range([0, areaChartWidth]);
    var y = d3.scale.linear().range([areaChartHeight, 0]);

    var parseDate = d3.time.format("%Y-%m-%d %H").parse;
    var xA=d3.scale.linear().range([0, areaChartWidth])
  
    var xZ=d3.scale.ordinal().rangePoints([0, areaChartWidth])
    var sets=[];
    data.forEach(function(d){
		d.times=parseDate(d.times);
	});
    x.domain(d3.extent(data,function(d){return d.times;}));
    y.domain([0,d3.max(data,function(d){return d.nums;})]);
    
    
    for(var i=0;i<29;i++)
        sets.push(i*6);
    xZ.domain(sets);
    xA.domain([0,168]);
    //.domain([parseDate("2015-09-07 00"),parseDate("2015-09-13 23")]);
  
    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")
//        .ticks(168)//.tickSubdivide(true)
//        .tickFormat("");
    var xAxis2 = d3.svg.axis()
        .scale(xZ)
        .orient("bottom")
        //	.ticks(14)
        .tickFormat(function(d){
            if(d%24==0) return 0;
            else if(d%24==6)return 6;
            else return d%24==12?12:18;

        }).tickSize(16, function(d, i) {
            return ((( d % 24 == 6)||( d % 24 == 18)) ?  9: 16);
        }, -4);


    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")
        .ticks(4);
    var main_line0 = d3.svg.line()
        .x(function(d) { return x(d.times); })
        .y(function(d) { return y(d.nums); });


    var timelineSvg = d3.select(".timeline").append("svg")
        .attr("width", areaChartWidth + margin.left + margin.right)
        .attr("height", areaChartHeight + margin.top + margin.bottom)
        .attr("class","areaChart")
    timelineSvg.append("defs").append("clipPath")
        .attr("id", "clip")
        .append("rect")
        .attr("width", areaChartWidth)
        .attr("height", areaChartHeight);
    var main=timelineSvg.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    var brush=d3.svg.brush()
        .x(x)
        .on("brush",brush);
    function brush(){
        if(!brush.empty()){
            timebrush.start=Math.floor(brush.extent()[0]);
            timebrush.end=Math.floor(brush.extent()[1]);
        }else
        {
            timebrush.start=1;
            timebrush.end=168;
        }
    }
        main.append("path")
            .datum(data)
            .attr("class", "line lines")
            .attr("stroke",function(){return "#D39651";})
            .attr("fill","none")
            .attr("stroke-width","1.5px")
            .attr("d", main_line0)
//            .on("mouseover",function(d){
//                d3.select(".tooltip")
//                    .style("opacity", .9);
//
//                d3.select(this).attr("stroke-width","3px")
//                //     .style("opacity", 1);
//                this.parentNode.appendChild(this);
//                d3.select(".tooltip").html("pattern"+(dims+1))
//                    .style("left", (d3.event.pageX) + "px")
//                    .style("top", (d3.event.pageY - 28) + "px");
//            })
//            .on("mouseout", function(d) {
////	    	  d3.selectAll(".line")
////	    	     .style("opacity", 1);
//                d3.select(this).attr("stroke-width","1.5px");
//                d3.select(".tooltip")
//                    .style("opacity", 0);
//            });




    main.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + areaChartHeight + ")")
        .call(xAxis)

//    main.append("g")
//        .attr("class", "x axis")
//        .attr("transform", "translate(0," + areaChartHeight + ")")
//        .call(xAxis2)
//   	 .append("text")
//   	 .attr("y", 9)
//   	 .attr("x", 39)
//   	 .attr("dy", ".71em")
//   	 .style("text-anchor", "end")
//   	 .text("Hour");
    main.append("g")
        .attr("class", "y axis")
        .call(yAxis)
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .text("Value");
    main.append("g")
        .attr("class", "x brush")
        .call(brush)
        .selectAll("rect")
        .attr("y", -6)
        .attr("height", areaChartHeight + 7);
}
