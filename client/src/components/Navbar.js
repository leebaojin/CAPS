import React from "react";

export const Navbar = () => {
  return (
    <div>
      <nav class="navbar navbar-expand-sm navbar-light bg-light">
        <div class="navbar-collapse collapse justify-content-center secondmenu">
          <ul class="navbar-nav">
            <li class="nav-item">
              {/* update href paths later */}
              <a class="nav-link" href="/manage/student/view">
                Manage&nbsp;Students
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/manage/lecturer/list">
                Manage&nbsp;Lecturers
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="">
                Manage&nbsp;Courses
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="">
                Manage&nbsp;Enrollment
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="">
                Log&nbsp;Out
              </a>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
