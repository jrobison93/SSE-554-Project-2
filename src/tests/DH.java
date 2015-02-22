package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import client.Diffie;

public class DH {
	
	int Sa, Sb;
	
	
	public void initialize(){
		int[] GnP = Diffie.Generate();
		//System.out.println(GnP[0] + " " +GnP[1]);
		int a = Diffie.Secret(GnP[0]);
		int b = Diffie.Secret(GnP[0]);
		//System.out.println(a + " " +b);
		int A = Diffie.GenMessage(GnP, a);
		int B = Diffie.GenMessage(GnP, b);
		//System.out.println(A + " " +B);
		Sa = Diffie.GenKey(GnP, B, a);
		Sb = Diffie.GenKey(GnP, A, b);
	}
	
	
	@Test
	public void testDH() {
	   //for(int i = 0;i<100;i++)
	   {
	   initialize();
	   assertEquals(true,Diffie.Test(Sa, Sb));
	   }}
	
	
	

}
