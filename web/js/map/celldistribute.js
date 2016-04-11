/**
 * Created by rsl_pc on 2016/4/11.
 */




stationmap=[];
lineSVG=[];
map._initPathRoot();

var LayStation=function(config){
    var svg=d3.select("#map").select("svg"),
        g=svg.append("g");
    ///////////////////////
    // map location data
    //  monitor_id,lat,lon,mid,dirtag
    ///////////////////////
    d3.csv("../resource/cellsnew.csv",function(mapdata){
        mapdata.forEach(function(d){
            d.monitor_id=+d.monitor_id;
            d.lon=+d.lon;
            d.lat=+d.lat;
            d.mid=+d.mid;
        });
        stationmap=mapdata;
        mapdata.forEach(function(d){
            d.LatLng = new L.LatLng(d.lat,d.lon);
        });

                var circledata=[];
  
                mapdata.forEach(function(ds,i){
                    var l=new L.LatLng(mapdata[i].lat,mapdata[i].lon);
                    circledata.push({"LatLng":l});
                });
                var radius=10;
                var path1 = d3.svg.line()
                    .x(function(d) { return map.latLngToLayerPoint(d).x; })
                    .y(function(d) { return map.latLngToLayerPoint(d).y; });
                var pie=d3.layout.pie()
                    .value(function(d){return d.topic; })
                    .sort(null);
                var arc=d3.svg.arc()
                    .innerRadius(radius*4/5)
                    .outerRadius(radius);

                var arrLine=[];
                var Line;
                var   circleSvg=g.selectAll(".circles")
                    .data(circledata)
                    .enter()
                    .append("circle")
                    .attr("class",function(d,i){return "circles c"+mapdata[i].monitor_id})
                    .attr("r",5)
                    .attr("fill",function(d){
                        return "green";})
                    .on("mouseover",function(d,i) {
                        d3.select(".tooltip")
                            .style("opacity", .9);

                        d3.select(".tooltip").html(mapdata[i].monitor_id)
                            .style("top", (d3.event.pageY + 16) + "px")
                            .style("left", (d3.event.pageX + 16) + "px");

                    })
                    .on("mouseout",function(){
                        d3.select(".tooltip") .style("opacity", 0);
                    });
                function update(){
                    var pie=d3.layout.pie()
                        .value(function(d){return d.topic; })
                        .sort(null);

                    var radius=10*((map.getZoom()-10)*0.1+1);

//
                    circleSvg.attr("cx",function(d) {
                        return map.latLngToLayerPoint(d.LatLng).x})
                    circleSvg.attr("cy",function(d) { return map.latLngToLayerPoint(d.LatLng).y})
                    circleSvg.attr("r",function(d) { return 5*((map.getZoom()-10)*0.1+1)});
                }
                map.on("viewreset", update);
                update();
       
        lineLayer = L.Class.extend({

            initialize: function () {
                return;
            },
            onAdd: function (map) {
                g.style("display", "block");
            },
            onRemove: function (map) {
                g.style("display", "none");
            },
        });
        railLineLayer = new lineLayer();
       
    });


     
     $.ajax({
    	 type:"post",
         url: "MonitorMatrix",
         data : {},
         dataType :'json',
         success : function(datas) {
        	 var nodes=[];
             stationmap.forEach(function(d){
            	 nodes.push({"name":d.monitor_id});
             });
             cellmatrix(datas,nodes);

		   var testData={"max":d3.max(datas.nodes,function(d){return d}) +0.1,"data":[]};
             stationmap.forEach(function(d,i){
            	    testData.data.push({"lat":+stationmap[i].lat,"lng":+stationmap[i].lon,"count":+datas.nodes[i]});
             })
            	heatmapLayer.setData(testData);
            	overlays={};
            	overlays["heat"]=heatmapLayer;
            	overlays["pattern"]=railLineLayer;
            	lays={};
				layss.removeFrom(map);
            	layss=L.control.layers(overlays).addTo(map);
         }
     })
};

LayStation();



