/* SelectionSortState class
*  Anderson, Franceschi
*/
import java.awt.Graphics;
import java.awt.Color;

public class SelectionSortState
{
 private int i, j, indexOfMax, counter;
 private int [] arrayState;

 public SelectionSortState( int i, int j, int [] array, int indexOfMax, int counter )
 {
  this.i = i;
  this.j = j;
  arrayState = new int[array.length];
  for ( int k = 0; k < array.length; k++ )
    arrayState[k ] = array[k];
  this.counter = counter;
  this.indexOfMax = indexOfMax;
 }

 public int getI( )
 {
  return i;
 }
 
 public int getJ( )
 {
  return j;
 }
 
 public int [] getArrayState( )
 {
  return arrayState;
 }
 
 public int getCounter( )
 {
  return counter;
 }
 
 public int getIndexOfMax( )
 {
  return indexOfMax;
 }

 public String toString( )
 {
  String result = "" + i + "; " + j + "; " + counter + "; " + "array: "; 
  for ( int k = 0; k < arrayState.length; k++ )
    result += arrayState[k] + " ";
  return result;
 }

}
