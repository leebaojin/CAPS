import React, { useState } from "react";
import * as lecturers from "../api/lecturers.json";

const Course = () => {
  const code = "SA4001";
  const initialCourseState = {
    courseCode: code,
    courseTitle: "",
    courseDescription: "",
    courseCredits: 0,
    courseCapacity: 0,
    courseStatus: true,
    courseLecturers: lecturers,
  };

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [credits, setCredits] = useState(0);
  const [capacity, setCapacity] = useState(0);
  const [status, setStatus] = useState(true);
  const [availLecturers, setAvailLecturers] = useState([]);
  const [selectedLecturers, setSelectedLecturers] = useState(lecturers);
  const [courseObj, setCourseObj] = useState(initialCourseState);

  const submitForm = () => {
    const courseInfoObj = {
      courseCode: code,
      courseTitle: title,
      courseDescription: description,
      courseCredits: credits,
      courseCapacity: capacity,
      courseStatus: status,
      courseLecturers: selectedLecturers,
    };
    console.log(courseInfoObj);
  };

  return (
    <div>
      <div class="container">
        <div class="row">
          <div class="col-3"></div>
          <div class="col-6">
            <h2>Course Form</h2>
            <form method="">
              <div class="row mb-3">
                <label for="courseCode" class="col-sm-3 col-form-label">
                  Course Code
                </label>
                <div class="col-sm-9">
                  <input
                    type="text"
                    class="form-control"
                    id="courseCode"
                    value={code}
                    disabled
                  />
                </div>
              </div>
              <div class="row mb-3">
                <label for="courseTitle" class="col-sm-3 col-form-label">
                  Course Title
                </label>
                <div class="col-sm-9">
                  <input
                    type="text"
                    class="form-control"
                    id="courseTitle"
                    onChange={(e) => {
                      setTitle(e.target.value);
                    }}
                  />
                  <p></p>
                </div>
              </div>
              <div class="row mb-3">
                <label for="courseDescription" class="col-sm-3 col-form-label">
                  Course Description
                </label>
                <div class="col-sm-9">
                  <input
                    type="text"
                    class="form-control"
                    id="courseDescription"
                    onChange={(e) => {
                      setDescription(e.target.value);
                    }}
                  />
                  <p></p>
                </div>
              </div>
              <div class="row mb-3">
                <label for="courseCredits" class="col-sm-3 col-form-label">
                  Credits
                </label>
                <div class="col-sm-9">
                  <input
                    type="number"
                    class="form-control"
                    id="courseCredits"
                    onChange={(e) => {
                      setCredits(e.target.value);
                    }}
                  />
                  <p></p>
                </div>
              </div>
              <div class="row mb-3">
                <label for="courseCapacity" class="col-sm-3 col-form-label">
                  Capacity
                </label>
                <div class="col-sm-9">
                  <input
                    type="number"
                    class="form-control"
                    id="courseCapacity"
                    onChange={(e) => {
                      setCapacity(e.target.value);
                    }}
                  />
                  <p></p>
                </div>
              </div>
              <div class="row mb-3">
                <label for="courseStatus" class="col-sm-3 col-form-label">
                  Status
                </label>
                <div class="col-sm-9">
                  <select
                    class="form-select-lg mb-3"
                    id="courseStatus"
                    aria-label="Default select example"
                    value={courseObj.status === true ? "Closed" : "Open"}
                    onChange={(e) => {
                      setCourseObj({
                        ...courseObj,
                        status: e.target.value === "Open" ? true : false,
                      });
                      console.log("e.target.value", e.target.value);
                    }}
                  >
                    <option value="Open">Open</option>
                    <option value="Close">Close</option>
                  </select>
                </div>
              </div>
              <div class="row mb-3">
                <label for="courseLecturers" class="col-sm-3 col-form-label">
                  Lecturers
                </label>
                <div class="col-sm-9">
                  <select
                    class="form-select-lg mb-3"
                    id="courseLecturers"
                    aria-label="Default select example"
                    name="courseLecturerAdd"
                  >
                    <option defaultValue="select">Add Lecturer...</option>
                  </select>
                </div>
              </div>
              <input
                type="submit"
                value="Submit"
                class="btn btn-secondary"
                onClick={submitForm}
              ></input>
              &nbsp; &nbsp;
              <input
                type="reset"
                value="Reset"
                class="btn btn-secondary"
              ></input>
            </form>
          </div>
          <div class="col-3"></div>
        </div>
      </div>

      {/* <div th:replace="fragments/layout.html :: footersection"></div> */}
    </div>
  );
};

export default Course;
