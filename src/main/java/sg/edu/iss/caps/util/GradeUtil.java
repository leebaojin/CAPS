package sg.edu.iss.caps.util;

import sg.edu.iss.caps.model.Grade;

public class GradeUtil {

	public static Grade  calculateGrade(int score) throws Exception {
	
		
	if(score >= 90 && score <= 100) {
		return Grade.A;
	} else if (score >= 80 && score <= 89){
		return Grade.B;
	} else if (score >= 70 && score <= 79) {
		return Grade.C;
	} else if (score >= 60 && score <= 69) {
		return Grade.D;
	}else if (score >= 50 && score <= 59){
		return Grade.E;
	}else if (score >= 0 && score <= 49){
		return Grade.F;
	} else {
		throw new Exception("Score must be between 0 to 100");
	}

}
}
