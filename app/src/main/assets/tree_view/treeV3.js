var w=document.body.clientWidth;
var h=document.body.clientHeight;

function setTree(object){
var canvas= d3.select("svg")
            .attr("width",w)
            .attr("height",h)
            .append("g")
            .attr("class","item1")
            .attr("transform","translate(50,50)");

var diagonal = d3.svg.diagonal()
                    .projection(function(d){return [d.y,d.x];});

var tree = d3.layout.tree()
            .size([h-50*2,w-50*2]);

var nodes = tree.nodes(object);
var roots=nodes[0];
var links = tree.links(nodes);

var node = canvas.selectAll(".node")
                    .data(nodes)
                    .enter()
                    .append("g")
                    .attr("class","node")
                    .attr("transform",function(d){ return "translate("+d.y+","+d.x+")";});
    
var root=node.filter(function(d,i){return i==0;});
    
node.append("circle")
        .attr("r",5)
        .attr("fill","black");

node.append("text")
    .text(function(d){return d.name;})
    .attr("transform","translate(0,-10)");

var link = canvas.selectAll(".link")
    .data(links)
    .enter()
    .append("path")
    .attr("class","link")
    .attr("fill","none")
    .attr("stroke","black")
    .attr("d",diagonal);
    
 node.on("click",function(e){

     var obj2=JSON.parse(Android.getNode(e.id));

     //region exit
     node.transition().duration(2500)
        .attr("transform","translate("+roots.y+","+roots.x+")");
     link.transition().duration(2500)
        .attr("d",diagonal.projection(function(d){return [roots.y,roots.x];}));
     d3.selectAll("text").transition().style("opacity","0");
     d3.select(".item1").transition().delay(2500).remove();
     //endregion
     //region enter
     var transitionPosition={x:e.x,y:e.y};

     setTree2(obj2,transitionPosition);
     //endregion
     //region next page
     setTimeout(function(){
     var url = new URL(window.location.href);
     var path=url.pathname;
     window.location.href=path+"?"+obj2.id;
     },2500);
     //endregion
});

root.on("click",function(e){
    var objAncestor= JSON.parse(Android.getAncestorNode(parseInt(e.id)));

    var tree = d3.layout.tree().size([h-50*2,w-50*2]);
    var nodes = tree.nodes(objAncestor);
    var links = tree.links(nodes);
    var canvas = d3.select("svg").append("g")
                                .attr("transform","translate(50,50)");
    
    var rootID=e.id;
    var rootIdNode=nodes.filter(function(e){return e.id==rootID;})[0];
    
    var orgNode=d3.select(".item1").selectAll(".node");
    orgNode.transition().duration(2500).attr("transform","translate("+rootIdNode.y+","+rootIdNode.x+")");
    var orgLink=d3.select(".item1").selectAll(".link");
        orgLink.transition().duration(2500).attr("d",diagonal.projection(function(d){return [rootIdNode.y,rootIdNode.x];}));
    orgNode.selectAll("text").transition().style("opacity","0");
    d3.select(".item1").transition().delay(2500).remove();
    
    var node = canvas.selectAll(".node")
                    .data(nodes)
                    .enter()
                    .append("g")
                    .attr("class","node");
    
    var nRoots = nodes[0];
        node.attr("transform","translate("+nRoots.y+","+nRoots.x+")");
    
    node.append("circle")
        .attr("r",5)
        .attr("fill","black");
    node.append("text")
        .text(function(d){return d.name})
        .attr("transform","translate(0,-10)");
    
    node.transition().duration(2500).attr("transform",function(d){return "translate("+d.y+","+d.x+")";});
            
    var link=canvas.selectAll(".link")
                    .data(links)
                    .enter()
                    .append("path")
                    .attr("class","link");
    
    var nDiagonal=d3.svg.diagonal();
    
    link.attr("d",nDiagonal.projection(function(d){return [nRoots.y,nRoots.x];}));
    link.attr("fill","none")
        .attr("stroke","black");
    link.transition().duration(2500).attr("d",nDiagonal.projection(function(d){return [d.y,d.x];}))
    //region next page
         setTimeout(function(){
         var url = new URL(window.location.href);
         var path=url.pathname;
         window.location.href=path+"?"+objAncestor.id;
         },2500);
         //endregion
});

};

function setTree2(data,lastPos){
var canvas = d3.select("svg").append("g")
                             .attr("transform","translate(50,50)");
var diagonal = d3.svg.diagonal()
                    .projection(function(d){return [d.y,d.x];});
    
var tree = d3.layout.tree().size([h-50*2,w-50*2]);
var nodes = tree.nodes(data);
var links = tree.links(nodes);
var node = canvas.selectAll(".node")
                .data(nodes)
                .enter()
                .append("g")
                .attr("class","node")
                .attr("transform",function(d){return "translate("+lastPos.y+","+lastPos.x+")"});
node.append("circle")
        .attr("r",5)
        .attr("fill","black");
node.append("text")
    .text(function(d){return d.name;})
    .attr("transform","translate(0,-10)");
    
var link = canvas.selectAll(".link")
                    .data(links)
                    .enter()
                    .append("path")
                    .attr("class","link")
                    .attr("fill","none")
                    .attr("stroke","black")
                    .attr("d",diagonal.projection(function(d){return [lastPos.y,lastPos.x];}));
    
node.transition().duration(2500)
                .attr("transform",function(d){return "translate("+d.y+","+d.x+")";});
link.transition().duration(2500)
                .attr("d",diagonal.projection(function(d){return [d.y,d.x];}));
};

var gname;

function main(){
var url = new URL(window.location.href);
var params=parseInt(url.search.slice(1));
gname=params;
var current = JSON.parse(Android.getNode(params));
console.log(current);
setTree(current);
}

function getgname(){return Android.waitForCurrentNodeId(gname);}