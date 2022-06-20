import React from "react";

const Header = () => {
  return (
    <div>
      {" "}
      <nav class="navbar navbar-expand-md navbar-light bg-light firstmenu">
        <a class="navbar-brand">CAPS</a>

        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            Welcome,&nbsp;
            <em>CAPS4</em>&nbsp; [<em>ADMIN</em>]
          </li>
          <li>&nbsp;</li>
          <li class="nav-item">
            <a class="nav-link">Login</a>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default Header;
