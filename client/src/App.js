import "./App.css";
import Course from "./components/Course";
import Navbar from "./components/Navbar";
import Header from "./components/Header";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Header />
        <Navbar />
        <Course />
      </header>
    </div>
  );
}

export default App;
