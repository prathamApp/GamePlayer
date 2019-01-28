let ctx;
let canvas;
let maze;
let mazeHeight;
let mazeWidth;
let player;
var mazeJson;
var blinkI;
var xDown = null;                                                        
var yDown = null;
var Utils={};
Utils.Path='';
var bestMove=54,timer=0,myTimer;
var wonFlag=false,moves=0,correctAnswer=0,helpFlag=false,scoredMarks=0,totalMarks=10,startTime=0,label="";
Utils.Path='';
Utils.mobileDeviceFlag=false;
isGroupGame=false;

class Player {
  constructor() {
    this.col = 0;
    this.row = 0;
  }
}

class MazeCell {
  constructor(col, row) {
    this.col = col;
    this.row = row;

    this.eastWall = true;
    this.northWall = true;
    this.southWall = true;
    this.westWall = true;
    this.visited = false;
  }

}

class Maze {
  constructor(cols, rows, cellSize) {
    correctAnswer=rows*cellSize-28;
    this.backgroundColor = "#ffffff";
    this.cols = cols;
    this.endColor = "#88FF88";
    this.mazeColor = "#000000";
    this.playerColor = "#880088";
    this.rows = rows;
    this.cellSize = cellSize;
    this.cells = [];
    this.generate()

  }
generate() {

    mazeHeight = this.rows * this.cellSize;
    mazeWidth = this.cols * this.cellSize;

    canvas.height = mazeHeight;
    canvas.width = mazeWidth;
    canvas.style.height = mazeHeight;
    canvas.style.width = mazeWidth;

    for (let col = 0; col < this.cols; col++) {
      this.cells[col] = [];
      for (let row = 0; row < this.rows; row++) {
        this.cells[col][row] = new MazeCell(col, row);
      }
    }
  let stack = [];
    let rndCol = Math.floor(Math.random() * this.cols);
    let rndRow = Math.floor(Math.random() * this.rows);
    
    stack.push(this.cells[rndCol][rndRow]);
    var a=rndCol+' '+rndRow;
    console.log(a);
    let currCell;
    let dir;
    let foundNeighbor;
    let nextCell;

    while (this.hasUnvisited(this.cells)) {
      currCell = stack[stack.length - 1];
      currCell.visited = true;
      if (this.hasUnvisitedNeighbor(currCell)) {

        nextCell = null;
        foundNeighbor = false;
        do {
          dir = Math.floor(Math.random() * 4);
          switch (dir) {
            case 0:
              if (currCell.col !== (this.cols - 1) && !this.cells[currCell.col + 1][currCell.row].visited) {
                currCell.eastWall = false;
                nextCell = this.cells[currCell.col + 1][currCell.row];
                nextCell.westWall = false;
                foundNeighbor = true;
                     
              }
              break;
            case 1:
              if (currCell.row !== 0 && !this.cells[currCell.col][currCell.row - 1].visited) {
                currCell.northWall = false;
                nextCell = this.cells[currCell.col][currCell.row - 1];
                nextCell.southWall = false;
                foundNeighbor = true;
                     
              }
              break;
            case 2:
              if (currCell.row !== (this.rows - 1) && !this.cells[currCell.col][currCell.row + 1].visited) {
                currCell.southWall = false;
                nextCell = this.cells[currCell.col][currCell.row + 1];
                nextCell.northWall = false;
                foundNeighbor = true;
                   
              }
              break;
            case 3:
              if (currCell.col !== 0 && !this.cells[currCell.col - 1][currCell.row].visited) {
                currCell.westWall = false;
                nextCell = this.cells[currCell.col - 1][currCell.row];
                nextCell.eastWall = false;
                foundNeighbor = true;
                    
              }
              break;
          }
          if (foundNeighbor) {
            stack.push(nextCell);

          }
        } while (!foundNeighbor)
      } else {
        currCell = stack.pop();
      }
    }
    this.redraw();
  }

  hasUnvisited() {
    for (let col = 0; col < this.cols; col++) {
      for (let row = 0; row < this.rows; row++) {
        if (!this.cells[col][row].visited) {
          return true;
        }
      }
    }
    return false;
  }

  hasUnvisitedNeighbor(mazeCell) {
    return ((mazeCell.col !== 0               && !this.cells[mazeCell.col - 1][mazeCell.row].visited) ||
            (mazeCell.col !== (this.cols - 1) && !this.cells[mazeCell.col + 1][mazeCell.row].visited) ||
            (mazeCell.row !== 0               && !this.cells[mazeCell.col][mazeCell.row - 1].visited) ||
            (mazeCell.row !== (this.rows - 1) && !this.cells[mazeCell.col][mazeCell.row + 1].visited));
  }

  redraw() {
   //var str = JSON.stringify(this.cells); // (Optional) beautiful indented output.
    //console.log(str);
    //console.dir(str);
    clearInterval(blinkI);
    this.cells=mazeJson;
    ctx.fillStyle = this.backgroundColor;
    ctx.fillRect(0, 0, mazeHeight, mazeWidth);

    ctx.fillStyle = this.endColor;
    ctx.fillRect((this.cols - 1) * this.cellSize, (this.rows - 1) * this.cellSize, this.cellSize, this.cellSize);
   // console.log("Finish Point "+(this.cols - 1) * this.cellSize, (this.rows - 1) * this.cellSize, this.cellSize, this.cellSize);
    ctx.strokeStyle = this.mazeColor;
    ctx.strokeRect(0, 0, mazeHeight, mazeWidth);


    for (let col = 0; col < this.cols; col++) {
      for (let row = 0; row < this.rows; row++) {
        if (this.cells[col][row].eastWall) {
             
          ctx.beginPath();
          ctx.moveTo((col + 1) * this.cellSize, row * this.cellSize);
          ctx.lineTo((col + 1) * this.cellSize, (row + 1) * this.cellSize);
          ctx.stroke();
        }
        if (this.cells[col][row].northWall) {
              
          ctx.beginPath();
          ctx.moveTo(col * this.cellSize, row * this.cellSize);
          ctx.lineTo((col + 1) * this.cellSize, row * this.cellSize);
          ctx.stroke();
        }
        if (this.cells[col][row].southWall) {
                
          ctx.beginPath();
          ctx.moveTo(col * this.cellSize, (row + 1) * this.cellSize);
          ctx.lineTo((col + 1) * this.cellSize, (row + 1) * this.cellSize);
          ctx.stroke();
        }
        if (this.cells[col][row].westWall) {
               
          ctx.beginPath();
          ctx.moveTo(col * this.cellSize, row * this.cellSize);
          ctx.lineTo(col * this.cellSize, (row + 1) * this.cellSize);
          ctx.stroke();
        }
      }
    }
    
    //ctx.fillRect((player.col * this.cellSize) + 2, (player.row * this.cellSize) + 2, this.cellSize - 4, this.cellSize - 4);    
	//var clr=col=cSize=row=0;
    ctx.fillStyle = this.playerColor;
    let clr=this.playerColor;
    let col=player.col;
    let cSize=this.cellSize;
    let row=player.row;
	   	
   	let counter = 0;
	blinkI=setInterval(function() {
	    ctx.fillStyle = (counter % 2 === 0) ? clr : 'white';
	    //context.fillRect(10, 10, 80, 80);
	    ctx.fillRect((col * cSize) + 2, (row * cSize) + 2, cSize - 4, cSize - 4);
	    counter++;
	}, 200);

	//if(row!=0 || col!=0){
		document.getElementById("playSound").src="sounds/Bell.mp3";
		document.getElementById("playSound").play();
	//}
    //console.log("Current Point "+((player.col * this.cellSize) + 2)+" "+((player.row * this.cellSize) + 2), this.cellSize - 4, this.cellSize - 4);
    if(((player.col * this.cellSize) + 2)==correctAnswer && ((player.row * this.cellSize) + 2)==correctAnswer  &&  wonFlag==false)
      {
        /*alert('You Won');  
        alert('Moves Required: '+bestMove+' Your Moves: '+moves);*/
		scoredMarks=10;
         wonFlag=true;
         $('.navigation').css("pointer-events","none");
          clearInterval(blinkI);
           ctx.fillStyle = this.playerColor;
           ctx.fillRect((col * cSize) + 2, (row * cSize) + 2, cSize - 4, cSize - 4);

          clearInterval(myTimer);          
         //$("#nextDiv").css("pointer-events","auto");
        //showLastPage();
		
      }
  }
}

function showLastPage()
{
   //document.getElementById('playSound').src='sounds/thatsright.mp3';
   // document.getElementById('playSound').play(); 
    //document.getElementById('playSound').pause();
	document.getElementById('gamePage').style.display='none';
    document.getElementById('endPage').style.display='block';
    if(wonFlag)
        document.getElementById('status').innerHTML=hardcodeddata.puzzleComplete;
    else
      document.getElementById('status').innerHTML=hardcodeddata.puzzleIncomplete;
    document.getElementById('correct').innerHTML=hardcodeddata.bestMoveMsg+bestMove;
    document.getElementById('wrong').innerHTML=hardcodeddata.totalMoveMsg+moves;
    document.getElementById('time').innerHTML=hardcodeddata.timeMsg+timer +" sec";  
    //document.getElementById('next').setAttribute("onclick","playNextGame()");
    document.getElementById('timerDiv').style.visibility='hidden';
	console.log(totalMarks,scoredMarks,helpFlag,timer,startTime,label,isGroupGame);
	if(Utils.mobileDeviceFlag)
		Android.addScore(totalMarks,scoredMarks,helpFlag,timer,startTime,label,isGroupGame);
}

/*function onClick(event) {
  maze.cols = document.getElementById("cols").value;
  maze.rows = document.getElementById("rows").value;
  maze.generate();
}
*/
function onKeyDown(event){
  console.log(event.type + ": " +  event.which);
  switch (event.which) {
    case 37:
    case 65:
      if (!maze.cells[player.col][player.row].westWall) {
        //console.log('left');
        player.col -= 1;
        moves++;
      }
       event.preventDefault();
      break;
    case 39:
    case 68:
      if (!maze.cells[player.col][player.row].eastWall) {
        player.col += 1;
       // console.log('right');
        moves++;
      }
     event.preventDefault();
      break;
    case 40:
    case 83:
      if (!maze.cells[player.col][player.row].southWall) {
        player.row += 1;
        //console.log('down');
        moves++;
      }
      event.preventDefault();
      break;
    case 38:
    case 87:
      if (!maze.cells[player.col][player.row].northWall) {
        player.row -= 1;
       //console.log('up');
        moves++;
      }
      event.preventDefault();
      break;
    default:
      break;
  }
    //console.log(moves);
  maze.redraw();
}

function navigation1(val){
 // console.log(event.type + ": " +  event.which);
  switch (val) {
    case 37:
    case 65:
      if (!maze.cells[player.col][player.row].westWall) {
        //console.log('left');
        player.col -= 1;
        moves++;
      }
       event.preventDefault();
      break;
    case 39:
    case 68:
      if (!maze.cells[player.col][player.row].eastWall) {
        player.col += 1;
       // console.log('right');
        moves++;
      }
     event.preventDefault();
      break;
    case 40:
    case 83:
      if (!maze.cells[player.col][player.row].southWall) {
        player.row += 1;
        //console.log('down');
        moves++;
      }
      event.preventDefault();
      break;
    case 38:
    case 87:
      if (!maze.cells[player.col][player.row].northWall) {
        player.row -= 1;
       //console.log('up');
        moves++;
      }
      event.preventDefault();
      break;
    default:
      break;
  }
    //console.log(moves);
  maze.redraw();
}

//set images
function setimages()
{
  //document.getElementById('play').src=Utils.Path+"images/playButton.png";
 
  //document.getElementById('home').src=Utils.Path+"images/Home.png";
  document.getElementById('next').src=Utils.Path+"images/nextButton.png";
  document.getElementById('nextImg').src=Utils.Path+"images/nextButton.png";
  document.getElementById('helpImg').src=Utils.Path+"images/help.png";
  document.getElementById('logo').src=Utils.Path+"images/logo.png";
  //document.getElementById('rightDiv').style.backgroundImage="url("+Utils.Path+"images/background.jpg)";
 // document.getElementById('next').setAttribute('onclick','showLastPage()');
 // document.getElementById('next').setAttribute('ontouchstart','showLastPage()');
  document.getElementById('rightImg').src=Utils.Path+"images/right.jpg";
  document.getElementById('upImg').src=Utils.Path+"images/up.jpg";
  document.getElementById('downImg').src=Utils.Path+"images/down.jpg";
  document.getElementById('leftImg').src=Utils.Path+"images/left.jpg";
}
function setTimer()
{
  document.getElementById("timerSpan").innerHTML=timer+" s";
  myTimer=setInterval(function()
  {
    timer++;
    document.getElementById("timerSpan").innerHTML=timer+" s";
  },1000);
}

function onLoad() {
	if(navigator.userAgent.match(/Android|BlackBerry|iPhone|iPad|iPod|Opera Mini|IEMobile/i))
		Utils.mobileDeviceFlag=true;
	else
		Utils.mobileDeviceFlag=false;

    wonFlag=false;
    moves=0;
    $("#homeDiv").css("visibility","hidden");
    $('#gamePage,#endPage').hide();
    canvas = document.getElementById("mainForm");
    ctx = canvas.getContext("2d");
    document.getElementById("playSound").pause();
    document.getElementById("playSound").src='';
    player = new Player();    
    maze = new Maze(12, 12, 30);
    $('#instructionDiv').hide();
    $('#instructionDiv').text(hardcodeddata.startingInstruction);
    $('#videoTag').prop('src',Utils.Path+'images/mazegame.mp4');
    document.getElementById("playSound").src='';
    
    document.getElementById('playImg').src=Utils.Path+"images/playButton.png";
     document.body.style.backgroundImage="url("+Utils.Path+"images/story4L2.png)";
    $('#playImg').attr('onclick','playGame()');

}

function playGame(){
 var d;	
  $('#coverPage').hide();
    $('#gamePage').show();
     document.getElementById('videoTag').pause();
  //$('#homeButton').css('visibility','hidden');
  $("#homeDiv,#logoDiv").hide();
  setimages();
  setTimer();
  d=new Date();
  startTime=d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
  var position=$('#canvasDiv').offset();
  $('#start').css('left',position.left);
  var canvasDiv1=document.getElementById('mainForm');
 document.addEventListener("keydown", onKeyDown);

  document.getElementById('left').setAttribute('onclick','navigation1(37)');
  document.getElementById('up').setAttribute('onclick','navigation1(38)');
  document.getElementById('right').setAttribute('onclick','navigation1(39)');
  document.getElementById('down').setAttribute('onclick','navigation1(40)');

   xDown = null;
  yDown = null; 

}
helpTaken=function()
{
	helpFlag=true;
}
playNextGameJS=function()
{
	if(Utils.mobileDeviceFlag)
	 	Android.playNextGame();
}
