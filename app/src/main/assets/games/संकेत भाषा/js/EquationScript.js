var Equation={};
Equation.timer=0;
Equation.noOfWrong=0;
isHelpTaken=false;
Equation.clickCounter=0;
Equation.isGroupGame=false;
var Utils={};
Utils.Path='';
var totalMarks=68,scoredMarks=0,timeTaken,startTime,label="";


$(document).ready(function()
{
	$(".se-pre-con").fadeOut("slow");
	if(navigator.userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|Opera Mini|IEMobile/i))
		Utils.mobileDeviceFlag=true;
	setTimeout(function()
	{
		Equation.init();
	},100);
});

Equation.init=function()
{
	$("#gamePage").hide();
	$("#scorePage").hide();
	$(".header").not("#logo").css("visibility","hidden");
	Equation.setImages();
	//document.getElementById("instruction").innerHTML=Equation.hardCodedData.instruction;	
	$('#videoTag').prop('src',Utils.Path+'images/cryptoGame.mp4');
	
	
}
//Sets the images to all buttons and image tags.
Equation.setImages=function()

{	
	document.body.style.backgroundImage ="url("+Utils.Path+"images/Game_bg.png";
	document.getElementById('play').src=Utils.Path+"images/playButton.png";
	document.getElementById('homeImage').src=Utils.Path+"images/Home.png";
	document.getElementById('logoImage').src=Utils.Path+"images/logo.png";
	document.getElementById("nextImg").src=Utils.Path+"images/Next.png";
	document.getElementById("nextGameImg").src=Utils.Path+"images/Next.png";
	document.getElementById("helpImg").src=Utils.Path+"images/help.png";
	document.getElementById("timerDiv").innerHTML=Equation.timer+"s";
	document.getElementById("assumption").innerHTML=Equation.hardCodedData.assumption;
	document.getElementById("questionWord").innerHTML=Equation.hardCodedData.question;
	document.getElementById("answerWord").innerHTML=Equation.hardCodedData.option;
	document.getElementById("result").innerHTML=Equation.hardCodedData.gameOver;
}

Equation.playGame=function()
{
	var d;
	document.getElementById("videoTag").pause();
	document.body.style.backgroundImage ="";
	document.body.style.backgroundColor ="rgb(113, 169, 239)";
	d=new Date();
	startTime=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	$("#coverPage").hide();
	$("#gamePage").show();
	//$(".header").css("visibility","visible");
	Equation.createNumberDivs();
	Equation.setQuestion();
	Equation.createOptionDivs();
	Equation.setOptions();
	Equation.playTimer();
}

Equation.playTimer=function()
{
	Equation.timerInterval=setInterval(function()
	{
		Equation.timer+=1;
		document.getElementById("timerDiv").innerHTML=Equation.timer+" s";
	},1000);
}
Equation.createNumberDivs=function()
{
	var i,parentDiv,Div1,Div2,outerDiv,div,sym,img;
	Div1=document.getElementById("Div1");
	
		
	for(i=0;i<10;i++)
	{
		if(i==5)
		{
			Div1=document.getElementById("Div2");
		}
		outerDiv=document.createElement("DIV");
		outerDiv.setAttribute("class","nopadding col-xs-12 col-sm-12 col-md-12 col-lg-12");
		Div1.appendChild(outerDiv);
		
		div=document.createElement("DIV");
		div.setAttribute("id","num"+i);
		div.setAttribute("class","numbers nopadding col-xs-2 col-sm-2 col-md-2 col-lg-2");
		div.innerHTML=Equation.numbers[i].number;
		outerDiv.appendChild(div);
		
		sym=document.createElement("DIV");
		sym.setAttribute("id","sym"+i);
		sym.setAttribute("class","symbols nopadding col-xs-1 col-sm-1 col-md-1 col-lg-1 ");
		sym.innerHTML="=";
		outerDiv.appendChild(sym);
		
		img=document.createElement("IMG");
		img.setAttribute("id","symImg"+i);
		img.setAttribute("class","images img-responsive nopadding col-xs-3 col-sm-3 col-md-3 col-lg-3 ");
		img.setAttribute("src",Utils.Path+"images/"+Equation.numbers[i].symbol);
		outerDiv.appendChild(img);
	}	
}	

Equation.setQuestion=function()
{
	document.getElementById("firstSym").src=Utils.Path+"images/"+Equation.numbers[4].symbol;
	document.getElementById("secondSym").src=Utils.Path+"images/"+Equation.numbers[5].symbol;
	document.getElementById("thirdSym").src=Utils.Path+"images/"+Equation.numbers[2].symbol;
	document.getElementById("fourthSym").src=Utils.Path+"images/"+Equation.numbers[3].symbol;
	document.getElementById("lineImg1").src=Utils.Path+"images/line.png";
	document.getElementById("lineImg2").src=Utils.Path+"images/line.png";
}

Equation.createOptionDivs=function()
{
	var i,j,parentDiv,div,img,span,imgDiv;
	parentDiv=document.getElementById("allOption");
	for(i=1;i<=4;i++)
	{
		div=document.createElement("DIV");
		div.setAttribute("id","optionDiv"+i);
		div.setAttribute("class"," options nopadding col-xs-2 col-sm-2 col-md-2 col-lg-2 col-xs-offset-1 col-md-offset-1 col-lg-offset-1 col-sm-offset-1");
		parentDiv.appendChild(div);
		
		span=document.createElement("DIV");
		span.setAttribute("id","span"+i);
		span.setAttribute("class","nopadding optionLetters col-xs-12 col-sm-12 col-md-12 col-lg-12");
		span.innerHTML=Equation.letters[i-1];
		div.appendChild(span);
		
		imgDiv=document.createElement("DIV");
		imgDiv.setAttribute("id","imgDiv"+i);
		imgDiv.setAttribute("class","nopadding optionDivs col-xs-12 col-sm-12 col-md-12 col-lg-12");
		imgDiv.setAttribute("onclick","Equation.checkAnswer(this.id);");
		div.appendChild(imgDiv);
		
		
		for(j=1;j<=2;j++)
		{
			img=document.createElement("IMG");
			img.setAttribute("id","div"+i+"img"+j);
			if(j==1)
				img.setAttribute("class","img-responsive nopadding col-xs-5 col-sm-5 col-md-5 col-lg-5 col-xs-offset-1 col-md-offset-1 col-lg-offset-1 col-sm-offset-1");
			else
				img.setAttribute("class","img-responsive nopadding col-xs-5 col-sm-5 col-md-5 col-lg-5");
			imgDiv.appendChild(img);
		}
	}
}

Equation.setOptions=function()
{
	var i,rNo,rIndex,rArr=[48,43,27,68],number1,number2;
	Equation.shuffle(rArr);
	
	for(i=0;i<4;i++)
	{
		number1=parseInt(rArr[i]/10);
		document.getElementById("div"+(i+1)+"img"+1).src=Utils.Path+"images/"+Equation.numbers[number1].symbol;
		number2=rArr[i]%10;
		document.getElementById("div"+(i+1)+"img"+2).src=Utils.Path+"images/"+Equation.numbers[number2].symbol;
		document.getElementById("imgDiv"+(i+1)).setAttribute("value",rArr[i]);	
	}	
}

Equation.checkAnswer=function(div)
{
	Equation.clickCounter++;
	$(".optionDivs").css("opacity",1);
	document.getElementById(div).style.opacity=0.5;
	//label+=$('#'+div).prop("value")+",";
	label=label+document.getElementById(div).getAttribute("value")+",";
	//setTimeout(function()
	//{
		//document.getElementById(div).style.opacity=1;
	//},1000);
	if(document.getElementById(div).getAttribute("value")==68)
	{
		clearInterval(Equation.timerInterval);
		document.getElementById("playSound").src=Utils.Path+"sounds/SoundForCorrect.mp3";
		$(".optionDivs").css("pointer-events","none");
		document.getElementById("nextImg").style.visibility="visible";	
	}
	else
	{
		document.getElementById("playSound").src=Utils.Path+"sounds/wrong_ans.mp3";
		Equation.noOfWrong+=1;
	}
	document.getElementById("playSound").play();
}

Equation.showResult=function()
{
	document.getElementById("playSound").pause();
	document.getElementById("timerDiv").style.visibility="hidden";
	$("#scorePage").show();
	$("#gamePage").hide();
	document.getElementById("timeTaken").innerHTML=Equation.hardCodedData.timeTaken+Equation.timer+" Seconds";
	document.getElementById("noOfWrongAttempts").innerHTML=Equation.hardCodedData.noOfWrongAttempts+Equation.noOfWrong;
	console.log(totalMarks,Equation.clickCounter,isHelpTaken,Equation.timer,startTime,label);
	if(Utils.mobileDeviceFlag)
	{
		Android.addScore(totalMarks,Equation.clickCounter,isHelpTaken,Equation.timer,startTime,label,Equation.isGroupGame); 
	}
}

Equation.showNextGame=function()
{
	if(Utils.mobileDeviceFlag)
		Android.playNextGame();
}


Equation.shuffle=function(array)
{
	var ctr = array.length, temp, index;
	while (ctr > 0)
	{
		index = Math.floor(Math.random() * array.length);
		ctr--;
		temp = array[ctr];
		array[ctr] = array[index];
		array[index] = temp;
	}
}

Equation.helpTaken=function()
{
	isHelpTaken=true;
}

Equation.callhome=function()
{
	location.reload();
}
