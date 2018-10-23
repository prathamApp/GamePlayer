var Drawing={};
var Utils = {};
Utils.Path='';
Utils.mobileDeviceFlag=false;
Drawing.time=0;
var counter=0;
Drawing.selectedColor='black';
var ctx,canvas,canvasx,canvasy;
var last_mousex = last_mousey=0;
var mousex = mousey = 0;
var mousedown = false;
var tooltype = 'draw';
var xDown,yDown;
var source = new Image();

var questionId=0,startTime=0,scoredMarks=0,totalMarks=10,resId=0;
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
		$('#instructionDiv').text(Drawing.hardcoredata.instruction);
		Drawing.init();
	},100);
},200);
Drawing.init=function()
{
	document.body.style.backgroundImage="url("+Utils.Path+"images/BG.png)";
	$('#secondsSpan').text("s");
	$('#scoreDiv').css('visibility','visible');
	$('#gamePage,#endPage').hide();
	Drawing.setImages();
}
// set Images//
Drawing.setImages=function()
{
	document.getElementById('play').src=Utils.Path+"images/playButton.png";
	/* document.getElementById('homeImage').src=Utils.Path+"images/Home.png"; */
	document.getElementById('logoImage').src=Utils.Path+"images/logo.png";
	document.getElementById('eraser').src=Utils.Path+"images/Eraser.png";
	document.getElementById('clearCanvas').src=Utils.Path+"images/clear.png";
	document.getElementById('clockImage').src=Utils.Path+"images/stopwatch.png";
	document.getElementById('nextButton').src=Utils.Path+"images/nextButton.png"; 
	document.getElementById('Canvas').style.backgroundImage="url("+Utils.Path+"images/sphere.png)";
}
//function which executes on play button click
Drawing.playGame=function()
{
	document.body.style.backgroundImage="url("+Utils.Path+"images/BG3.png)";
	$('#coverPage').hide();
	$('#homeImage,#paletteSection,#clockDiv,#Canvas').css('visibility','visible');
	$('#homeImage').css('visibility','hidden');
	$('#logoImage,#gamePage').show();
	Drawing.setTimer();
	canvas = document.getElementById('Canvas');
	ctx = canvas.getContext('2d');
	//Variables
	canvasx = $(canvas).offset().left;
	canvasy = $(canvas).offset().top;
	document.addEventListener('touchstart', handleTouchStart, false);        
	document.addEventListener('touchmove', handleTouchMove, false);
}
Drawing.setTimer=function()
{
	$("#timerSpan").text(Drawing.time);
	Drawing.timer=setInterval(function() 
	{
		Drawing.time++;
		$("#timerSpan").text(Drawing.time);
	},1000);
}
$('.colorHeight').on("click",function() {
	Drawing.selectedColor=this.style.backgroundColor;
	Drawing.useTool("draw");
	$("#selectedColor").css("background-color",Drawing.selectedColor);
});
$("#eraser").on("click", function() {
	tooltype='erase';
});
$("#clearCanvas").on("click", function() {
	ctx.clearRect(0,0,700,400);	
	$("#selectedColor").css("background-color",'black');
	tooltype='draw';
	//$('#Canvasdraw').css('fill','white');
});
/* Drawing.setMarker=function(selectedColor) {
	$("#selectedColor").css("background-color",selectedColor);
	Drawing.sketchObj.color=selectedColor;
	Drawing.sketchObj.color=selectedColor.replace(')', ', '+Drawing.opacity+')').replace('rgb', 'rgba');
	Drawing.selectedColor=selectedColor;
}; */
$("#Canvas").on('mousedown', function(e) {
	console.log("Stroke"+""+counter++);
	$('#strokes').text(counter);
	last_mousex = mousex = parseInt(e.clientX-canvasx);
	last_mousey = mousey = parseInt(e.clientY-canvasy);
	mousedown = true;
});
$("#Canvas").on('mouseup', function(e) {
	mousedown = false;
});
$("#Canvas").on('mousemove', function(e) {
	mousex = parseInt(e.clientX-canvasx);
	mousey = parseInt(e.clientY-canvasy);
	if(mousedown) {
		ctx.beginPath();
		if(tooltype=='draw') {
			ctx.globalCompositeOperation = 'source-over';
			ctx.strokeStyle = Drawing.selectedColor;
			ctx.lineWidth = 3;
		} else {
			ctx.globalCompositeOperation = 'destination-out';
			ctx.lineWidth =25;
		}
		ctx.moveTo(last_mousex,last_mousey);
		ctx.lineTo(mousex,mousey);
		ctx.lineJoin = ctx.lineCap = 'round';
		ctx.stroke();
	}
	last_mousex = mousex;
	last_mousey = mousey;
 });
/*  $("#Canvas").on('touchstart', function(e) {
	 //alert("AA");
	 console.log("Stroke"+""+counter++);
	$('#strokes').text(counter);
	last_mousex = mousex = parseInt(e.pageX-canvasx);
	last_mousey = mousey = parseInt(e.pageY-canvasy);
	mousedown = true;
	 
 }); */
function getTouches(evt) {
 return evt.touches ||             // browser API
        evt.originalEvent.touches; // jQuery
}                                                     
/* function handleTouchEnd(evt) {  
//alert('in end');   
	console.log("Stroke"+""+counter++);
	$('#strokes').text(counter);
}; */
$("#Canvas").on('touchend', function(e) {
	console.log("Stroke"+""+counter++);
	$('#strokes').text(counter);
});
function handleTouchStart(evt) {  
//alert('in start');   
   xDown = getTouches(evt)[0].clientX;                                      
   yDown = getTouches(evt)[0].clientY;  
	last_mousex = mousex = parseInt(xDown-canvasx);
	last_mousey = mousey = parseInt(yDown-canvasy);
	ctx.beginPath();
	ctx.moveTo(mousex,mousey);
	mousedown = true;
};          
function handleTouchMove(e) { 
  var xUp = e.touches[0].clientX;                                    
  var yUp = e.touches[0].clientY;

  /*  var xDiff = xDown - xUp;
   var yDiff = yDown - yUp; */
	mousex = parseInt(xUp-canvasx);
	mousey = parseInt(yUp-canvasy);
	if(mousedown){
		//ctx.beginPath();
		if(tooltype=='draw') {
			ctx.globalCompositeOperation = 'source-over';
			ctx.strokeStyle = Drawing.selectedColor;
			ctx.lineWidth = 7;
		} else {
			ctx.globalCompositeOperation = 'destination-out';
			ctx.lineWidth =25;
		}
		//ctx.moveTo(mousex,mousey);
		ctx.lineTo(mousex,mousey);
		//ctx.lineJoin = ctx.lineCap = 'round';
		ctx.stroke();
	}
	last_mousex = mousex;
	last_mousey = mousey;
};
Drawing.useTool= function(tool) {
	 tooltype = tool; //update
}
Drawing.showEndPage=function(){
	$('#gamePage').hide();
	$('#endPage').show();
	document.getElementById('strokes').innerHTML="Strokes  :"+counter;
	document.getElementById('time').innerHTML="Total Time  :"+Drawing.time+ "sec"; 
	clearInterval(Drawing.timer);
	$('#clockDiv').css('visibility','hidden');
	Android.playNextGame();
}
//function which executes on home button click
Drawing.goToHome=function()
{
		location.reload();
}	
