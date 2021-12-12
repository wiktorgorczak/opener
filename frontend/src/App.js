import './App.css';
import React from 'react';
import { Container, Header, Footer} from 'rsuite'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom' 
import Uploaded from './components/Uploaded';
import Home from './Home'
import Report from './Report';

function App() {
  return (
    <Router>
      <Container className="mainContainer noselect" align="center">
        <Header className="mainHeader noselect">
          Opener.gov
        </Header>
        <Routes>
          <Route exact path="/" element={<Home/>}/>
          <Route exact path="/generateReport" element={<Uploaded/>}/>
          <Route exact path="/report/:id" element={<Report/>}/>
        </Routes>
          <Footer>Copyright © 2021, All Right Reserved</Footer>
      </Container>
    </Router>
  )
}

export default App;
