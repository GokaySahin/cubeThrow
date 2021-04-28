// Gökay Şahin 041901032 10.05.2020

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Gökay_Şahin {
    public static void main(String[] args) throws FileNotFoundException {
        String dataFileName = "game_environment.txt";
        File dataBase = new File( dataFileName );
        ArrayList<GameObject> gameEnv = fileReader( dataBase );
        //Assigning variables , holding balls first speed and converting angle to radian

        double g = 9.81;
        double x0 = 120;
        double y0 = 120;
        int ntime = 1;
        int pauseTime = 100;
        double deltaTime = 0.1;
        double time = 0;
        double windF = 0;
        double ballX = x0;
        double ballY = y0;
        double ballV = 15.0;
        double ballAngle = 65.0;
        double ballR = 4.0;
        double halfX0 = x0 / 2.0;
        double halfY0 = y0 / 2.0;
        double arrowLength = 100;


        // creating variables to use later
        String velocityText;
        String angleText;
        String angleVelocity;

        //assigning values for canvas and panel for showing balls values
        int width = 1300;
        int height = 600;
        double halfWidth = x0 / 2.0;
        double halfHeight = y0 / 2.0;
        boolean isFired = false;
        boolean gameStatus = true;
        boolean windStatus = false;

        // assigning canvas sizes and scaling
        StdDraw.setCanvasSize( width, height );
        StdDraw.setXscale( 0, width );
        StdDraw.setYscale( 0, height );
        // loop for reading input and throwing ball
        while (true) {
            //play again button
            if (StdDraw.isKeyPressed( KeyEvent.VK_P )) {
                System.out.println( "Restarting" );
                ballX = x0;
                ballY = y0;
                ntime = 1;
                time = 0;
                StdDraw.pause( pauseTime );
                windStatus = false;
                gameStatus = true;
                isFired = false;
            }
            // button for wind
            if (StdDraw.isKeyPressed( KeyEvent.VK_W )) {
                if (!windStatus) {
                    windStatus = true;
                    Random random = new Random();
                    windF = random.nextInt( 5 + 5 ) - 5;
                } else {
                    System.out.println( "Wind is already active." );
                }
                StdDraw.pause( pauseTime );

            }
            // button for quit
            if (StdDraw.isKeyPressed( KeyEvent.VK_Q )) {
                System.out.println( "Quiting..." );
                System.exit( 0 );
            }
            // button to fire
            if (StdDraw.isKeyPressed( KeyEvent.VK_SPACE )) {
                if (ntime == 1 && !isFired) {
                    System.out.println( "Fire the ball..." );
                    ntime++;
                    isFired = false;
                }
            }
            // button to increase angle
            if (StdDraw.isKeyPressed( KeyEvent.VK_UP )) {
                System.out.println( "Angle got increased" );
                ballAngle += 0.5;
                System.out.println( "Angle: " + ballAngle + " Velocity: " + ballV );
                StdDraw.pause( pauseTime );
            }
           // button to decrease angle
            if (StdDraw.isKeyPressed( KeyEvent.VK_DOWN )) {
                System.out.println( "Angle got decreased" );
                ballAngle -= 0.5;
                System.out.println( "Angle: " + ballAngle + " Velocity: " + ballV );
                StdDraw.pause( pauseTime );
            }
            // button to decrease speed also arrow length
            if (StdDraw.isKeyPressed( KeyEvent.VK_LEFT )) {
                System.out.println( "Speed got decreased" );
                ballV -= 0.5;
                arrowLength -= 5;
                System.out.println( "Angle: " + ballAngle + " Velocity: " + ballV );
                StdDraw.pause( pauseTime );
            }
            // button to increase speed also arrow length
            if (StdDraw.isKeyPressed( KeyEvent.VK_RIGHT )) {
                System.out.println( "Speed got increased" );
                ballV += 0.5;
                arrowLength += 5;
                System.out.println( "Angle: " + ballAngle + " Velocity: " + ballV );
                StdDraw.pause( pauseTime );
            }
            // updating angle in radian and arrows coordinates
            double angleRadian = ballAngle * (Math.PI / 180.0);
            double x1 = x0 + arrowLength / 4.0 * Math.cos( angleRadian );
            double y1 = y0 + arrowLength / 4.0 * Math.sin( angleRadian );

            // updating balls coordinates if fired
            if (ntime > 1) {
                    time = time + deltaTime;
                    ballX = ballX + (((ballV * Math.cos( angleRadian ) * time) + windF));
                    ballY = (ballY + ((ballV * time * Math.sin( angleRadian )))) - (g / 2.0 * time * time);
            }
            // checking for hit and notifying user
            for (int i = 0; i < gameEnv.size(); i++) {
                if (gameEnv.get( i ).isInside( ballX, ballY ) && gameEnv.get( i ).type == 1) {
                    ntime = 1;
                    if (gameStatus) {
                        System.out.println( "Failed." );
                        gameStatus = false;
                    }
                }
                if (gameEnv.get( i ).isInside( ballX, ballY ) && gameEnv.get( i ).type == 2) {
                    ntime = 1;
                    if (gameStatus) {
                        System.out.println( "Good shot ! You hit the target" );
                        gameStatus = false;
                    }
                }
            }
            // clearing and drawing again for updating image
            StdDraw.clear();
            StdDraw.setPenColor( Color.BLACK );
            StdDraw.line( x0, y0, x1, y1 );
            StdDraw.textLeft( 975, 550, "PRESS P TO PLAY AGAIN . Q TO QUIT" );
            StdDraw.textLeft( 975, 525, "PRESS W TO ACTIVATE WIND" );
            if (windStatus) {
                StdDraw.textLeft( 975, 500, "Wind: " + windF );
                if (windF >= 0) {
                    StdDraw.textLeft( 975, 485, "------>" );
                } else {
                    StdDraw.textLeft( 975, 485, "<------" );
                }
            }

            StdDraw.setPenColor( Color.MAGENTA );
            StdDraw.filledRectangle( x0 / 2.0, y0 / 2.0, halfX0, halfY0 );

            StdDraw.setPenColor( Color.black );
            velocityText = String.format( "%.1f", ballV );
            angleText = String.format( "%.1f", ballAngle );
            angleVelocity = angleText + " " + velocityText;
            StdDraw.setPenColor( StdDraw.BLACK );
            StdDraw.text( halfWidth, halfHeight, angleVelocity );

            ballDrawer( ballX, ballY, ballR );
            for (int i = 0; i < gameEnv.size(); i++) {
                gameEnv.get( i ).draw();
            }
            StdDraw.enableDoubleBuffering();
            StdDraw.show();
            StdDraw.pause( 25 );
        }
    }

    /**
     *
     * @param dataBase file of game environment txt
     * @return game objects in a array list
     * @throws FileNotFoundException if failes to find file
     */
    public static ArrayList<GameObject> fileReader(File dataBase) throws FileNotFoundException {
        ArrayList<GameObject> input_readPlaces = new ArrayList<>();
        Scanner scanner = new Scanner( dataBase );
        while (scanner.hasNextLine()) {
            String wholeData = scanner.nextLine();
            String[] wholeDataArray = wholeData.split( ";" );
            int type = Integer.parseInt( wholeDataArray[0] );
            double input_x = Double.parseDouble( wholeDataArray[1] );
            double input_y = Double.parseDouble( wholeDataArray[2] );
            double input_w = Double.parseDouble( wholeDataArray[3] );
            double input_h = Double.parseDouble( wholeDataArray[4] );
            GameObject tempObject = new GameObject( type, input_x, input_y, input_w, input_h );
            input_readPlaces.add( tempObject );
        }
        scanner.close();
        return input_readPlaces;
    }

    /**
     *
     * @param input_X x coordinate of ball
     * @param input_Y y coordinate of ball
     * @param input_R radius of ball
     */
    public static void ballDrawer(double input_X, double input_Y, double input_R) {
        StdDraw.setPenColor( Color.BLACK );
        StdDraw.filledCircle( input_X, input_Y, input_R );
    }
}