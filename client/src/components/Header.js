import React from "react";

const mainappaddress = "http://localhost:8080";

const Header = () => {
  return (
    <div>
      {" "}
      <nav className="navbar navbar-expand-md navbar-light bg-light firstmenu">
        <a className="navbar-brand" href={mainappaddress + "/home"}>CAPS</a>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            Welcome,&nbsp;
            <em>CAPS4</em>&nbsp; [<em>ADMIN</em>]
          </li>          
        </ul>
      </nav>
    </div>
  );
};

export default Header;
