import "./App.css";
import Course from "./components/Course";
import Navbar from "./components/Navbar";
import Header from "./components/Header";
import React from 'react';
import { BrowserRouter as Router, Switch, Route, Link, Redirect} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import CourseLecturerList from "./components/CourseLecturerList";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Header />
        <Navbar />

      </header>
      <Router>
        <Route exact path='/CourseLecturer' component={CourseLecturerList} />
        <Route exact path="/">
          <Redirect to="/CourseLecturer" />
        </Route>
      </Router>
    </div>
  );
}

export default App;
