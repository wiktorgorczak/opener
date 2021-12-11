import './App.css';
import React, { useState } from 'react';
import { ReactComponent as Icon} from './components/uploadIcon.svg';
import { Container, Header, Footer } from 'rsuite'
import ProgressBar from './components/progressBar'

function App() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [completed, setCompleted] = useState(33);

  return (
    <Container className="mainContainer noselect" align="center">
      <Header className="mainHeader noselect">
        Opener.gov
      </Header>
      <Container>
        <Icon className="uploadIcon"/>
      </Container>
      <Container>
      <input
          className="inputFileContainer noselect"
          type="file"
          multiple
          onChange={(e) => {
            setSelectedFile(e.target.files)
            setCompleted(66)
          }}
        />
        {
          selectedFile && <input className="upload noselect" type="button" value="Upload!" onclick="goToRaport()"  //selectedFile && 
          /> 
        }
      </Container>
      <Container>
        <ProgressBar completed={completed} />
      </Container>
        <Footer>Copyright © 2021, All Right Reserved</Footer>
    </Container>
  );
}

export default App;
