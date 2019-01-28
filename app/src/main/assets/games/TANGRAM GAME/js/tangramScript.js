var Tangram={};
var Utils = {};
Utils.Path='';
Utils.mobileDeviceFlag=false;
var shapes=[];
var questionId=0,startTime=0,scoredMarks=0,totalMarks=10,resId=0,label="";
Tangram.correctColorFilledObj=[];
Tangram.timer=0,Tangram.wrongAttempt=0;
Tangram.completeFlag=false;
Tangram.isGroupGame=true;
Tangram.objRotated=false;
Tangram.sevenShapeName=["largetriangle","rightangletriangle","square","meadiumtriangle","parallelogram","smalltriangle1","smalltriangle2"];
Tangram.colorArray=["#fd1800","#d3267f","#fe7430","#fde500","#387ddd","#eb5241","#6b13d0","#68b92e","#94780a","#c51d02","#c49777","#00d3a2","#09cbff"];
Tangram.helpFlag=false;

$(document).ready(function()
{
	$(".se-pre-con").fadeOut("slow");
	if(navigator.userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|Opera Mini|IEMobile/i))
		Utils.mobileDeviceFlag=true;
	else
		Utils.mobileDeviceFlag=false;

	setTimeout(function()
	{
		Tangram.init();
	},100);
});

Tangram.init=function()
{
	document.body.style.backgroundImage="url("+Utils.Path+"images/BG3.png)";
	$('#homeDiv').css('visibility','hidden');
	$('#logoDiv,#gamePage,#endPage,#instructionDiv,#homeAndLogoDiv').hide();
	$("#gameInstructionPage").hide();
	$("#coverPage").show();
	$("#videoTag").show();
	$('#videoTag').prop('src',Utils.Path+'images/tangramgame.mp4');
	document.getElementById('headerMsg').innerHTML=Tangram.hardcodeddata.headerMessage;
	document.getElementById('paraMsg').innerHTML=Tangram.hardcodeddata.paraInstruction;
	$('#playImg').attr('onclick','Tangram.playGame();');
	Tangram.setimages();
}

//set images
Tangram.setimages=function()
{
	document.getElementById('playImg').src=Utils.Path+"images/playButton.png";
	document.getElementById('showInstructionImg').src=Utils.Path+"images/playButton.png";
	document.getElementById('home').src=Utils.Path+"images/Home.png";
	document.getElementById('logo').src=Utils.Path+"images/logo.png";
	document.getElementById('next').src=Utils.Path+"images/nextButton.png";
	document.getElementById('helpImg').src=Utils.Path+"images/help.png";
	//document.getElementById('leftDiv').style.backgroundImage="url("+Utils.Path+"images/background7.jpg)";
   	document.getElementById('nextDiv').setAttribute('onclick','showLastPage()');
   //document.getElementById('rightDiv').style.backgroundImage="url("+Utils.Path+"images/background.jpg)";
}

Tangram.playGame=function()
{
	Tangram.wrongAttempt=0;
	Tangram.setTimer();
	var piecesCnt=7;
	$('#coverPage,#instructionDiv').hide();
	document.body.style.backgroundImage="url("+Utils.Path+"images/story6.png)";
	$('#logoImage,#gamePage,#currentDiv,#homeAndLogoDiv').show();
	document.getElementById("videoTag").pause();
	Tangram.piecesId=[];
	Tangram.piecesColor=[];
	Tangram.piecesId=Tangram.populate(Tangram.piecesId,piecesCnt,piecesCnt);//get unique and shuffled shapes
	Tangram.piecesColor=Tangram.populate(Tangram.piecesColor,piecesCnt,Tangram.colorArray.length); ////get unique and shuffled colors
	console.log(Tangram.piecesId);
	console.log(Tangram.colorArray);
	Tangram.setSVGImgShapeData();
	Tangram.addPieces(4,7,3,3);
}
Tangram.showCoverPage=function()
{
	$("#gameInstructionPage").hide();
	$("#coverPage").show();
	//$("#instructionDiv").show();
	//$('#instructionDiv').text(Tangram.hardcodeddata.startingInstruction);
}
Tangram.setTimer=function()
{
	document.getElementById("timerSpan").innerHTML=Tangram.timer+" s";
	Tangram.myTimer=setInterval(function()
	{
	Tangram.timer++;
	document.getElementById("timerSpan").innerHTML=Tangram.timer+" s";
	},1000);
}
//
Tangram.setSVGImgShapeData=function(){
	var SVG,children,i,d;
	d=new Date();
	startTime=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	//var randomIndex=Math.floor(Math.random()* Tangram.data.length);
	$("#svgDiv").html(Tangram.data[0].imgURL);	
	//$('.svgParts').css({"pointer-events":"all"});
	SVG = document.getElementById('svgDiv').getElementsByTagName('svg');
	children = SVG[0].children;
	i=0;
	[].forEach.call(children[0].children, function (child){
	    if(child.nodeType === 1 )//)
	    {
	        //child.setAttribute('class', "svgParts");
	        child.setAttribute("id","element"+i);
			child.setAttribute("onclick","Tangram.colorCurrentClickedSVGPart(this.id)");
			//child.style.fill="#000000";
			//$(child).css("stroke",'#FF0000');
			child.style.strokeWidth=32;
			i++;
	     }
	});
	$('.svgParts').css({"pointer-events":"all"});
}

Tangram.colorCurrentClickedSVGPart=function(obj){

	Tangram.reqiredShapeRotation=(document.getElementById(obj).getAttribute('shaperotation').split(',')).map(Number);
	Tangram.actualColor=$('#'+obj).data('colorToSet').split(',');
	console.log(Tangram.reqiredShapeRotation+' '+Tangram.currentShapeRotation);

	//console.log($('#'+obj).data('colorToSet')+' '+Tangram.selectedColor);
	if((Tangram.objRotated) && (Tangram.actualColor[0]==Tangram.selectedColor || Tangram.actualColor[1]==Tangram.selectedColor) &&
	((Tangram.currentShapeRotation>=Tangram.reqiredShapeRotation[0]-10) &&((Tangram.currentShapeRotation<=Tangram.reqiredShapeRotation[0]+10)))
	||((Tangram.currentShapeRotation>=Tangram.reqiredShapeRotation[1]-10) &&((Tangram.currentShapeRotation<=Tangram.reqiredShapeRotation[1]+10)))
	||((Tangram.currentShapeRotation>=Tangram.reqiredShapeRotation[2]-10) &&((Tangram.currentShapeRotation<=Tangram.reqiredShapeRotation[2]+10)))
	||((Tangram.currentShapeRotation>=Tangram.reqiredShapeRotation[3]-10) &&((Tangram.currentShapeRotation<=Tangram.reqiredShapeRotation[3]+10)))
	)
	{
		if(($.inArray(Tangram.currentShape,Tangram.correctColorFilledObj) === -1)&&($.inArray(obj,Tangram.correctColorFilledObj) === -1))
		{	//Tangram.correctColorFilledObj.push(obj);
			Tangram.correctColorFilledObj.push(Tangram.currentDiv.children()[0].children[0].id);
			Tangram.correctColorFilledObj.push(obj);
			document.getElementById(obj).style.fill=Tangram.selectedColor;
			document.getElementById(obj).style.stroke='#000000';
			$(Tangram.currentDiv).css('opacity','0.6');
			$(Tangram.currentDiv).css("visibility","hidden");
			var id1=Tangram.currentDiv.prop('id');
			document.getElementById('playSound').src=Utils.Path+"sounds/Buzzercorrect1.mp3";
			document.getElementById('playSound').play();
			$("#currentDiv").empty();
			$('#'+Tangram.currentDiv.children()[0].children[0].id).css('pointer-events','none');
			$("#"+id1).css("visibility","hidden");
			Tangram.objRotated=false;
		}


		//alert('Correct Filled');	
	}
	else
		Tangram.wrongAttempt++;

	if(Tangram.correctColorFilledObj.length==Tangram.piecesId.length*2)
	{
		scoredMarks=10;
		clearInterval(Tangram.myTimer);	
		Tangram.completeFlag=true;
		$("#helpDiv").css("visibility","hidden");
	}
	
	//alert("Picture Completed"+" Wrong"+Tangram.wrongAttempt);
	//$('#obj').css("fill",Tangram.selectedColor);

}

function showLastPage()
{
 

    document.getElementById('gamePage').style.display='none';
	document.getElementById('nextImg').src=Utils.Path+"images/nextButton.png";
	document.getElementById('nextImg').setAttribute("onclick","Tangram.playNextGame()");
    document.getElementById('endPage').style.display='block';
    document.getElementById('timerDiv').style.visibility='hidden';

    if(Tangram.completeFlag)
		document.getElementById('status').innerHTML=Tangram.hardcodeddata.puzzleComplete;
	else
		document.getElementById('status').innerHTML=Tangram.hardcodeddata.puzzleIncomplete;
    document.getElementById('wrong').innerHTML=Tangram.hardcodeddata.wrongMsg+Tangram.wrongAttempt;
    document.getElementById('time').innerHTML=Tangram.hardcodeddata.timeMsg+Tangram.timer + " sec"; 
	console.log(totalMarks,scoredMarks,Tangram.helpFlag,Tangram.timer,startTime,label,Tangram.isGroupGame);
	if(Utils.mobileDeviceFlag)
		Android.addScore(totalMarks,scoredMarks,Tangram.helpFlag,Tangram.timer,startTime,label,Tangram.isGroupGame);
}
Tangram.populate=function(piecesId,totalPieces,upperLimit)//(array1,noofpieces)
{
	var randomIndex,min=0;
	while (piecesId.length!==totalPieces) 
	{
		randomIndex = Math.floor(Math.random()* upperLimit)+min;
		if($.inArray(randomIndex,piecesId) === -1)
		piecesId.push(randomIndex);
	}
	return piecesId;
}
Tangram.goToHome=function()
{
	location.reload();
}

//add 7 pieces to rightDiv
Tangram.addPieces=function(colsize,piecesCnt,rowCnt,noOfPiecesInRow)
{	
	var i=0,subDiv,shapeData,index;
	while(i<piecesCnt)
	{
		subDiv = document.createElement("div");
		subDiv.setAttribute("id","subDiv"+i);
		subDiv.className = "pieces col-xs-"+colsize+" col-sm-"+colsize+" col-md-"+colsize+" col-lg-"+colsize+" noPadding";
		$(subDiv).html(Tangram.sevenPieces[Tangram.piecesId[i]].imgURL);	
		$('#rightDiv').append(subDiv);		
		i++;
	}
	for(i=0;i<piecesCnt;i++)
	{
		document.getElementById('Shape'+i).setAttribute('class','sevenPiece');
		shapeData=document.getElementById("element"+i).getAttribute('shapedata');
		index=$.inArray(shapeData,Tangram.sevenShapeName);
		//console.log(index);
		if(index!==-1)
		{
			$('.sevenPiece').css({"pointer-events":"all"});
			document.getElementById('Shape'+index).style.fill=Tangram.colorArray[Tangram.piecesColor[i]];		
			$("#element"+i).css("stroke",'#303030');
			$("#element"+i).css("fill",'#303030');
			//$("#element"+i).css("stroke",Tangram.colorArray[Tangram.piecesColor[i]]);	
			
			$("#element"+i).data('colorToSet',Tangram.colorArray[Tangram.piecesColor[i]]);
			$("#Shape"+index).data('colorCode',Tangram.colorArray[Tangram.piecesColor[i]]);
			
			if(document.getElementById("element"+i).getAttribute('shapedata')=='smalltriangle1')
			{	var color1=Tangram.colorArray[Tangram.piecesColor[i]];
				//console.log('aa');
			}
			if(document.getElementById("element"+i).getAttribute('shapedata')=='smalltriangle2')
			{	var color2=Tangram.colorArray[Tangram.piecesColor[i]];
				//console.log('aa');
			}
			if(document.getElementById("element"+i).getAttribute('shapedata')=='largetriangle')
			{	var color3=Tangram.colorArray[Tangram.piecesColor[i]];
				//console.log('aa');
			}
			if(document.getElementById("element"+i).getAttribute('shapedata')=='rightangletriangle')
			{	var color4=Tangram.colorArray[Tangram.piecesColor[i]];
				//console.log('aa');
			}
		}	
		$('#Shape'+i).on("click", function() {
  			Tangram.getCurrentClickObj(this);
   		 });
		$('#Shape'+i).on("touchstart", function() {
  			Tangram.getCurrentClickObj(this);
   		 });
	}
	for(i=0;i<piecesCnt;i++)
		{
			if(document.getElementById("element"+i).getAttribute('shapedata')=='smalltriangle1')
			{
				//console.log($("#element"+i).data('colorToSet')+','+color2);
				var c1=$("#element"+i).data('colorToSet')+','+color2;
				$("#element"+i).data('colorToSet',c1);
			}

			if(document.getElementById("element"+i).getAttribute('shapedata')=='smalltriangle2')
			{
				var c2=$("#element"+i).data('colorToSet')+','+color1;
			 	$("#element"+i).data('colorToSet',c2);
			 	//console.log($("#element"+i).data('colorToSet')+','+color1);
			}

			if(document.getElementById("element"+i).getAttribute('shapedata')=='largetriangle')
			{
				//console.log($("#element"+i).data('colorToSet')+','+color2);
				var c1=$("#element"+i).data('colorToSet')+','+color4;
				$("#element"+i).data('colorToSet',c1);
			}

			if(document.getElementById("element"+i).getAttribute('shapedata')=='rightangletriangle')
			{
				var c2=$("#element"+i).data('colorToSet')+','+color3;
			 	$("#element"+i).data('colorToSet',c2);
			 	//console.log($("#element"+i).data('colorToSet')+','+color1);
			}
		}
}
// get Current Click obj of leftDiv SVG
Tangram.getCurrentClickObj=function(obj){
	var div,clone,currentShapeId,selectedDiv,img,offset,mouseDown;
	$('.rotateDiv').removeClass('rotateDiv');
	//$('.pieces').addClass='sevenPiece';
	$('.sevenPiece').css({"pointer-events":"all"});
	$("#currentDiv").empty();
	Tangram.selectedColor=$(obj).data('colorCode');
	div = document.getElementById($(obj).parent().parent().attr('id'));
    clone = div.cloneNode(true); // true means clone all childNodes and all event handlers
	clone.id = "selectedObj";
	$(clone).css({"height":"90%","width":"90%","transform":""});
	$("#currentDiv").append(clone);
	document.getElementById('playSound').src=Utils.Path+"sounds/Bell.mp3";
	document.getElementById('playSound').play();

	selectedDiv=$(obj).parent().parent().attr('id');
	$('.rotateDiv').css("z-index","10");
	$('#'+selectedDiv).addClass('rotateDiv');
	currentShapeId=$(obj).attr('id');
	//document.getElementById(currentShapeId).setAttribute('class','rotateDiv');
	//Tangram.currentShapeRotation=0;
		img =$('#'+selectedDiv);
		Tangram.currentShape=$('#'+selectedDiv).children().children()[0].id;
		offset = img.offset();
		mouseDown = false;
		function mouse(evt) {
		    if(mouseDown ==true){
		    var center_x = (offset.left) + (img.width() / 2);
		    var center_y = (offset.top) + (img.height() / 2);
		    var mouse_x = evt.pageX;
		    var mouse_y = evt.pageY;
		    var radians = Math.atan2(mouse_x - center_x, mouse_y - center_y);
		    var degree = (radians * (180 / Math.PI) * -1) + 90;
		    img.css('-moz-transform', 'rotate(' + degree + 'deg)');
		    img.css('-webkit-transform', 'rotate(' + degree + 'deg)');
		    img.css('-o-transform', 'rotate(' + degree + 'deg)');
		    img.css('-ms-transform', 'rotate(' + degree + 'deg)');
		    Tangram.currentShapeRotation=degree;
		    Tangram.objRotated=true;
		    Tangram.currentDiv=$('#'+selectedDiv);
		    }
		}
		img.mousedown(function (e) {
		   		mouseDown=true;
		    	$(document).mousemove(mouse);

		});
		$(document).mouseup(function (e) {
			$('#'+selectedDiv).css("z-index","1");
			$('#rightDiv>*').not(".rotateDiv").css("z-index","10");
		    mouseDown = false;		     
		});
	
		mouseDown = false;
		div=$(img).attr('id');
		document.getElementById(div).addEventListener("touchstart",function(e){
		 // var touchobj = e.changedTouches[0];
		 mouseDown=true;
		 e.preventDefault();
		});	
		document.getElementById(div).addEventListener("touchend",function(e){
			mouseDown=false;
			e.preventDefault();
		});
		document.getElementById(div).addEventListener("touchmove",function(e){
			mouseDown=true;
			Tangram.touchobj = e.changedTouches[0];
			move(e);
			function move(evt){
				//$('.rotateDiv').removeClass('rotateDiv');
				offset = img.offset();
			    if(mouseDown ==true){
			    var center_x = (offset.left) + (img.width() / 2);
			    var center_y = (offset.top) + (img.height() / 2);
			    var mouse_x = Tangram.touchobj.pageX;
			    var mouse_y = Tangram.touchobj.pageY;
			    var radians = Math.atan2(mouse_x - center_x, mouse_y - center_y);
			    var degree = (radians * (180 / Math.PI) * -1) + 180;
			    //degree=degree-20;
			    img.css('-moz-transform', 'rotate(' + degree + 'deg)');
			    img.css('-webkit-transform', 'rotate(' + degree + 'deg)');
			    img.css('-o-transform', 'rotate(' + degree + 'deg)');
			    img.css('-ms-transform', 'rotate(' + degree + 'deg)');
			    Tangram.currentShapeRotation=degree;
			    Tangram.objRotated=true;
			     Tangram.currentDiv=$('#'+selectedDiv);
			    }
			}		
		});
}

Tangram.helpTaken=function()
{
	Tangram.helpFlag=true;
}

Tangram.playNextGame=function()
{
	if(Utils.mobileDeviceFlag)
		Android.playNextGame();
}