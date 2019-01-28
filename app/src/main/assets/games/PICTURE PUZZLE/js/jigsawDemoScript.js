Jigsaw={};
var Utils={};
Utils.Path='';
Utils.mobileDeviceFlag=false;
var context,img,newImg=0,attemptedCnt=0;
Jigsaw.arr=[];
Jigsaw.positionArray=[];
var numColsToCut=5,numRowsToCut=5,wrongAttempts=0,totalMarks=10,scoredMarks=0,label="",startTime=0;
Jigsaw.timer=0;
Jigsaw.helpFlag=false;
Jigsaw.isGroupGame=true;
$(document).ready(function()
{
	$(".se-pre-con").fadeOut("slow");
	if(navigator.userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|Opera Mini|IEMobile/i))
		Utils.mobileDeviceFlag=true;
	setTimeout(function()
	{
		document.body.style.backgroundImage="url("+Utils.Path+Jigsaw.coverImage.imgname+")";
		document.getElementById('coverpage').style.display="block";
		//document.getElementById('instructionDiv').style.display="block";
		document.getElementById('widgetDiv').style.display="none";
		document.getElementById('gamepage').style.display="none";
		document.getElementById('endPage').style.display="none";
		//document.getElementById("home").style.visibility = "hidden";
		//document.getElementById("instructionDiv").innerHTML=Jigsaw.instruction;
		Jigsaw.setImage();
	},300);
});
Jigsaw.setImage=function()
{
	//document.getElementById('logo').src=Utils.Path+"images/logo.png";
	document.getElementById('play').src=Utils.Path+"images/Play.png";
	//document.getElementById('home').src=Utils.Path+"images/Home.png";
	document.getElementById('nextImg').src=Utils.Path+"images/Next.png";
	document.getElementById('playNextGameImg').src=Utils.Path+"images/Next.png";
	document.getElementById('helpImg').src=Utils.Path+"images/help.png";
	document.getElementById("videoTag").src=Utils.Path+"sounds/Sample.mp4";
}
Jigsaw.playGame=function()
{
	document.getElementById('coverpage').style.display="none";
	document.getElementById('gamepage').style.display="block";
	document.getElementById('refImgDiv').style.display="block";
	document.getElementById('widgetDiv').style.display="block";
	document.getElementById("jigsawDiv").style.display="block";
	document.getElementById("header").style.display="block";
	document.getElementById("timerDiv").style.visibility = "visible";
	document.getElementById("videoTag").pause();
	Jigsaw.setTimer();
	Jigsaw.createImageParts();
}
Jigsaw.setTimer=function()
{
	var d=new Date();
	startTime=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	document.getElementById("timer").innerHTML=Jigsaw.timer+"s";
	Jigsaw.myTimer=setInterval(function()
	{
		Jigsaw.timer++;
		document.getElementById("timer").innerHTML=Jigsaw.timer+"s";
	},1000);
}
Jigsaw.createImageParts=function()
{
	var canvas;
	document.getElementById("jigsawDiv").innerHTML="";
	canvas=document.getElementById("myCanvas1");
	context=canvas.getContext("2d");
	img=new Image();
	img.onload=Jigsaw.drawImg;
	img.src=Utils.Path+"images/Cover.png";
}
Jigsaw.drawImg=function()
{
	var jigsawDiv,index=0,ans=1;
	document.getElementById("jigsawDiv").innerHTML="";
	var imgX=0,imgY=0,imgHeight=Math.floor(img.width/numColsToCut),imgWidth=Math.floor(img.height/numColsToCut),dx=0,dy=0,dheight=100,dwidth=100,temp=Math.floor(12/numColsToCut);
	var subCanvas,subContext;
	Jigsaw.arr=[];
	refWidth=document.getElementById("myCanvas1").width;
	refHeight=document.getElementById("myCanvas1").height;
	
	context.drawImage(img,0,0,refWidth,refHeight);
	jigsawDiv=document.getElementById("jigsawDiv");
	
    for(var x = 0; x < numColsToCut; ++x)
	{
		var outerDiv,mainDiv,div;
		outerDiv=document.createElement("div");
		outerDiv.id="outerDiv"+x;
		outerDiv.className="outerDivClass col-sm-12 col-lg-12 col-md-12 col-xs-12 noPadding";
		outerDiv.style.height=(100/numRowsToCut)+"%";
		jigsawDiv.append(outerDiv);
		for(var y = 0; y < numColsToCut; ++y)
		{
			mainDiv=document.createElement('div');
			mainDiv.id="mainDiv"+x+""+y;
			mainDiv.className=" noPadding ";
			mainDiv.style.width=(100/numRowsToCut)+"%";
			div=document.createElement('div');
			div.id="div"+x+""+y;
			div.className="dragClass noPadding ";
			
			this.subCanvas=document.createElement("canvas");
			this.subContext=this.subCanvas.getContext("2d");
			this.subCanvas.className="jigsawCanvas";
			$(div).append(this.subCanvas);
			this.subCanvas.id=x+""+y;
			dheight=this.subCanvas.height;
			dwidth=this.subCanvas.width;
			this.subContext.drawImage(img,imgX,imgY,imgHeight,imgWidth,0,0,dwidth,dheight );
			imgX=imgX+imgWidth;
			$(div).data("value",ans);
			$(mainDiv).data("value",ans);
			ans++;
			Jigsaw.arr.push(div);
			$("#outerDiv"+x).append(mainDiv);
		}
		dx=0;
		imgX=0;
		imgY=imgY+imgHeight;
	}
	shuffleArray(Jigsaw.arr);
	
	for(var x = 0; x < numRowsToCut; ++x)
	{
		for(var y = 0; y < numColsToCut; ++y)
		{
			$("#mainDiv"+x+""+y).droppable({drop :Jigsaw.onDrop,dragover:allowDrop,});
			$("#mainDiv"+x+""+y).append(Jigsaw.arr[index]);
			index++;
		}
	}
	for(var i=0;i<Jigsaw.arr.length;i++)
	{
		if( $(Jigsaw.arr[i]).data("value")==$(Jigsaw.arr[i]).parent().data("value"))
			Jigsaw.positionArray.push($(Jigsaw.arr[i]).data("value"));
	}
	 $('.dragClass').draggable({
		zIndex:900,
		revert:"invalid"
		});
	console.log(Jigsaw.positionArray);
}
Jigsaw.onDrop=function(event,ui)
{
	var src,srcDiv,dst,dstDiv,heightOfParent=0;
	src=ui.draggable;
	srcDiv=$(src).parent();
	dstDiv=$(this);
	dst=$(dstDiv).children();
	$(dstDiv).append(src);
	$(srcDiv).append(dst);
	$(src).width('100%');
	$(src).css({'height':'100%','top':'0px','left':'0px'});
	Jigsaw.checkAnswer(src,srcDiv,dst,dstDiv);
	if(Jigsaw.positionArray.length==Jigsaw.arr.length)
	{
		clearInterval(Jigsaw.myTimer);
		scoredMarks=10;
		$('.dragClass').draggable('disable');
		document.getElementById('helpDiv').style.visibility="hidden";
		$("#jigsawDiv").removeClass("animated fadeInDown");
		$("#jigsawDiv").addClass("animated zoomIn");
		document.getElementById("playSound").src=Utils.Path+"sounds/thats_right.mp3";
		document.getElementById("playSound").play();
	}
}
Jigsaw.checkAnswer=function(src,srcDiv,dst,dstDiv)
{
	var srcData=0,srcDivData=0,dstData=0,dstDivData=0;
	srcData=$(src).data("value");
	srcDivData=$(srcDiv).data("value");
	dstData=$(dst).data("value");
	dstDivData=$(dstDiv).data("value");
	
	if(srcData==dstDivData)
	{
		if(!Jigsaw.positionArray.includes(srcData))
			Jigsaw.positionArray.push(srcData);
	}
	else
	{
		if(Jigsaw.positionArray.includes(srcData))
			Jigsaw.positionArray.splice(Jigsaw.positionArray.indexOf(srcData),1);
		wrongAttempts++;
	}
	if(srcDivData==dstData)
	{
		if(!Jigsaw.positionArray.includes(srcDivData))
			Jigsaw.positionArray.push(srcDivData);
	}
	else
	{
		if(Jigsaw.positionArray.includes(srcDivData))
			Jigsaw.positionArray.splice(Jigsaw.positionArray.indexOf(srcDivData),1);
	}
	console.log(wrongAttempts);
}
function allowDrop(event)
{
	event.preventDefault();
}
Jigsaw.helpTaken=function()
{
	Jigsaw.helpFlag=true;
}
Jigsaw.endGame=function()
{
	document.getElementById("playSound").pause();
	document.getElementById('endPage').style.display="block";
	document.getElementById('timerDiv').style.visibility="hidden";
	document.getElementById('widgetDiv').style.display="none";
	document.getElementById('coverpage').style.display="none";
	document.getElementById('gamepage').style.display="none";
	document.getElementById('wrong').innerHTML=Jigsaw.message[0]+wrongAttempts;
	document.getElementById('time').innerHTML=Jigsaw.message[1]+Jigsaw.timer + " sec"; 
	if(Jigsaw.positionArray.length==Jigsaw.arr.length)
	{
		Jigsaw.positionArray=[];
		document.getElementById('puzzleComplete').innerHTML=Jigsaw.message[2];
	}
	else
		document.getElementById('puzzleComplete').innerHTML=Jigsaw.message[3];
	console.log(totalMarks, scoredMarks,Jigsaw.helpFlag,Jigsaw.timer,startTime,label,Jigsaw.isGroupGame);
	if(Utils.mobileDeviceFlag)
		Android.addScore(totalMarks, scoredMarks,Jigsaw.helpFlag,Jigsaw.timer,startTime,label,Jigsaw.isGroupGame);
}
Jigsaw.playNextGame=function()
{
	if(Utils.mobileDeviceFlag)
		Android.playNextGame();
}
