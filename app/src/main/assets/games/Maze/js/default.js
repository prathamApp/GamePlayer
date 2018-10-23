let ctx;
let canvas;
let maze;
let mazeHeight;
let mazeWidth;
let player;
var mazeJson;
var xDown = null;                                                        
var yDown = null;
var Utils={};
Utils.Path='';
var bestMove=54,timer=0,myTimer;
var wonFlag=false,moves=0,correctAnswer=0;

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

    ctx.fillStyle = this.playerColor;
    ctx.fillRect((player.col * this.cellSize) + 2, (player.row * this.cellSize) + 2, this.cellSize - 4, this.cellSize - 4);
    //console.log("Current Point "+((player.col * this.cellSize) + 2)+" "+((player.row * this.cellSize) + 2), this.cellSize - 4, this.cellSize - 4);
    if(((player.col * this.cellSize) + 2)==correctAnswer && ((player.row * this.cellSize) + 2)==correctAnswer  &&  wonFlag==false)
      {
        /*alert('You Won');  
        alert('Moves Required: '+bestMove+' Your Moves: '+moves);*/
         wonFlag=true;
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
    document.getElementById('timerDiv').style.visibility='hidden';

    if(wonFlag)
      document.getElementById('status').innerHTML="Puzzle Completed";
    else
      document.getElementById('status').innerHTML="Puzzle Incompleted";
    document.getElementById('correct').innerHTML="Best Moves Count  :"+bestMove;
    document.getElementById('wrong').innerHTML="Total Moves Count :"+moves;
    document.getElementById('time').innerHTML="Total Time  :"+timer + " sec"; 
}

/*function onClick(event) {
  maze.cols = document.getElementById("cols").value;
  maze.rows = document.getElementById("rows").value;
  maze.generate();
}
*/
function onKeyDown(event) {
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


function getTouches(evt) {
  return evt.touches ||             // browser API
         evt.originalEvent.touches; // jQuery
}                                                     

function handleTouchStart(evt) {  
//alert('in start');                                       
    xDown = getTouches(evt)[0].pageX;                                      
    yDown = getTouches(evt)[0].pageY;                             
};                                                

function handleTouchMove(evt) {
  //alert('in move');

    if ( ! xDown || ! yDown ) {
        return;
    }

    var xUp = evt.touches[0].pageX;                                    
    var yUp = evt.touches[0].pageY;

    var xDiff = xDown - xUp;
    var yDiff = yDown - yUp;

    if ( Math.abs( xDiff ) > Math.abs( yDiff ) ) {/*most significant*/
        if ( xDiff > 0 ) {
          /* left swipe */ 
           if (!maze.cells[player.col][player.row].westWall) 
              {  player.col -= 1;     moves++;}  
          } 
         else {
            /* right swipe */
              if (!maze.cells[player.col][player.row].eastWall) 
               { player.col += 1;  moves++;}
                                 
            }
      }
    else {
        if ( yDiff > 0 ) {
            /* up swipe */
              if (!maze.cells[player.col][player.row].northWall)
                {  player.row -= 1;   moves++;}
        } else { 
            /* down swipe */
              if (!maze.cells[player.col][player.row].southWall) 
              {player.row += 1;  moves++;}
        }                                                                 
    }
   //console.log(''+moves);
   
  maze.redraw();     
   

};

function handleTouchEnd(evt){
   evt.preventDefault(); 
     xDown = null;
    yDown = null; 
}

//set images
function setimages()
{
  //document.getElementById('play').src=Utils.Path+"images/playButton.png";
  document.body.style.backgroundImage="url("+Utils.Path+"images/story4L2.png)";
  //document.getElementById('home').src=Utils.Path+"images/Home.png";
  document.getElementById('next').src=Utils.Path+"images/nextButton.png";
  document.getElementById('logo').src=Utils.Path+"images/logo.png";
  //document.getElementById('rightDiv').style.backgroundImage="url("+Utils.Path+"images/background.jpg)";
  document.getElementById('next').setAttribute('onclick','showLastPage()');
  document.getElementById('next').setAttribute('ontouchstart','showLastPage()');
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
  wonFlag=false;
  moves=0;
  $("#homeDiv").css("visibility","hidden");
  $('#endPage').hide();
  canvas = document.getElementById("mainForm");
  ctx = canvas.getContext("2d");
  player = new Player();
  maze = new Maze(12, 12, 30);
  $('#homeButton').css('visibility','hidden');

  setimages();
  setTimer();
  var position=$('#canvasDiv').offset();
  $('#start').css('left',position.left);
  var canvasDiv1=document.getElementById('mainForm');
 document.addEventListener("keydown", onKeyDown);
 /* canvasDiv1.addEventListener('touchstart', handleTouchStart, false);        
  canvasDiv1.addEventListener('touchmove', handleTouchMove, false);
  canvasDiv1.addEventListener('touchend', handleTouchEnd, false); */
   document.addEventListener('touchstart', handleTouchStart, false);        
   document.addEventListener('touchmove', handleTouchMove, false);
   document.addEventListener('touchend', handleTouchEnd, false); 
   xDown = null;
  yDown = null; 

}
