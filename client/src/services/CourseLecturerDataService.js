import axios from "axios";

const COURSELECTURER_API_BASE_URL = "http://localhost:8080/api/manage/courselecturer";



class CourseLecturerDataService{
    
    getCourses(){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listCourse",{withCredentials: true});
    }
    getLecturers(){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listLecturer",{withCredentials: true});
    }
    getUser(){
        return axios.get(COURSELECTURER_API_BASE_URL+"/getUser",{withCredentials: true});
    }
    getLecturersByCourseId(courseId){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listLecturerByCourseId/"+courseId,{withCredentials: true});
    }
    getAvailLecturersByCourseId(courseId){
        return axios.get(COURSELECTURER_API_BASE_URL+"/listAvilLecturerByCourseId/"+courseId,{withCredentials: true});
    }

    putLectuerToCourse(courseId, lecturers){
        //const postJSON = { lecturers: lecturers};
        return axios.post(COURSELECTURER_API_BASE_URL+"/addLecturersByCourseId/"+courseId, lecturers,{withCredentials: true});
    }

}

export default new CourseLecturerDataService();