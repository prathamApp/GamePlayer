var Memorygame={};
var Utils = {};
Utils.Path='';
Utils.mobileDeviceFlag=false;
Memorygame.isGroupGame=false;
Memorygame.helpTakenFlag=false;
var questionId=0,startTime=0,scoredMarks=0,totalMarks=8,resId=0,label="";
$(document).ready(function()
{
	$(".se-pre-con").fadeOut("slow");
	if(navigator.userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|Opera Mini|IEMobile/i))
		Utils.mobileDeviceFlag=true;
	else
		Utils.mobileDeviceFlag=false;
	setTimeout(function()
	{
		$('#instructionDiv').css('display','block');
		$("#videoDiv").css('display','block');
		$('#videoTag').prop('src',Utils.Path+'images/video.mp4');
		//$('#instructionDiv').text(Memorygame.hardcoredata.instruction);
		Memorygame.init();
	},100);
},200);
Memorygame.init=function()
{
	Memorygame.clickCnt=0;
	Memorygame.correctCnt=0;
	Memorygame.wrongCnt=0;
	Memorygame.level=0;
	Memorygame.score=0;
	Memorygame.consecutive=0;
	Memorygame.firstid="";
	Memorygame.moves=0;
	Memorygame.time=0;
	$('#gameOverText').text(Memorygame.hardcoredata.gameovertext);
	$('#start').text(Memorygame.hardcoredata.playButtonText);
	$('#secondsSpan').text("s");
	document.body.style.backgroundImage="url("+Utils.Path+"images/BG3.png)";
	$('#scoreDiv,#clockImage').css('visibility','hidden');
	$('#gamePage,#endPage').hide();
	Memorygame.setImages();
}
// set Images//
Memorygame.setImages=function()
{
	document.getElementById('play').src=Utils.Path+"images/playButton.png";
	document.getElementById('homeImage').src=Utils.Path+"images/Home.png";
	document.getElementById('logoImage').src=Utils.Path+"images/logo.png";
	document.getElementById('clockImage').src=Utils.Path+"images/stopwatch.png";
	document.getElementById('nextButton').src=Utils.Path+"images/nextButton.png";
	document.getElementById('nextGameButton').src=Utils.Path+"images/nextButton.png";
	document.getElementById('helpBtn').src=Utils.Path+"images/help.png";
}
//function which executes on play button click
Memorygame.playGame=function()
{
	$('#coverPage').hide();
	$('#homeImage,#nextButton').css('visibility','hidden');
	$('#logoImage,#gamePage').show();
	Memorygame.endGameFlag=false;
	Memorygame.createGrid();	
	document.getElementById('videoTag').pause();
}
//function which starts the timer
Memorygame.setTimer=function()
{
	$("#timerSpan").text(Memorygame.time);
	Memorygame.timer=setInterval(function() 
	{/* 
		if(time==1)	
		{
			clearInterval(Memorygame.timer);
			$('#gamePage').hide();
			Memorygame.endGameFlag=true;
			$('#endPage').show();
			$('#clockDiv').css('visibility','hidden'); 
		} */
		Memorygame.time++;
		$("#timerSpan").text(Memorygame.time);
	},1000);
}
//function which creates Div Grid
Memorygame.createGrid=function()
{
	var gridDiv,Div,img;
	var d=new Date();
	startTime=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	
	gridDiv=document.createElement('div');
	gridDiv.setAttribute('id',"gridDiv");
	gridDiv.setAttribute('class',"nopadding col-xs-"+Memorygame.layout[Memorygame.level].gridDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].gridDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].gridDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].gridDivClass+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].gridDivOffset+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].gridDivOffset+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].gridDivOffset+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].gridDivOffset);
	document.getElementById('OuterDiv').appendChild(gridDiv);
	for(var i=0;i<Memorygame.layout[Memorygame.level].noOfImages;i++)
	{	
		Div=document.createElement('div');
		Div.setAttribute('id',"Div"+i);
		if(Memorygame.level==2 && (i==0 || i==5 || i==10 || i==15 || i==20))
			Div.setAttribute('class',"nopadding cell"+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].innerDivOffset+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].innerDivOffset+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].innerDivOffset+" "+"col-xs-offset-"+Memorygame.layout[Memorygame.level].innerDivOffset);
		else
			Div.setAttribute('class',"nopadding cell"+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass+" "+"col-xs-"+Memorygame.layout[Memorygame.level].innerDivClass);
		img=document.createElement('img');
		img.setAttribute('id',"img"+i);
		img.setAttribute('class',"nopadding img-responsive imgcls col-xs-12 col-md-12 col-lg-12 col-sm-12");
		Div.appendChild(img);
		document.getElementById('gridDiv').appendChild(Div);
	}
	$('.cell').css('pointer-events','none');
	$('.cell').on('click',function(){
		Memorygame.checkAnswer(this.id);
	});
	$('.imgcls').addClass('animated flip');
	Memorygame.fillImageArray();
	setTimeout(function(){
		$('.imgcls').removeClass('animated flip');
		$('.cell').css('pointer-events','auto');
		$('.imgcls').css('visibility','hidden');
		$('#clockDiv,#nextButton').css('visibility','visible');
		clearInterval(Memorygame.timer);
		Memorygame.setTimer();
	},Memorygame.layout[Memorygame.level].duration);
}
//function which fills images into array to be shown on grid
Memorygame.fillImageArray=function()
{
	var pairs,randomIndex=0,remaining=0,imgArr=[];
	pairs=Math.floor(Memorygame.layout[Memorygame.level].noOfImages/2);// no of images in pairs
	remaining=Memorygame.layout[Memorygame.level].noOfImages-pairs*2;// no of random images rather than pairs
	console.log(remaining);
	//filling pairs
	for(var i=0;i<pairs;i++)
	{
		randomIndex=Math.floor(Math.random()*Memorygame.data.length);
		while($.inArray(randomIndex,imgArr)!=-1)
			randomIndex=Math.floor(Math.random()*Memorygame.data.length);
		imgArr.push(randomIndex);
		imgArr.push(randomIndex);
	}	
	//filling images rather than pairs
	for(var i=0;i<remaining;i++)
	{
		randomIndex=Math.floor(Math.random()*Memorygame.data.length);
		while($.inArray(randomIndex,imgArr)!=-1)
			randomIndex=Math.floor(Math.random()*Memorygame.data.length);
		imgArr.push(randomIndex);
	}	
	shuffleArray(imgArr);
	console.log(imgArr);
	Memorygame.setImagesToGrid(imgArr);
}
Memorygame.checkAnswer=function(obj)
{
	Memorygame.clickCnt++;
	$("#"+obj).children('img').css('visibility','visible');
	$("#"+obj).css('pointer-events','none');
	if(Memorygame.clickCnt==1)
	{	
		Memorygame.answer=$("#"+obj).data('answer');
		Memorygame.firstid=""+obj;
	}
	if(Memorygame.clickCnt==2)
	{
		Memorygame.moves++;
		if(Memorygame.answer==$("#"+obj).data('answer'))
		{	
			//console.log(resId,questionId,10,totalMarks,Memorygame.level,startTime);
			/* if(Utils.mobileDeviceFlag)
				Android.addScore(resId,questionId,10,totalMarks,1,startTime); */
			$("#"+obj).children('img').addClass('animated bounceIn');
			/* document.getElementById('smileeImg').src=Utils.Path+"images/correct.png";
			document.getElementById('playSound').src=Utils.Path+"sounds/Hureyyy.mp3";
			document.getElementById('playSound').play();
			setTimeout(function(){document.getElementById('smileeImg').src=""},1000); */
			Memorygame.correctCnt++;
			Memorygame.score+=5;
			Memorygame.consecutive++;
			$("#score").text(Memorygame.score);
			$("#"+obj).css('pointer-events','none');
			Memorygame.consecutive==3
			if(Memorygame.correctCnt==Memorygame.layout[Memorygame.level].levelchange)
			{
				clearInterval(Memorygame.timer);
				document.getElementById('smileeImg').src=Utils.Path+"images/correct.png";
				document.getElementById('playSound').src=Utils.Path+"sounds/Hureyyy.mp3";
				document.getElementById('playSound').play();
				$('#puzzleComplete').text(Memorygame.hardcoredata.puzzleComplete);
				Memorygame.consecutive=0;
				//Memorygame.level++;
				console.log(Memorygame.moves);
				Memorygame.moves=0;
				/* if(Memorygame.level==0)
				{ */
					/* Memorygame.endGameFlag=true;
					$('#gamePage').hide();
					$('#endPage').show(); 
					$('#clockDiv').css('visibility','hidden'); */
				/* }	 */
				/* setTimeout(function(){
					$('#OuterDiv').empty();
					$('#clockDiv').css('visibility','hidden');
					Memorygame.createGrid();
				},1000); */
			}	
		}
		else
		{
			Memorygame.wrongCnt++;
			Memorygame.score-=1;
			$("#score").text(Memorygame.score);
			Memorygame.consecutive=0;
			$("#"+obj).children('img').addClass('animated wobble');
			setTimeout(function(){$("#"+obj).children('img').css('visibility','hidden');},300);
			$("#"+Memorygame.firstid).children('img').css('visibility','hidden');
			$("#"+obj).css('pointer-events','auto');
			$("#"+Memorygame.firstid).css('pointer-events','auto');
			/* if(Utils.mobileDeviceFlag)
				Android.addScore(resId,questionId,0,totalMarks,1,startTime); */
			//console.log(resId,questionId,0,totalMarks,Memorygame.level,startTime);
			/* document.getElementById('playSound').src=Utils.Path+"sounds/oops.mp3";
			document.getElementById('playSound').play();
			document.getElementById('smileeImg').src=Utils.Path+"images/wrong.png";
			setTimeout(function(){document.getElementById('smileeImg').src=""},300); */
		}		
		Memorygame.clickCnt=0;
	}
}
//function which sets images to grid
Memorygame.setImagesToGrid=function(imgArr)
{
	for(var i=0;i<Memorygame.layout[Memorygame.level].noOfImages;i++)
	{	
		document.getElementById("img"+i).src=Utils.Path+"images/"+Memorygame.data[imgArr[i]].img;
		$("#Div"+i).data('answer',Memorygame.data[imgArr[i]].name);
	}	
}
//function which executes on home button click
Memorygame.goToHome=function()
{
	if(Memorygame.endGameFlag)
	{
		Memorygame.replay();
	}
	else	
		location.reload();
}
Memorygame.isHelpTaken=function()
{
	Memorygame.helpTakenFlag=true;
}	
//function which executes on replay button click
Memorygame.replay=function()
{
	Memorygame.endGameFlag=false;
	Memorygame.clickCnt=0;
	Memorygame.correctCnt=0;
	Memorygame.level=0;
	Memorygame.score=0;
	Memorygame.moves=0;
	Memorygame.consecutive=0;
	Memorygame.firstid;
	$('#endPage').hide();
	$('#gamePage').show();
	$('#OuterDiv').empty();
	$("#score").text(Memorygame.score);
	$("#responseBoxDiv").show();
}
Memorygame.showEndPage=function(){
	document.getElementById('playSound').pause();
	if(Memorygame.correctCnt!=Memorygame.layout[Memorygame.level].levelchange)
		$('#puzzleComplete').text(Memorygame.hardcoredata.puzzleIncomplete);
	$('#gamePage').hide();
	$('#endPage').show();
	document.getElementById('correct').innerHTML="Correct Attempt  :"+Memorygame.correctCnt;
	document.getElementById('wrong').innerHTML="Wrong Attempt  :"+Memorygame.wrongCnt;
	document.getElementById('time').innerHTML="Total Time  :"+Memorygame.time+ "sec"; 
	$('#clockDiv').css('visibility','hidden');
		console.log(totalMarks,Memorygame.correctCnt,Memorygame.helpTakenFlag,Memorygame.time,startTime,label,Memorygame.isGroupGame);
	if(Utils.mobileDeviceFlag)
		Android.addScore(totalMarks,Memorygame.correctCnt,Memorygame.helpTakenFlag,Memorygame.time,startTime,label,Memorygame.isGroupGame);
}
Memorygame.showNextGame=function(){
	if(Utils.mobileDeviceFlag)
		Android.playNextGame();
}
