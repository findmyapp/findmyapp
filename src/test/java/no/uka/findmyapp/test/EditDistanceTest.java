package no.uka.findmyapp.test;

import static org.junit.Assert.*;
import no.uka.findmyapp.service.EditDistanceService;

import org.junit.Test;


public class EditDistanceTest {
	@Test
	public void testEditDistance() {
		String qry = "bcd";
		String str = "abcdef";
		int rsp = EditDistanceService.editDistance(str, qry, true, true);
		assertEquals(3, rsp);
		rsp = EditDistanceService.editDistance(str, qry, true, false);
		assertEquals(1, rsp);
		rsp = EditDistanceService.editDistance(str, qry, false, true);
		assertEquals(2, rsp);
		rsp = EditDistanceService.editDistance(str, qry, false, false);
		assertEquals(0, rsp);
		
		qry = "cd ab   efg";
		rsp = EditDistanceService.splitDistance(str, qry);
		assertEquals(1, rsp);
	}
}
