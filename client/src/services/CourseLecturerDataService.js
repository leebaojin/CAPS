import axios from "axios";

const COURSELECTURER_API_BASE_URL = "http://localhost:8080/api/manage/courselecturer";

class CourseLecturerDataService{
    getCourses(){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listCourse");
    }
    getLecturers(){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listLecturer");
    }
    getLecturersByCourseId(courseId){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listLecturerByCourseId/"+courseId);
    }
    getAvailLecturersByCourseId(courseId){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listAvilLecturerByCourseId/"+courseId);
    }

    putLectuerToCourse(courseId, lecturers){
        //const postJSON = { lecturers: lecturers};
        return axios.post(COURSELECTURER_API_BASE_URL+"/addLecturersByCourseId/"+courseId, lecturers);
    }

}

export default new CourseLecturerDataService();