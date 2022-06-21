import React, { Component } from "react";
import { Link } from "react-router-dom";
import CourseLecturerDataService from "../services/CourseLecturerDataService";

export default class CourseLecturerList extends Component {
    constructor(props) {
        super(props);
        this.retrieveCourse = this.retrieveCourse.bind(this);
        this.retrieveLecturer = this.retrieveLecturer.bind(this);
        this.setActiveCourse = this.setActiveCourse.bind(this);
        this.setActiveAssignLecturer = this.setActiveAssignLecturer.bind(this);
        this.retrieveAssignLecturer = this.retrieveAssignLecturer.bind(this);
        this.setActiveAssignLecturer = this.setActiveAssignLecturer.bind(this);
        this.assignLecturerToCourse = this.assignLecturerToCourse.bind(this);
        this.retrieveAssignLecturer = this.retrieveAssignLecturer.bind(this);
        this.retrieveNotAssignLecturer = this.retrieveNotAssignLecturer.bind(this);

        this.state = {
            //Store the states to be used
            courses: [],
            currentCourse: null,
            currentCourseIndex: -1,
            lecturers: [],
            notAssignedLecturers: [],
            currentNotAssignedLecturer: null,
            currentNotAssignedLecturerIndex: -1,
            assignedLecturers:[],
            currentLecturerAssigned: null,
            currentLecturerAssignedIndex: -1
        };
    }

    componentDidMount() {
        //Basically the activities that will be done when mounting this component
        this.retrieveCourse();
    }

    //Methods that are required
    retrieveCourse() {
        //Retrieve all courses (use for left table)
        CourseLecturerDataService.getCourses()
            .then(response => {
                this.setState({
                    courses: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    //Methods that are required
    retrieveLecturer() {
        CourseLecturerDataService.getLecturers()
            .then(response => {
                this.setState({
                    lecturers: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    retrieveAssignLecturer(courseId) {
        //Retrieve assign lecturers (use for right table)
        CourseLecturerDataService.getLecturersByCourseId(courseId)
        .then(response => {
            this.setState({
                assignedLecturers: response.data
            });
            console.log(response.data);
            this.forceUpdate();
        })
        .catch(e => {
            console.log(e);
        });
    }

    retrieveNotAssignLecturer(courseId) {
        //Retrieve not assign lecturers (use for middle table)
        CourseLecturerDataService.getAvailLecturersByCourseId(courseId)
        .then(response => {
            this.setState({
                notAssignedLecturers: response.data
            });
            console.log(response.data);
            this.forceUpdate();
        })
        .catch(e => {
            console.log(e);
        });
    }



    setActiveCourse(course, index) {
        //Select course
        this.setState({
            currentCourse: course,
            currentCourseIndex: index
        });
        this.retrieveAssignLecturer(course.courseCode);
        this.retrieveNotAssignLecturer(course.courseCode);
        
    }

    setActiveAssignLecturer(lecturer, index) {
        this.setState({
            currentLecturerAssigned: lecturer,
            currentLecturerAssignedIndex: index
        });
    }

    setActiveNotAssignedLecturer(lecturer, index){
        this.setState({
            currentNotAssignedLecturer : lecturer,
            currentNotAssignedLecturerIndex: index
        });
    }

    assignLecturerToCourse(lecturer, notassignlec, assignlec){
        //Remove the state
        notassignlec = notassignlec.filter(x => x.lecturerId != lecturer.lecturerId)
        assignlec.push(lecturer)
        this.setState({
            currentNotAssignedLecturer: null,
            currentNotAssignedLecturerIndex: -1,
            assignedLecturers : assignlec,
            notAssignedLecturers : notassignlec
        });
        
    }


    //Render
    render() {
        const { courses, currentCourse, currentCourseIndex,
            lecturers,
            notAssignedLecturers, currentNotAssignedLecturer, currentNotAssignedLecturerIndex,
            assignedLecturers, currentLecturerAssigned, currentLecturerAssignedIndex } = this.state;
        return (
            <div className="list row">
                <div className="col-sm-4">
                    <h4>Course List</h4>

                    <ul className="list-group">
                        {courses &&
                            courses.map((course, index) => (
                                <li
                                    className={
                                        "list-group-item " +
                                        (index === currentCourseIndex ? "active" : "")
                                    }
                                    onClick={() => this.setActiveCourse(course, index)}
                                    key={index}
                                >
                                    {course.courseCode}
                                </li>
                            ))}
                    </ul>


                </div>


                <div className="col-sm-4">
                    <h4>Available Lecturer</h4>
                    <ul className="list-group">
                        {currentCourse ? (notAssignedLecturers.length > 0 ?
                            (notAssignedLecturers && notAssignedLecturers.map((lecturerRmd, index) => (
                                <li
                                    className={
                                        "list-group-item " +
                                        (index === currentNotAssignedLecturerIndex ? "active" : "")
                                    }
                                    onClick={() => this.setActiveNotAssignedLecturer(lecturerRmd, index)}
                                    onDoubleClick={() => this.assignLecturerToCourse(lecturerRmd, notAssignedLecturers,assignedLecturers)}
                                    key={index}
                                >
                                    {lecturerRmd.firstName + " " + lecturerRmd.lastName}
                                </li>
                            ))) :
                            (<div>
                                <br />
                                <p>No Lecturer left...</p>
                            </div>)
                        ) : (<div>
                            <br />
                            <p>Please select a course...</p>
                        </div>)}
                    </ul>
                </div>


                <div className="col-sm-4">
                    <h4>Assigned Lecturer</h4>

                    <ul className="list-group">
                        {currentCourse ?
                            assignedLecturers.length > 0 ?
                                (assignedLecturers &&
                                    assignedLecturers.map((lecturer, index) => (
                                        <li
                                            className={
                                                "list-group-item " +
                                                (index === currentLecturerAssignedIndex ? "active" : "")
                                            }
                                            onClick={() => this.setActiveAssignLecturer(lecturer, index)}
                                            key={index}
                                        >
                                            {lecturer.firstName + " " + lecturer.lastName}
                                        </li>
                                    ))) :(
                                <div>
                                    <br />
                                    <p>No Lecturer assigned...</p>
                                </div>
                            ) : (
                                <div>
                                    <br />
                                    <p>Please Select a Course...</p>
                                </div>
                            )}
                    </ul>
                </div>
            </div>
        );
    }

}
