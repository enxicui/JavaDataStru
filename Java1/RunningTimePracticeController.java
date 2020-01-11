/** RunningTimePracticeController
*   Anderson, Franceschi
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.animation.AnimationTimer;

public class RunningTimePracticeController 
{
 private BarChart bc;
  
 private int [] arr;
 private int size;
  
 @FXML private BorderPane bp;
 @FXML private HBox hBox1;
 @FXML private HBox hBox2;
  
 @FXML private TextField numberOfElementsInput;
 @FXML private Button showArray;
 @FXML private Button selectionSort;

 private Pane pane;
 private Canvas canvas; 
 private GraphicsContext gc;
 
 private static int maxIndex = -1;
 private boolean animate = false;
 private boolean firstTime = true;
 private boolean canStart = false;
 
 public void initialize( )
 {  
   Insets insets = new Insets( Dimensions.APP_PADDING, Dimensions.APP_PADDING, 
                               Dimensions.APP_PADDING, Dimensions.APP_PADDING );
   bp.setPadding( insets );
   hBox1.setPadding( insets );
   hBox2.setPadding( insets );
    
   pane = new Pane( );
   bp.setCenter( pane );
   canvas = new Canvas( Dimensions.APP_WIDTH - 2 * Dimensions.APP_PADDING, 
                        Dimensions.APP_HEIGHT - 6 * Dimensions.APP_PADDING );
   pane.getChildren( ).add( canvas );
   gc = canvas.getGraphicsContext2D( ); 
 }

  
 //  1. *****  student writes this method
 /**  Sorts arr in ascending order using the selection sort algorithm
 *    Adds a counter to count the number of iterations
 */
 public int selectionSort( )
 {
   // Note: To count the number of iterations, use a counter
   // The variable counter has been declared and initialized for you
   // at the beginning of this method
   // Inside the body of the inner loop, increment the counter
   // Replace the return statement so that this method returns the value of
   // the counter. To slow down or accelerate the animation, modify the
   // argument of Pause.wait in the handle method of the ArrayAnimationTimer inner class

   int counter = 0;
   int temp, indexOfMax;

   for ( int i = 0; i < arr.length - 1; i++ )
   {
 	  // find index of largest value in the subarray
     indexOfMax = 0;
	  animate( i, 0, 0, counter );
     for ( int j = 1; j < arr.length - i; j++ )
     {       
		 if ( arr[j] > arr[indexOfMax] )
			indexOfMax = j;
		 counter++;
		 animate( i, j, indexOfMax, counter);
     }
		  
	  // swap arr[indexOfMax] and arr[arr.length - i]
	  temp = arr[indexOfMax];
	  arr[indexOfMax] = arr[arr.length - i - 1];
	  arr[arr.length - i - 1] = temp;
   }
   animate( arr.length - 1, 0, 0, counter );
   return counter;

 } // end of selectionSort student code
 
 @FXML protected void showArray( ActionEvent event ) 
 {
   boolean goodInput = false;
   try
   {
       String answer = numberOfElementsInput.getText( );
       if ( answer != null )
       {
         size = Integer.parseInt( answer );
         if ( size >= 5 && size <= 25 )
           goodInput = true;
       }
   }
   catch ( Exception e )
   {
       disableButtons( false );
   }
   
   if ( goodInput )
   {
      // instantiate array
      arr = new int [size];
      // fill with random numbers between 50 and 80
      for ( int i = 0; i < arr.length; i++ )
        arr[i] = ( int ) ( Math.random( ) * 31 ) + 50;
      
      // display array
      bc = new BarChart( arr );
      bc.setSize( size );
      bc.drawArrayInBlue( gc );
      
      canStart = true;
   }
 }

 @FXML protected void activity( ActionEvent event ) 
 {
   if ( canStart )
   {
     int steps = selectionSort( );

     if ( animate )
     {
       disableButtons( true );
       bc.clearMessages( gc );
       AnimationTimer timer = new ArrayAnimationTimer( );
       timer.start( );
     }
   }
 }

 public void disableButtons( boolean state )
 {
   selectionSort.setDisable( state );
   showArray.setDisable( state );
 }

 
 public void animate( int i, int j, int indexOfMax, int counter )
 {        
   SelectionSortState sss = new SelectionSortState( i, j, arr, indexOfMax, counter );
   bc.addToSSStates( sss );
   animate = true;
 }
       
 private class ArrayAnimationTimer extends AnimationTimer 
 {
   @Override
   public void handle( long now ) 
   {
     Pause.wait( .5 );
          
     bc.drawSelectionSort( gc ); 
     // manage animation sequence
     bc.incrementCurrentIndex( );  
     if ( ! bc.checkCurrentIndexSelectionSort( ) )
     {
       bc.resetCurrentIndex( );
       bc.clearSSStates( );
       stop( );
       disableButtons( false );
     }    
   }
 } 
}