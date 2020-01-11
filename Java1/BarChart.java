/* BarChart Class
*  Anderson, Franceschi
*/

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class BarChart
{
  public static int XMAX = Dimensions.APP_WIDTH;
  public static int YMAX = 350;

  private int [] oldData;
  private int [] data;
  private int barSize;
  private int xStart;
  private int size;
  
  private int currentIndex;
  
  private ArrayList<SelectionSortState> ssStates;

  public BarChart( int [] dArray )
  {
    ssStates = new ArrayList<SelectionSortState>( );
  
    oldData = new int [dArray.length];
    data = new int [dArray.length];
    for ( int i = 0; i < dArray.length; i++ )
    {
        data[i] = dArray[i];
        oldData[i] = dArray[i];
    }
    barSize = ( XMAX - 20 ) / data.length;
    size = 0;
    currentIndex = 0;
  }
  
  public void addToSSStates( SelectionSortState sss )
  {
    ssStates.add( sss );
  }
  
  public void clearSSStates( )
  {
    ssStates.clear( );
  }
  
  public boolean checkIfArraySorted( int [] arr )
  {
    boolean sorted = true;
    for ( int i = 0; i < arr.length - 1; i++ )
    {
      if ( arr[i] > arr[i+1] )
        sorted = false;
    }
    return sorted;
  }
  
  public void selectionSort( int [] arr )
  {
    int temp, indexOfMax;
    for ( int i = 0; i < arr.length - 1; i++ )
    {
 	    // find index of largest value in the subarray
       indexOfMax = 0;
       for ( int j = 1; j < arr.length - i; j++ )
       {
		    if ( arr[j] > arr[indexOfMax] )
			   indexOfMax = j;
       }
		  
	    temp = arr[indexOfMax];
	    arr[indexOfMax] = arr[arr.length - i - 1];
	    arr[arr.length -i - 1] = temp;
    }
  } 
 
  public String checkIfSameArray( int [] arr )
  {
    String result = "";
    if ( arr.length != oldData.length )
      result = "ERROR: Your array no longer has the same length!";
    else
    {
      int [] arrCopy = new int[arr.length];
      for ( int i = 0; i < arr.length; i++ )
        arrCopy[i] = arr[i];
      selectionSort( arrCopy );
      // sort oldData
      selectionSort( oldData );
      // compare oldData and arr
      for ( int i = 0; i < arrCopy.length; i++ )
      { 
        if ( arrCopy[i] != oldData[i] )
          result = "ERROR: Your array has changed";
      }
    }
    System.out.println( );
    return result;
  }
  
  public void setData( int [] dArray )
  {
    for ( int i = 0; i < dArray.length; i++ )
    {
      oldData[i] = data[i];
      data[i] = dArray[i];
    }   
  }
  
  public void incrementCurrentIndex( )
  {
    currentIndex++;
  }

  public boolean checkCurrentIndexSelectionSort( )
  {
    return ( currentIndex >= 0 && currentIndex < ssStates.size( ) );
  }

  public void resetCurrentIndex( )
  {
    currentIndex = 0;
  }

  public void setArray( int [] dArray )
  {
    for ( int i = 0; i < dArray.length; i++ )
    {
        oldData[i] = data[i];
        data[i] = dArray[i];
    }
  }

  public void setSize( int newSize )
  {
    size = newSize;
  }
  
  public int calculateSteps( )
  {
    return size * ( size - 1 ) / 2;
  }

  public void drawArrayInBlue( GraphicsContext gc )
  {
    xStart = 10;
    gc.setFill( Color.NAVY );
    gc.setStroke( Color.NAVY );
    Font mediumFont = new Font( Dimensions.MEDIUM_SMALLER_FONT_SIZE );
    gc.setFont( mediumFont );
    gc.clearRect( 0, 0, Dimensions.APP_WIDTH, Dimensions.APP_HEIGHT  );

    for ( int i = 0; i < data.length; i++ )
    {
      gc.clearRect( xStart - 1, YMAX - 25 - 2 * data[i] - 1, barSize - 5 + 2, 2 * data[i] + 2 );

      gc.fillRect( xStart, YMAX - 25 - 2 * data[i], barSize-5, 2 * data[i] );
      gc.fillText( String.valueOf( data[i]), xStart + barSize / 4, YMAX - 5 );
      xStart += barSize;
    }
  }
  
  public void clearMessages( GraphicsContext gc )
  {
    // clear messages
    gc.clearRect( 0, 0, Dimensions.APP_WIDTH, Dimensions.APP_HEIGHT );
    
    // redraw in blue
    drawArrayInBlue( gc );
  }
  
  public void drawSelectionSort( GraphicsContext gc )
  {
    SelectionSortState sss = ssStates.get( currentIndex );
    int iIndex = sss.getI( ); // not used here
    int j = sss.getJ( );
    int counter = sss.getCounter( );
    int indexOfMax = sss.getIndexOfMax( );
    int [] currentData = sss.getArrayState( );
    
    gc.clearRect( 0, 0, Dimensions.APP_WIDTH, Dimensions.APP_HEIGHT );
    
    gc.setFill( Color.NAVY );
    // gc.clearRect( 25, 115 - 15, Dimensions.APP_WIDTH, 20 );
    gc.setStroke( Color.NAVY );
    
    Font mediumFont = new Font( Dimensions.MEDIUM_FONT_SIZE );
    gc.setFont( mediumFont );
    gc.fillText( "Sorting using Selection Sort", 25, YMAX / 7 );
    
    String result = "Your number of steps = " + counter;
    gc.fillText( result, 25, 3 * YMAX / 7 );
         
    Font mediumFont2 = new Font( Dimensions.MEDIUM_SMALLER_FONT_SIZE );
    gc.setFont( mediumFont2 );

    xStart = 10;
    for ( int i = 0; i < data.length; i++ )
    {
      if ( i == indexOfMax ) // draw current max array element
      {
        gc.setFill( Color.RED );
        gc.fillRect( xStart, YMAX - 25 - 2 * currentData[i], barSize-5, 2 * currentData[i] );
        gc.fillText( String.valueOf( currentData[i] ), xStart + barSize / 4, YMAX - 5 );
      }
      else if ( i == j ) // draw current array element
      {
        gc.setStroke( Color.RED );
        gc.strokeRect( xStart, YMAX - 25 - 2 * currentData[i], barSize-5, 2 * currentData[i] );
        gc.fillText( String.valueOf( currentData[i] ), xStart + barSize / 4, YMAX - 5 );
      }
      else 
      {
        gc.setFill( Color.NAVY );
        gc.fillRect( xStart, YMAX - 25 - 2 * currentData[i], barSize-5, 2 * currentData[i] );
        gc.fillText( String.valueOf( currentData[i] ), xStart + barSize / 4, YMAX - 5 );
      }
      xStart += barSize;
    }
    
    if ( currentIndex == ssStates.size( ) - 1 )
    {
      gc.setFont( mediumFont );
      
      int steps = calculateSteps( );
      String message = "The correct number of steps is " + steps;
      if ( counter == steps )
        gc.setFill( Color.GREEN );
      else
        gc.setFill( Color.RED );
      
      gc.fillText( message, 25, 2 * YMAX / 7 );
      
      setArray( currentData );
    } 
  }
}
