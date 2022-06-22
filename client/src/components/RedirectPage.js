import React, { Component } from "react";

export default class RedirectPage extends Component{
    constructor(props){

    }
    componentDidMount(){
        window.location.replace("http://localhost:8080/");
    }
}