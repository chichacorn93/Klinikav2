package org.itech.klinikav2.domain

/**
 * DailyCounter
 * A domain class describes the data object and it's mapping to the database
 */
class DailyCounter {
	Date date
	int patientCount = 0	
	
	static belongsTo = PatientQueue
}
