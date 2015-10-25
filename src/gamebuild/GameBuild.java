/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebuild;

/**
 *
 * @author matt
 */
public class GameBuild {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //First, we define our multidimensional array to house our data
        int [][] gameArray=new int[4][4];
        
        //Next, we define our other variables
        int randomLocation=0;
        int randomValue=0;
        int directionSelected=0;
        int validDirection=1;
        int validCompression=1;
        boolean youLose=false;
                
        //now we initialize our gameArray to all zeros
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                gameArray[i][j]=0;
            }
        }
        
        while (youLose==false) {
        
            //We update the board
            youLose=computerPlay(gameArray);

            //Print the updated board
            printArray(4,4,gameArray);
            System.out.println();
            
            if (youLose==true)
                //TODO: call to calculate score
                break;

            //Update the board based on the direction selected
            while ((validDirection==1) && (validCompression==1)) {
                //We listen for the user to pick a direction
                //TODO
                //For now, we let the computer pick a random direction
                directionSelected=pickRandomDirection();
                System.out.println("direction selected: "+directionSelected);
                validDirection=shiftBoard(gameArray,directionSelected);
                validCompression=compressBoard(gameArray,directionSelected);
                shiftBoard(gameArray,directionSelected);
            }
            validDirection=1;
            validCompression=1;

            printArray(4,4,gameArray);
        
        }
        
        System.out.println("Game Over");
        
    }
    
    public static void printArray(int numRows, int numCols, int[][] inputArray) {
        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {
                System.out.print("Array["+i+"]["+j+"]="+inputArray[i][j]+"\t");
            }
            System.out.print("\n");
        }
    }
    
    public static boolean computerPlay(int[][] inputArray) {
        //We need to place the logic to pick a random location and give it a value
        while (true) {
            int randomLocation=(int)(Math.random()*16);
            System.out.println("random Location: "+randomLocation);
            if (inputArray[randomLocation/4][randomLocation%4]==0){
                inputArray[randomLocation/4][randomLocation%4]=(Math.random()<.9?2:4);
                break;
            }
        }
        //Does the most recent placement end the game?
        //First check to see if the latest placement fills the grid
        boolean gridFull=true;
        zeroGridCheck:
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (inputArray[i][j]==0) {
                    gridFull=false;
                    break zeroGridCheck;
                }
            }
        }
        //If the grid is full, can the player make a valid move?
        if (gridFull==true) {
            boolean youLose=true;
            //Scan for a possible column combo
            columnCheck:
            for (int i=0; i<4; i++) {
                for (int j=0; j<3; j++) {
                    if (inputArray[i][j]==inputArray[i][j+1]) {
                        youLose=false;
                        break columnCheck;
                    }
                }
            }
            if (youLose==true) {
                rowCheck:
                for (int j=0; j<4; j++) {
                    for (int i=0; i<3; i++) {
                        if (inputArray[i][j]==inputArray[i+1][j]) {
                            youLose=false;
                            break rowCheck;
                        }
                    }
                }
            }
            return youLose;
        }
        return false;
    }
    
    public static int pickRandomDirection() {
        int randomDirection=(int)(Math.random()*100/(100/4));
        return randomDirection;
    }
    
    public static int shiftBoard(int[][] inputArray,int direction) {
        int error=1;
        //This shiftBoard function simply eliminates the zero spaces
        switch (direction) {
            //if the direction is up
            case 0:
                for (int x=0; x<4; x++) {
                    for (int j=0; j<4; j++) {
                        for (int i=3; i>0; i--) {
                            if ((inputArray[i-1][j]==0) && (inputArray[i][j]!=0)) {
                                inputArray[i-1][j]=inputArray[i][j];
                                inputArray[i][j]=0;
                                error=0;
                            }

                        }
                    }
                }
                return error;
            //if the direction is to the right
            case 1:
                for (int x=0; x<4; x++) {
                    for (int i=0; i<4; i++) {
                        for (int j=0; j<3; j++) {
                            if ((inputArray[i][j+1]==0) && (inputArray[i][j]!=0)) {
                                inputArray[i][j+1]=inputArray[i][j];
                                inputArray[i][j]=0;
                                error=0;
                            }
                        }
                    }
                }
                return error;
            //if the direction is down
            case 2:
                for (int x=0; x<4; x++) {
                    for (int j=0; j<4; j++) {
                        for (int i=0; i<3; i++) {
                            if ((inputArray[i+1][j]==0) && (inputArray[i][j]!=0)) {
                                inputArray[i+1][j]=inputArray[i][j];
                                inputArray[i][j]=0;
                                error=0;
                            }
                        }
                    }
                }
                return error;
            //if the direction is to the left
            case 3:
                for (int x=0; x<4; x++) {
                    for (int i=0; i<4; i++) {
                        for (int j=3; j>0; j--) {
                            if ((inputArray[i][j-1]==0) && (inputArray[i][j]!=0)) {
                                inputArray[i][j-1]=inputArray[i][j];
                                inputArray[i][j]=0;
                                error=0;
                            }
                        }
                    }
                }
                return error;
            //else do nothing
            default:
                return error;
        }
    }
    
    public static int compressBoard(int[][] inputArray, int direction) {
        int error=1;
        
        switch(direction) {
            case 0:
                for (int x=0; x<4; x++) {
                    for (int j=0; j<4; j++) {
                        for (int i=0; i<3; i++) {
                            if ((inputArray[i+1][j]==inputArray[i][j]) && (inputArray[i][j]!=0)) {
                                inputArray[i][j]=inputArray[i][j]*2;
                                inputArray[i+1][j]=0;
                                error=0;
                            }
                        }
                    }
                }
                return error;
            case 1:
                for (int x=0; x<4; x++) {
                    for (int i=0; i<4; i++) {
                        for (int j=3; j>0; j--) {
                            if ((inputArray[i][j-1]==inputArray[i][j]) && (inputArray[i][j]!=0)) {
                                inputArray[i][j]=inputArray[i][j]*2;
                                inputArray[i][j-1]=0;
                                error=0;
                            }
                        }
                    }
                }
                return error;
            case 2:
                for (int j=0; j<4; j++) {
                    for (int i=3; i>0; i--) {
                        if ((inputArray[i-1][j]==inputArray[i][j]) && (inputArray[i][j]!=0)) {
                            inputArray[i][j]=inputArray[i][j]*2;
                            inputArray[i-1][j]=0;
                            error=0;
                        }
                    }
                }
                return error;
            case 3:
                for (int i=0; i<4; i++) {
                    for (int j=0; j<3; j++) {
                        if ((inputArray[i][j+1]==inputArray[i][j]) && (inputArray[i][j]!=0)) {
                            inputArray[i][j]=inputArray[i][j]*2;
                            inputArray[i][j+1]=0;
                            error=0;
                        }
                    }
                }
                return error;
            default:
                return error;
        }
    }
}
