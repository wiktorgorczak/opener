import './App.css';
import React, { useState } from 'react';
import { ReactComponent as Icon} from './components/uploadIcon.svg';
import { Container, Header, Footer, Table } from 'rsuite'
import ProgressBar from './components/progressBar'
import Uploaded from './components/Uploaded';
import Home from './Home'

function App() {
  return (
    <Container className="mainContainer noselect" align="center">
      <Header className="mainHeader noselect">
        Opener.gov
      </Header>
      <Home />
      <Uploaded />
        <Footer>Copyright © 2021, All Right Reserved</Footer>
    </Container>
  )
}

export default App;
