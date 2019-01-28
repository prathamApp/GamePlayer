var FindIt={};
var Utils={};
Utils.Path='';
Utils.mobileDeviceFlag=false;
FindIt.time=0;
FindIt.correctAttempt=0;
FindIt.wrongAttempt=0;
FindIt.correctAns=0;
totalMarks=100,scoredMarks=0,isHelpTaken=false,timeTaken=0,startTime=0,label="";
FindIt.isGroupGame=false;
$(document).ready(function()
{
	$(".se-pre-con").fadeOut("slow");
	if(navigator.userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|Opera Mini|IEMobile/i))
		Utils.mobileDeviceFlag=true;
	setTimeout(function(){
	//document.getElementById('instructionDiv').style.display="block ";
	document.body.style.backgroundImage="url("+Utils.Path+'images/BG.png'+")";
	document.getElementById('coverpage').style.display="block";
	document.getElementById('gamepage').style.display="none";
	document.getElementById('endPage').style.display='none';
	//document.getElementById('nextDiv').style.display='none';
	//document.getElementById('instructionDiv').innerHTML=FindIt.hardcorded[1];
	document.getElementById('videoTag').src=Utils.Path+'images/Sample.mp4';
	FindIt.setImage();
},200);
});
//playGame called on play btn
FindIt.playGame=function()
{
	document.getElementById('videoTag').pause();
	document.body.style.backgroundImage="url("+Utils.Path+'images/BG.png'+")";
	//document.getElementById('nextDiv').style.display='none';
	document.getElementById('gamepage').style.display="block";
	document.getElementById('coverpage').style.display="none";
	document.getElementById('subDiv').style.display="block";
	document.getElementById('clockDiv').style.visibility="visible";
	document.getElementById('header').style.display='block';
	//FindIt.setTimer();
	FindIt.setImage();
	FindIt.setTimer();
	FindIt.createDivs();
	//FindIt.setMainAndSubImage();  
}
FindIt.setTimer=function()
{
	timeId.innerHTML=FindIt.time;
	FindIt.timer=setInterval(function() 
	{
		FindIt.time++;
		timeId.innerHTML=FindIt.time;
		
	},1000);
}
FindIt.setImage=function()
{
	//document.getElementById('logo').src=Utils.Path+"images/logo.png";
	document.getElementById('play').src=Utils.Path+"images/Play.png";
	document.getElementById('next').src=Utils.Path+"images/Next.png";
	document.getElementById('nextGameImg').src=Utils.Path+"images/Next.png";
	document.getElementById('helpImg').src=Utils.Path+"images/help.png";
	document.getElementById('clockImg').src=Utils.Path+"images/stopwatch.png";
}
FindIt.createDivs=function()
{
	var divElement,imgElement;
	for(var i=1;i<=10;i++)
	{
		divElement=document.createElement('div');
		divElement.setAttribute('id','subDiv'+i);
		divElement.setAttribute('class','widthClass  noPadding col-sm-6 col-lg-6 col-md-6 col-xs-6 ');
		
		imgElement=document.createElement('img');
		imgElement.setAttribute('id','subImg'+i);
		imgElement.setAttribute('class',' noPadding col-sm-12 col-lg-12 col-md-12 col-xs-12  img-responsive noPadding imgcls ');
		
		divElement.append(imgElement);
		if(i<6)
			subImageDiv1.append(divElement);
		else
			subImageDiv2.append(divElement);
	}
 	/*  divElement=document.createElement('div');
	divElement.setAttribute('id','nextDiv');
	divElement.setAttribute('class','noPadding col-sm-1 col-lg-1 col-md-1 col-xs-1  col-sm-offset-11 col-lg-offset-11 col-md-offset-11 col-xs-offset-11');
	divElement.setAttribute('onclick','FindIt.showLastPage()');
	imgElement=document.createElement('img');
	imgElement.setAttribute('id','next');
	imgElement.setAttribute('class','img-responsive noPadding imgcls col-sm-9 col-lg-9 col-md-9 col-xs-9');	
	imgElement.setAttribute('src','images/Next.png');
	divElement.append(imgElement); 
	gamepage.append(divElement); */
	FindIt.setMainAndSubImage();  
}

FindIt.setMainAndSubImage=function()
{
	var img,randomSVG=0,imgArray=[],object=[],svgId,temp;
	var d=new Date();
	startTime=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	randomSVG=Math.floor(Math.random() * FindIt.pictureList.length);
	img=FindIt.pictureList[randomSVG].svgData;
	$('#mainImgDiv').html(""+img);
	temp=$('svg>*');
	console.log(temp);
	for(j=1;j<temp.length;j++)
	{
		svgId=$(temp[j]).prop('id');
		document.getElementById(svgId).setAttribute("value","noData");
		document.getElementById(svgId).setAttribute("onclick","FindIt.checkAnswer(this.id)");
	}
	object=FindIt.pictureList[randomSVG].Wlist;
	for(i=0;i<object.length;i++)
	{
		document.getElementById(object[i].ID).setAttribute("class","csshover");
		
	}
//	document.getElementById(FindIt.pictureList[randomSVG].PID).setAttribute('class','noPadding col-sm-12 col-lg-12 col-md-12 col-xs-12');
	$('.csshover').css('pointer-events','all');
	imgArray=FindIt.pictureList[randomSVG].pngImages;
	FindIt.shuffleArray(imgArray);
	for(var i=0;i<imgArray.length;i++)
	{
		document.getElementById('subImg'+(i+1)).src=Utils.Path+"images/"+imgArray[i].imageName;
		document.getElementById('subDiv'+(i+1)).setAttribute("data",imgArray[i].data);
	}
	
	object=FindIt.pictureList[randomSVG].Wlist;
	for(i=0;i<object.length;i++)
	{
		document.getElementById(object[i].ID).setAttribute("value","image"+i);
		//document.getElementById(object[i].ID).setAttribute("sound",FindIt.pictureList[randomSVG].Wlist[i].sound);
		document.getElementById(object[i].ID).setAttribute("onclick","FindIt.checkAnswer(this.id);");
//console.log(document.getElementById(object[i].ID).getAttribute("sound"));
	}
}	
FindIt.shuffleArray=function(array)
{
	var i,j,temp;
	for (i = array.length - 1; i > 0; i--) 
	{
		j = Math.floor(Math.random() * (i + 1));
		temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	return array;
}

FindIt.checkAnswer=function(obj)
{
	var svgData,imgArray=[],divData,flag=false;
	console.log(obj);
	svgData=document.getElementById(obj).getAttribute("value");
	//svgSound=document.getElementById(obj).getAttribute("sound");
	console.log(svgData);
	//console.log(svgSound);
	for(var i=0;i<10;i++)
	{
		divData=document.getElementById('subDiv'+(i+1)).getAttribute("data");
		if(divData==svgData)
		{
			flag=true;
			scoredMarks=scoredMarks+10;
			FindIt.correctAns++;
			$("#"+obj).css('pointer-events','none');
			$('#subDiv'+(i+1)).css('opacity','0.5');
			document.getElementById('playSound').src=Utils.Path+'sounds/thatsright.mp3';
			document.getElementById('playSound').play(); 
			break;
		}
	}
	if(i==10 && flag==false)
	{
		FindIt.wrongAttempt++;
		
	}
	 if(FindIt.correctAns == 10)
	 {
		 clearTimeout(FindIt.timer);
		 document.getElementById('playSound').src='sounds/thatsright.mp3';
		document.getElementById('playSound').play(); 
	 }
	//	FindIt.showLastPage(); 
}
FindIt.helpPage=function()
{
	isHelpTaken=true;
}
FindIt.showLastPage=function()
{
	document.getElementById('helpDiv').style.pointerEvents="none";
	
	
	document.getElementById('playSound').pause();
	document.getElementById('gamepage').style.display='none';
	document.getElementById('endPage').style.display='block';
	document.getElementById('clockDiv').style.visibility='hidden';
	if(FindIt.correctAns==10)
		document.getElementById('puzzleComplete').innerHTML=FindIt.hardcorded[2];
	else
		document.getElementById('puzzleComplete').innerHTML=FindIt.hardcorded[3];
	//document.getElementById('wrong').innerHTML=FindIt.hardcorded[4] +"" +":" +FindIt.wrongAttempt;
	document.getElementById('correct').innerHTML=FindIt.hardcorded[5] + ":" +FindIt.correctAns;
	document.getElementById('time').innerHTML=FindIt.hardcorded[6] + ":" +FindIt.time + "s";
	console.log(totalMarks,scoredMarks,isHelpTaken,FindIt.time,startTime,label,FindIt.isGroupGame);
	if(Utils.mobileDeviceFlag)
		Android.addScore(totalMarks,scoredMarks,isHelpTaken,FindIt.time,startTime,label,FindIt.isGroupGame);
}
FindIt.nextGame=function()
{
	if(Utils.mobileDeviceFlag)
		Android.playNextGame();
}