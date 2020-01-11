/* Pause class to pause applications
   Anderson, Franceschi
*/

public class Pause
{
	public static void wait( double time )
	{
	   try {
			Thread.sleep(  ( int ) ( time * 2000 ) );
	   }
	   catch ( InterruptedException e )
	   {
			e.printStackTrace();
	   }
	}
   
   public void wait2( double time )
	{
	   try {
			Thread.sleep(  ( int ) ( time * 2000 ) );
	   }
	   catch ( InterruptedException e )
	   {
			e.printStackTrace();
	   }
	}
}