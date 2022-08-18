package com.abernathyclinic.riskreport.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculator {

	public static Period ageCalculator(Date patientBirthdate) {
		return Period.between(LocalDate.ofInstant(patientBirthdate.toInstant(), ZoneId.of("Europe/Paris")), LocalDate.now());
	}
	
	public static boolean isUnderThirty(Period age) {
		if(age.minusYears(Constants.AGE_THIRTY).isNegative()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isUnderThirtyFunction(Date patientBirthdate) {
		Period age = ageCalculator(patientBirthdate);
		return isUnderThirty(age);
	}
}
