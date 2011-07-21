package no.uka.findmyapp.test;

import static org.junit.Assert.assertEquals;
import no.uka.findmyapp.service.helper.EditDistanceHelper;

import org.junit.Before;
import org.junit.Test;


public class EditDistanceTest {
	EditDistanceHelper edService;
	
	@Before
	public void setUp() {
		edService = new EditDistanceHelper();
	}
	
	
	@Test
	public void testEditDistance() {	
		String qry = "bcd";
		String str = "abcdef";
		int rsp = edService.editDistance(str, qry, true, true);
		assertEquals(3, rsp);
		rsp = edService.editDistance(str, qry, true, false);
		assertEquals(1, rsp);
		rsp = edService.editDistance(str, qry, false, true);
		assertEquals(2, rsp);
		rsp = edService.editDistance(str, qry, false, false);
		assertEquals(0, rsp);
		
		qry = "cd ab   efg";
		rsp = edService.splitDistance(str, qry);
		assertEquals(1, rsp);
	}
}
